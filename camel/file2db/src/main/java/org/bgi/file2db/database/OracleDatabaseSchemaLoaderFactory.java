package org.bgi.file2db.database;

import org.bgi.file2db.format.FileFormat;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class OracleDatabaseSchemaLoaderFactory implements DatabaseSchemaLoaderFactory, ApplicationContextAware {

	private ApplicationContext ctx;
	
	private boolean dropExistingDatabaseObject;
	
	public OracleDatabaseSchemaLoader createSchemaLoader(FileFormat fileFormat) {
		OracleDatabaseSchemaLoader loader = new OracleDatabaseSchemaLoader(this.ctx);
		loader.setDropExistingObjects(this.dropExistingDatabaseObject);
		return loader;
	}

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}

	public boolean isDropExistingDatabaseObject() {
		return dropExistingDatabaseObject;
	}

	public void setDropExistingDatabaseObject(boolean dropExistingDatabaseObject) {
		this.dropExistingDatabaseObject = dropExistingDatabaseObject;
	}

}
