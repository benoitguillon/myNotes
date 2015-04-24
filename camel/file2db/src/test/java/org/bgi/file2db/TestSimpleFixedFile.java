package org.bgi.file2db;

import java.util.Date;

import org.apache.camel.Exchange;
import org.bgi.file2db.camel.CamelHelper;
import org.junit.Test;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(
		loader = SpringockitoContextLoader.class,
		locations={"/simpleFixedFile/simpleFixedFile.xml"}) // configuration to test

public class TestSimpleFixedFile extends AbstractTestCase {

	@Test
	@DirtiesContext
	public void testFile1() throws Exception {
		Exchange exchange = this.executeTest("simpleFixedFile/fixedOk.txt");
		long jobId = CamelHelper.getJobId(exchange);
		Date jobStartDate = CamelHelper.getJobStartDate(exchange);
		Object[][] expected = { 
				{jobId, jobStartDate, "AB1", "R1", "AZERT1"},
				{jobId, jobStartDate, "AB2", "R2", "AZERT2"},
				{jobId, jobStartDate, "AB3", "R3", "AZERT3"},
				{jobId, jobStartDate, "AB4", "R4", "AZERT4"},
				{jobId, jobStartDate, "AB5", "R5", "AZERT5"},
				{jobId, jobStartDate, "AB6", "R6", "AZERT6"},
				{jobId, jobStartDate, "AB7", "R7", "AZERT7"},
				{jobId, jobStartDate, "AB8", "R8", "AZERT8"},
				{jobId, jobStartDate, "AB9", "R9", "AZERT9"},
				
		};
		checkDataBaseContent(expected);
	}
}
