package org.bgi.file2db.database;

import org.bgi.file2db.format.FileFormat;

public interface DatabaseSchemaLoaderFactory {
	
	public DatabaseSchemaLoader createSchemaLoader(FileFormat fileFormat);

}
