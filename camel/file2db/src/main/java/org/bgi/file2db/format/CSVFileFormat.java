package org.bgi.file2db.format;

import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.apache.commons.csv.CSVFormat;

public class CSVFileFormat extends FileFormat {
	
	private Character delimiter;

	public Character getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(Character delimiter) {
		this.delimiter = delimiter;
	}
	
	@Override
	public DataFormat toCamelDataFormat() {
		return new CsvDataFormat(toCSVFormat());
	}
	
	protected String[] getColumnsArray(){
		final String[] result = new String[this.getColumns().size()];
		for(int i=0; i<result.length; i++){
			result[i] = this.getColumns().get(i).getName();
		}
		return result;
	}
	
	protected CSVFormat toCSVFormat(){
		return CSVFormat.DEFAULT
				.withDelimiter(this.delimiter)
				.withHeader(this.getColumnsArray());
	}

}
