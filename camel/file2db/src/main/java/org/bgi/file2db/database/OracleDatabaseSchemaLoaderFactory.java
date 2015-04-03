package org.bgi.file2db.database;

import org.bgi.file2db.format.FileFormat;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class OracleDatabaseSchemaLoaderFactory implements DatabaseSchemaLoaderFactory, ApplicationContextAware {

	private ApplicationContext ctx;
	
	public OracleDatabaseSchemaLoader createSchemaLoader(FileFormat fileFormat) {
		return new OracleDatabaseSchemaLoader(this.ctx);
	}

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}

}
