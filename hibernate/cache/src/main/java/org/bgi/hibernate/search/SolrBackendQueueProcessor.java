package org.bgi.hibernate.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.search.backend.AddLuceneWork;
import org.hibernate.search.backend.DeleteLuceneWork;
import org.hibernate.search.backend.FlushLuceneWork;
import org.hibernate.search.backend.IndexingMonitor;
import org.hibernate.search.backend.LuceneWork;
import org.hibernate.search.backend.OptimizeLuceneWork;
import org.hibernate.search.backend.PurgeAllLuceneWork;
import org.hibernate.search.backend.UpdateLuceneWork;
import org.hibernate.search.backend.spi.BackendQueueProcessor;
import org.hibernate.search.indexes.spi.DirectoryBasedIndexManager;
import org.hibernate.search.spi.WorkerBuildContext;

public class SolrBackendQueueProcessor implements BackendQueueProcessor {

	public static final String SOLR_SERVER_URL_PROPERTY = "hibernate.search.solr.url"; 
	
	public static final String SOLR_SERVER_URL_DEFAULT = "http://localhost:8983/solr/core1";
	
	private static final Log LOG = LogFactory.getLog(SolrBackendQueueProcessor.class);
	
	private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
    private static final ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
	
	private ConcurrentUpdateSolrClient solrClient;
	
	private DirectoryBasedIndexManager indexManager;
	
	private static final String ID_FIELD_NAME = "id";
	
	@Override
	public void initialize(Properties props, WorkerBuildContext context, DirectoryBasedIndexManager indexManager) {
		if(LOG.isDebugEnabled()){
			LOG.debug("Initializing SolrBackendQueueProcessor");
		}
		String url = props.getProperty(SOLR_SERVER_URL_PROPERTY);
		if(url == null){
			url = SOLR_SERVER_URL_DEFAULT;
		}
		solrClient = new ConcurrentUpdateSolrClient(url, 10, 3);
		this.indexManager = indexManager;
	}

	@Override
	public void close() {
		solrClient.close();
	}

	@Override
	public void applyWork(List<LuceneWork> luceneWorks, IndexingMonitor monitor) {
		List<SolrInputDocument> solrWorks = new ArrayList<>(luceneWorks.size());
        List<String> documentsForDeletion = new ArrayList<>();

        for (LuceneWork work : luceneWorks) {
        	LOG.info("Inspecting LuceneWork: " + work);
            SolrInputDocument solrWork = new SolrInputDocument();
            if (work instanceof AddLuceneWork) {
                handleAddLuceneWork((AddLuceneWork) work, solrWork);
            } else if (work instanceof UpdateLuceneWork) {
                handleUpdateLuceneWork((UpdateLuceneWork) work, solrWork);
            } else if (work instanceof DeleteLuceneWork) {
                documentsForDeletion.add(((DeleteLuceneWork)work).getIdInString());
            } else if(work instanceof PurgeAllLuceneWork){
            	try {
            		solrClient.deleteByQuery("*:*");
            	}
            	catch(Exception e){
            		throw new RuntimeException("Failed to update solr", e);
            	}
            } else if(work instanceof OptimizeLuceneWork){
            	try {
            		solrClient.optimize();
            	}
            	catch(Exception e){
            		throw new RuntimeException("Failed to update solr", e);
            	}
            } else if(work instanceof FlushLuceneWork){
            	try {
					solrClient.commit();
				} catch (Exception e) {
					throw new RuntimeException("Failed to update solr", e);
				}
            }
            
            
            else {
                throw new RuntimeException("Encountered unsupported lucene work " + work);
            }
            solrWorks.add(solrWork);
        }
        try {
            deleteDocs(documentsForDeletion);
            solrClient.add(solrWorks);
            softCommit();
        } catch (SolrServerException | IOException e) {
            throw new RuntimeException("Failed to update solr", e);
        }
	}

	@Override
    public void applyStreamWork(LuceneWork luceneWork, IndexingMonitor indexingMonitor) {
        applyWork(Arrays.asList(luceneWork), indexingMonitor);
    }

    @Override
    public Lock getExclusiveWriteLock() {
        return writeLock;
    }

    @Override
    public void indexMappingChanged() {
    	LOG.warn("index mapping changed");
    	for(Class<?> c : this.indexManager.getContainedTypes()){
    		LOG.warn("Class being indexed : " + c.getCanonicalName());
    	}
    }

    private void deleteDocs(Collection<String> collection) throws IOException, SolrServerException {
        if (collection.size()>0) {
            StringBuilder stringBuilder = new StringBuilder(collection.size()*10);
            stringBuilder.append(ID_FIELD_NAME).append(":(");
            boolean first=true;
            for (String id : collection) {
                if (!first) {
                    stringBuilder.append(',');
                }
                else {
                    first=false;
                }
                stringBuilder.append(id);
            }
            stringBuilder.append(')');
            solrClient.deleteByQuery(stringBuilder.toString());
        }
    }
    private void copyFields(Document document, SolrInputDocument solrInputDocument) {
        boolean addedId = false;
        for (IndexableField fieldable : document.getFields()) {
            if (fieldable.name().equals(ID_FIELD_NAME)) {
                if (addedId)
                    continue;
                else
                    addedId = true;
            }
            solrInputDocument.addField(fieldable.name(), fieldable.stringValue());
        }
    }

    private void handleAddLuceneWork(AddLuceneWork luceneWork, SolrInputDocument solrWork) {
        copyFields(luceneWork.getDocument(), solrWork);
    }

    private void handleUpdateLuceneWork(UpdateLuceneWork luceneWork, SolrInputDocument solrWork) {
        copyFields(luceneWork.getDocument(), solrWork);
    }

    private void softCommit() throws IOException, SolrServerException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setParam("soft-commit", "true");
        updateRequest.setAction(UpdateRequest.ACTION.COMMIT,false, false);
        updateRequest.process(solrClient);
    }

}
