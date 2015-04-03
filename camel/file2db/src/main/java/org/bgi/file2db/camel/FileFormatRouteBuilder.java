package org.bgi.file2db.camel;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.util.URISupport;
import org.bgi.file2db.format.ColumnFormat;
import org.bgi.file2db.format.FileFormat;
import org.bgi.file2db.processing.ExternalDataLoadingService;

public class FileFormatRouteBuilder extends RouteBuilder {
	
	private FileFormat format;
	
	private MessageNormalizer messageNormalizer;
	
	private ExternalDataLoadingService dataLoadingService;
	
	public FileFormatRouteBuilder(FileFormat format){
		this.format = format;
		this.messageNormalizer = new MessageNormalizer();
		this.messageNormalizer.setFileFormat(format);
	}

	@Override
	public void configure() throws Exception {
		from(getFileEndpointUri())
		.process(createAssignJobIdProcessor())
		.doTry()
			.split(body().tokenize(format.getLineSeparator())).streaming()
				.unmarshal(format.toCamelDataFormat())
				.process(this.messageNormalizer)
				.to(getSqlEndPointUri())
		.endDoTry()
		.doCatch(Exception.class)
			.to(getErrorFileEndpointUri())
		.end();
			
	}
	
	protected Processor createAssignJobIdProcessor(){
		AssignJobIdProcessor result = new AssignJobIdProcessor();
		result.setService(getDataLoadingService());
		return result;
	}
	
	protected String getErrorFileEndpointUri() throws Exception {
		URI fileUri = new File(format.getDirectory(), "errors").toURI();
		return fileUri.toASCIIString();
	}
	
	protected String getFileEndpointUri() throws Exception {
		URI fileUri = format.getDirectory().toURI();
		String fileUriStr = fileUri.toASCIIString();
		Map<String, Object> parameters = new HashMap<String, Object>();
		return URISupport.appendParametersToURI(fileUriStr, parameters);
	}
	
	protected String getSqlEndPointUri() throws Exception {
		String sqlUri = "sql:"+ generateSqlInsertQuery(); 
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("batch", true);
		parameters.put("parametersCount", this.getColumnCount());
		parameters.put("dataSource", this.format.getTargetDataSourceName());
		return URISupport.appendParametersToURI(sqlUri, parameters);
	}
	
	protected int getColumnCount(){
		return this.format.getColumns().size();
	}
	
	protected String generateSqlInsertQuery(){
		// generates : insert into <TABLE> (COL1, COL2, ..., COLN) values (#, #, ..., #)
		StringBuilder sql = new StringBuilder();
		
		//  COL1, COL2, ..., COLN
		StringBuilder columnList = new StringBuilder();
		// #, #, ..., #
		StringBuilder parametersList = new StringBuilder();
		
		Character sep =  null;
		for(ColumnFormat<?> column : this.format.getColumns()){
			if(sep != null){
				columnList.append(sep);
				parametersList.append(sep);
			}
			sep = ',';
			columnList.append(column.getName());
			parametersList.append('#');
		}
		
		sql.append("insert into ");
		sql.append(this.format.getTargetTableName());
		sql.append("(");
		sql.append(columnList.toString());
		sql.append(") values (");
		sql.append(parametersList.toString());
		sql.append(")");
		return sql.toString();
	}

	public ExternalDataLoadingService getDataLoadingService() {
		return dataLoadingService;
	}

	public void setDataLoadingService(ExternalDataLoadingService dataLoadingService) {
		this.dataLoadingService = dataLoadingService;
	}
	
	

}
