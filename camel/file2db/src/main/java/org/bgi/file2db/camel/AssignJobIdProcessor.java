package org.bgi.file2db.camel;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bgi.file2db.processing.ExternalDataLoadingService;

public class AssignJobIdProcessor implements Processor {

	private ExternalDataLoadingService service;
	
	public void process(Exchange exchange) throws Exception {
		long id = this.getService().assignNextJobId();
		CamelHelper.setJobId(exchange, id);
		CamelHelper.setJobStartDate(exchange, new Date());
	}

	public ExternalDataLoadingService getService() {
		return service;
	}

	public void setService(ExternalDataLoadingService service) {
		this.service = service;
	}

}
