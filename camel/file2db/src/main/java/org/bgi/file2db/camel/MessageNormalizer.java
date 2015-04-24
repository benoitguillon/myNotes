package org.bgi.file2db.camel;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bgi.file2db.format.ColumnFormat;
import org.bgi.file2db.format.FileFormat;

public class MessageNormalizer implements Processor {

	private FileFormat fileFormat;
	
	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {
		List<?> msgContent = exchange.getIn().getBody(List.class);
		if(msgContent == null){
			throw new Exception("Message content is not a list but " + exchange.getIn().getBody().getClass());
		}
		if(msgContent.size() == 0){
			return;
		}
		List<List<Object>> output;
		// guess list content based on first item
		Object firstItem = msgContent.get(0);
		// if it's List<List<Object>> : there are several rows 
		if(firstItem instanceof List<?>){
			List<List<Object>> rows = (List<List<Object>>)msgContent;
			output = new ArrayList<List<Object>>(rows.size());
			for(List<Object> row : rows){
				output.add(processRow(row, exchange));
			}
		}
		else {
			output = new ArrayList<List<Object>>(1);
			List<Object> row = (List<Object>)msgContent;
			output.add(processRow(row, exchange));
		}
		exchange.getIn().setBody(output);
	}
	
	protected List<Object> processRow(List<Object> row, Exchange exchange) throws Exception {
		List<Object> result = new ArrayList<Object>(this.getFileFormat().getColumns().size());
		int rowIndex = 0;
		for(int i=0; i<this.getFileFormat().getColumns().size(); i++){
			ColumnFormat<?> columnFormat = this.getFileFormat().getColumns().get(i);
			String headerName = columnFormat.getMessageHeaderName();
			Object inputData = null;
			Object outputData = null;
			// column content from message
			if(headerName == null){
				inputData = row.get(rowIndex);
				rowIndex++;
			}
			// column content from header
			else {
				inputData = exchange.getIn().getHeader(headerName);
			}
			outputData = typeObject(inputData, columnFormat);
			result.add(outputData);
		}
		return result;
	}
	
	protected Object typeObject(Object input, ColumnFormat<?> format) throws Exception {
		if(input instanceof String){
			String dataValue = (String)input;
			return format.fromString(dataValue);
		}
		return input;
	}

	public FileFormat getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(FileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}
	
	

}
