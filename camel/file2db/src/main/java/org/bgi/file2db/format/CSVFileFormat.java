package org.bgi.file2db.format;

import org.apache.commons.csv.CSVFormat;

public class CSVFileFormat extends FileFormat {
	
	private Character delimiter;

	public Character getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(Character delimiter) {
		this.delimiter = delimiter;
	}
	
	public CSVFormat toCSVFormat(){
		return CSVFormat.DEFAULT
				.withDelimiter(this.delimiter)
				.withHeader(this.getColumnsArray());
	}
	
	protected String[] getColumnsArray(){
		final String[] result = new String[this.getColumns().size()];
		for(int i=0; i<result.length; i++){
			result[i] = this.getColumns().get(i).getName();
		}
		return result;
	}

}
