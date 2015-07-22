// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.bgi.hibernate.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.Query;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.bgi.hibernate.search.SolrBackendQueueProcessor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;


public class ContactDao {
    
    private SessionFactory sessionFactory;

    public Contact getById(long id){
        Session session = sessionFactory.openSession();
        try {
            return (Contact) session.get(Contact.class, id);
        }
        finally {
            session.close();
        }
    }
    
    public long save(Contact c){
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            long id = (long)session.save(c);
            t.commit();
            return id;
        }
        catch(Exception e){
        	t.rollback();
        	throw e;
        }
        finally {
            session.close();
        }
    }
    
    public void update(Contact c){
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
        	session.saveOrUpdate(c);
        	t.commit();
        }
        catch(Exception e){
        	t.rollback();
        	throw e;
        }
        finally {
        	session.close();
        }
    }
    
    @SuppressWarnings("unchecked")
	public List<Contact> findAll(){
    	Session session = sessionFactory.openSession();
    	try {
    		List<Contact> contacts = (List<Contact>) session.createQuery("FROM Contact ORDER BY lastName, firstName").list();
        	return contacts;
    	}
    	finally {
    		session.close();
    	}
    }
    
    public void delete(Contact c){
    	Session session = sessionFactory.openSession();
    	Transaction t = session.beginTransaction();
    	try {
    		session.delete(c);
    		t.commit();
    	}
    	catch(Exception e){
        	t.rollback();
        	throw e;
        }
        finally {
        	session.close();
        }
    }
    
    public void deleteAll() {
    	Session session = sessionFactory.openSession();
    	Transaction t = session.beginTransaction();
    	try {
    		int nbUpdates = session.createQuery("DELETE FROM Contact").executeUpdate();
    		t.commit();
    	}
    	catch(Exception e){
        	t.rollback();
        	throw e;
        }
        finally {
        	session.close();
        }
	}
    
    @SuppressWarnings("unchecked")
	public List<Contact> findFullText(String text){
    	Session session = sessionFactory.openSession();
    	FullTextSession fullTextSession = Search.getFullTextSession(session);
    	try {
    		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Contact.class).get();
    		Query q = qb.keyword().onFields("firstName", "lastName").matching(text).createQuery();
    		FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(q, Contact.class);
    		return (List<Contact>)fullTextQuery.list();
    	}
    	finally {
    		fullTextSession.close();
    	}
    }
    
//    @SuppressWarnings("unchecked")
//	public List<Contact> findFullText(String text){
//    	List<Contact> result =  new ArrayList<Contact>();
//    	ConcurrentUpdateSolrClient solrClient = null;
//    	Session session = null;
//    	try {
//    		session = sessionFactory.openSession();
//    		solrClient = new ConcurrentUpdateSolrClient(SolrBackendQueueProcessor.SOLR_SERVER_URL_DEFAULT, 10, 3);
//        	SolrQuery query = new SolrQuery();
//        	query.setQuery(text);
//        	solrClient.query(query);
//        	
//        	QueryResponse response = solrClient.query(query);
//        	SolrDocumentList results = response.getResults();
//            for (int i = 0; i < results.size(); ++i) {
//            	SolrDocument doc = results.get(i);
//            	Object o = doc.getFieldValue("id");
//            	long id = Long.parseLong(o.toString());
//            	Contact c = (Contact)session.get(Contact.class, id);
//            	if(c != null){
//            		result.add(c);
//            	}
//            	System.out.println(doc.toString());
//            }
//        	
//    	}
//    	catch(Exception e){
//    		throw new RuntimeException(e);
//    	}
//    	finally {
//    		if(solrClient != null){
//    			solrClient.close();
//    		}
//    		if(session != null){
//    			session.close();
//    		}
//    	}
//    	return result;
//    }
    
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	
    
    

}
