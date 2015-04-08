package org.bgi.file2db;

import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.camel.Exchange;
import org.bgi.file2db.camel.CamelHelper;
import org.junit.Test;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(
		loader = SpringockitoContextLoader.class,
		locations={"/simpleCsvFile/simpleCsvFile.xml"}) // configuration to test

public class TestSimpleCsvFile extends AbstractTestCase {

	@Test
	@DirtiesContext
	public void testFile1() throws Exception {
		Exchange exchange = this.executeTest("simpleCsvFile/fileOk.csv");
		long jobId = CamelHelper.getJobId(exchange);
		Date jobStartDate = CamelHelper.getJobStartDate(exchange);
		Object[][] expected = { 
				{jobId, jobStartDate, "COL0-0", "COL1-0", "COL2-0", "COL3-0", new GregorianCalendar(2015, 3, 1).getTime()},
				{jobId, jobStartDate, "COL0-1", "COL1-1", "COL2-1", "COL3-1", new GregorianCalendar(2015, 3, 1).getTime()},
				{jobId, jobStartDate, "COL0-2", "COL1-2", "COL2-2", "COL3-2", new GregorianCalendar(2015, 3, 1).getTime()},
				{jobId, jobStartDate, "COL0-3", "COL1-3", "COL2-3", "COL3-3", new GregorianCalendar(2015, 3, 1).getTime()},
				{jobId, jobStartDate, "COL0-4", "COL1-4", "COL2-4", "COL3-4", new GregorianCalendar(2015, 3, 1).getTime()},
				{jobId, jobStartDate, "COL0-5", "COL1-5", "COL2-5", "COL3-5", new GregorianCalendar(2015, 3, 1).getTime()},
				{jobId, jobStartDate, "COL0-6", "COL1-6", "COL2-6", "COL3-6", new GregorianCalendar(2015, 3, 1).getTime()},
				{jobId, jobStartDate, "COL0-7", "COL1-7", "COL2-7", "COL3-7", new GregorianCalendar(2015, 3, 1).getTime()},
				{jobId, jobStartDate, "COL0-8", "COL1-8", "COL2-8", "COL3-8", new GregorianCalendar(2015, 3, 1).getTime()},
				
		};
		checkDataBaseContent(expected);
	}
}
