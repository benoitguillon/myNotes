package org.bgi.file2db.camel;

import java.util.Date;

import org.apache.camel.Exchange;

public class CamelHelper {

	public static final String JOB_ID_HEADER_NAME = "JOB_ID";
	
	public static final String JOB_START_DATE_HEADER_NAME = "JOB_START_DATE";
	
	private CamelHelper(){
		
	}
	
	public static void setJobId(final Exchange exchange, long jobId){
		exchange.getIn().setHeader(JOB_ID_HEADER_NAME, jobId);
	}
	
	public static void setJobStartDate(final Exchange exchange, final Date date){
		exchange.getIn().setHeader(JOB_START_DATE_HEADER_NAME, date);
	}
	
	public static long getJobId(final Exchange exchange){
		return exchange.getIn().getHeader(JOB_ID_HEADER_NAME, Long.class);
	}
	
	public static Date getJobStartDate(final Exchange exchange){
		return exchange.getIn().getHeader(JOB_START_DATE_HEADER_NAME, Date.class);
	}
}
