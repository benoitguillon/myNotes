package org.bgi.file2db.processing;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class AssignJobIdProcessor implements Processor {

	public static final String JOB_ID_HEADER_NAME = "EMS_JOB_ID";
	
	private ExternalDataLoadingService service;
	
	public void process(Exchange exchange) throws Exception {
		long id = this.getService().assignNextJobId();
		exchange.setProperty(JOB_ID_HEADER_NAME, id);
	}

	public ExternalDataLoadingService getService() {
		return service;
	}

	public void setService(ExternalDataLoadingService service) {
		this.service = service;
	}

}
