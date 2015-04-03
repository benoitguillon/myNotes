package org.bgi.file2db;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.builder.RouteBuilder;
import org.bgi.file2db.camel.DefaultFileFormatRouteBuilderFactory;
import org.bgi.file2db.camel.FileFormatRouteBuilderFactory;
import org.bgi.file2db.database.DatabaseSchemaLoader;
import org.bgi.file2db.database.DatabaseSchemaLoaderFactory;
import org.bgi.file2db.format.FileFormat;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class FileFormatRegistry implements InitializingBean, CamelContextAware {

	private CamelContext camelContext;
	
	private List<FileFormat> files = new ArrayList<FileFormat>();
	
	private FileFormatRouteBuilderFactory routeBuilderFactory;
	
	private DatabaseSchemaLoaderFactory databaseSchemaLoaderFactory;
	
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(camelContext, "camelContext property not set");
		Assert.notNull(databaseSchemaLoaderFactory, "databaseSchemaLoaderFactory property not set");
		if(this.routeBuilderFactory == null){
			this.routeBuilderFactory = new DefaultFileFormatRouteBuilderFactory();
		}
	}
	
	public void addFile(FileFormat file) throws Exception {
		this.files.add(file);
		// database schema loader
		DatabaseSchemaLoader schemaLoader = this.databaseSchemaLoaderFactory.createSchemaLoader(file);
		schemaLoader.createDatabaseObjects(file);
		// set up camel route
		RouteBuilder routeBuilder = this.routeBuilderFactory.createRouteBuilder(file);
		this.camelContext.addRoutes(routeBuilder);
	}

	public CamelContext getCamelContext() {
		return camelContext;
	}

	public void setCamelContext(CamelContext camelContext) {
		this.camelContext = camelContext;
	}

	public FileFormatRouteBuilderFactory getRouteBuilderFactory() {
		return routeBuilderFactory;
	}

	public void setRouteBuilderFactory(
			FileFormatRouteBuilderFactory routeBuilderFactory) {
		this.routeBuilderFactory = routeBuilderFactory;
	}

	public DatabaseSchemaLoaderFactory getDatabaseSchemaLoaderFactory() {
		return databaseSchemaLoaderFactory;
	}

	public void setDatabaseSchemaLoaderFactory(DatabaseSchemaLoaderFactory databaseSchemaLoaderFactory) {
		this.databaseSchemaLoaderFactory = databaseSchemaLoaderFactory;
	}
	

}
