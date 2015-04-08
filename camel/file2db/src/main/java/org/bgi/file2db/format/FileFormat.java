package org.bgi.file2db.format;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.spi.DataFormat;
import org.bgi.file2db.FileFormatRegistry;
import org.bgi.file2db.camel.CamelHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public abstract class FileFormat implements InitializingBean {
	
	public static final String JOB_ID_COLUMN_NAME = "JOB_ID";
	
	public static final String JOB_START_DATE_COLUMN_NAME = "JOB_START_DATE";
	
	private String lineSeparator = "\n|\r\n";
	
	private File directory;
	
	private String name;
	
	private String targetDataSourceName;
	
	private List<ColumnFormat<?>> columns;
	
	private FileFormatRegistry fileFormatRegistry;
	
	private String targetTableName;
	
	public abstract DataFormat toCamelDataFormat();
	
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.name, "name property not set");
		Assert.notNull(targetDataSourceName, "targetDataSourceName property not set");
		Assert.notNull(directory, "directory property not set");
		if(this.targetTableName == null){
			this.targetTableName = this.name;
		}
		List<ColumnFormat<?>> builtins = createBuiltInColumns();
		List<ColumnFormat<?>> finalColumns = new ArrayList<ColumnFormat<?>>(builtins.size() + this.columns.size());
		finalColumns.addAll(builtins);
		finalColumns.addAll(this.columns);
		this.columns = finalColumns;
		
		this.getFileFormatRegistry().addFile(this);
	}
	
	private List<ColumnFormat<?>> createBuiltInColumns(){
		List<ColumnFormat<?>> builtInColumns = new ArrayList<ColumnFormat<?>>();
		LongColumnFormat jobIdColumn = new LongColumnFormat();
		jobIdColumn.setName(JOB_ID_COLUMN_NAME);
		jobIdColumn.setLength(10);
		jobIdColumn.setMessageHeaderName(CamelHelper.JOB_ID_HEADER_NAME);
		
		builtInColumns.add(jobIdColumn);
		
		TimestampColumnFormat jobStartDate = new TimestampColumnFormat();
		jobStartDate.setName(JOB_START_DATE_COLUMN_NAME);
		jobStartDate.setMessageHeaderName(CamelHelper.JOB_START_DATE_HEADER_NAME);
		builtInColumns.add(jobStartDate);
		
		return builtInColumns;
	}
	
	public List<ColumnFormat<?>> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnFormat<?>> columns) {
		this.columns = columns;
	}

	public String getTargetDataSourceName() {
		return targetDataSourceName;
	}

	public void setTargetDataSourceName(String targetDataSourceName) {
		this.targetDataSourceName = targetDataSourceName;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	public String getLineSeparator() {
		return lineSeparator;
	}

	public void setLineSeparator(String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}

	public FileFormatRegistry getFileFormatRegistry() {
		return fileFormatRegistry;
	}

	public void setFileFormatRegistry(FileFormatRegistry fileFormatRegistry) {
		this.fileFormatRegistry = fileFormatRegistry;
	}

	public String getTargetTableName() {
		return targetTableName;
	}

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}
}
