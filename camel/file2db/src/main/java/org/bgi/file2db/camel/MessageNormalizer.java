package org.bgi.file2db.camel;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bgi.file2db.format.ColumnFormat;
import org.bgi.file2db.format.FileFormat;

public class MessageNormalizer implements Processor {

	private FileFormat fileFormat;
	
	public void process(Exchange exchange) throws Exception {
		List<?> msgContent = exchange.getIn().getBody(List.class);
		if(msgContent == null){
			throw new Exception("Message content is not a list but " + exchange.getIn().getBody().getClass());
		}
		if(msgContent.size() == 0){
			return;
		}
		Object firstItem = msgContent.get(0);
		if(firstItem instanceof String){
			List<Object> row = (List<Object>)msgContent;
			processRow(row);
		}
		else if(firstItem instanceof List<?>){
			List<List<Object>> rows = (List<List<Object>>)msgContent;
			for(List<Object> row : rows){
				processRow(row);	
			}
		}
	}
	
	private void processRow(List<Object> row) throws Exception {
		for(int i=0; i<Math.max(row.size(), this.getFileFormat().getColumns().size()); i++){
			Object data = row.get(i);
			if(data instanceof String){
				String dataValue = (String)data;
				ColumnFormat<?> columnFormat = fileFormat.getColumns().get(i);
				Object typedData = columnFormat.fromString(dataValue);
				row.set(i, typedData);
			}
			else {
				// do nothing
			}
		}
	}

	public FileFormat getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(FileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}
	
	

}
