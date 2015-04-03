package org.bgi.file2db.database;

import org.bgi.file2db.format.FileFormat;

public interface DatabaseSchemaLoader {
	
	public void createDatabaseObjects(FileFormat format) throws Exception;
	
}
