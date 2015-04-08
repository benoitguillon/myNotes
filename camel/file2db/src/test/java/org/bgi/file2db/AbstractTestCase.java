package org.bgi.file2db;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.test.spring.UseAdviceWith;
import org.bgi.file2db.camel.CamelHelper;
import org.bgi.file2db.format.ColumnFormat;
import org.bgi.file2db.format.FileFormat;
import org.bgi.file2db.processing.ExternalDataLoadingService;
import org.junit.Assert;
import org.junit.Before;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(
		loader = SpringockitoContextLoader.class,
		locations={
		AbstractTestCase.DATASOURCE_CTX_FILE, // test database location
		AbstractTestCase.FRAMEWORK_CTX_FILE})  // framework
@UseAdviceWith(value=true)
public abstract class AbstractTestCase extends AbstractJUnit4SpringContextTests {
	
	public static final String DATASOURCE_CTX_FILE = "/test-datasource-context.xml";
	
	public static final String FRAMEWORK_CTX_FILE = "/data-loading-service-context.xml";
	
	private static final String SELECT_ALL_SQL = "SELECT * FROM %s ORDER BY ROWID";
	
	@EndpointInject(uri="direct:input")
	private ProducerTemplate producer; 
	
	@EndpointInject(uri="mock:resultingMock")
	private MockEndpoint resultingMock;
	
	@Autowired(required=true)
	protected ModelCamelContext camelContext;
	
	@Autowired(required=true)
	protected FileFormat testFileFormat;
	
	@ReplaceWithMock
	@Autowired(required=true)
	protected ExternalDataLoadingService externalDataLoadingService;
	
	@Autowired
	protected DataSource dataSource;
	
	protected long jobId = 0;
	
	@Before
	public void setUpContext() throws Exception {
		this.jobId = Math.abs(new Random(System.currentTimeMillis()).nextLong());
		Mockito.when(externalDataLoadingService.assignNextJobId()).thenReturn(jobId);
		
		AdviceWithRouteBuilder builder = new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				replaceFromWith("direct:input");
				weaveAddLast().to("mock:resultingMock");
			}
		};
		this.camelContext.getRouteDefinition(this.testFileFormat.getName()).adviceWith(this.camelContext, builder);
		this.camelContext.start();
	}
	
	protected long getLastGeneratedJobId(){
		return this.jobId;
	}
	
	protected Exchange executeTest(String classPathLocation) throws Exception {
		Resource testFileResource = new ClassPathResource(classPathLocation);
		File testFile = testFileResource.getFile();
		producer.sendBody(testFile);
		Assert.assertEquals(1, resultingMock.getExchanges().size());
		Exchange exchange = resultingMock.getExchanges().get(0);
		Message message = exchange.getIn();
		Assert.assertEquals(jobId, message.getHeader(CamelHelper.JOB_ID_HEADER_NAME));
		return exchange;
	}
	
	protected void checkDataBaseContent(Object[][] expectedResult){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
		 List<Object[]>  result = jdbcTemplate.query(String.format(SELECT_ALL_SQL, this.testFileFormat.getTargetTableName()), new ArrayRowMapper(this.testFileFormat));
		 Assert.assertEquals(expectedResult.length, result.size());
		 for(int i=0; i<expectedResult.length; i++){
			 Object[] expectedRow = expectedResult[i];
			 Object[] resultRow = result.get(i);
			 Assert.assertArrayEquals(expectedRow, resultRow);
		 }
	}
	
	private class ArrayRowMapper implements RowMapper<Object[]>{
		
		private FileFormat format;
		
		public ArrayRowMapper(FileFormat format){
			this.format = format;
		}
		
		public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
			int size = rs.getMetaData().getColumnCount();
			Object[] result = new Object[size];
			for(int i=0; i<result.length; i++){
				ColumnFormat<?> column = this.format.getColumns().get(i);
				result[i] = column.extractFromResultSet(rs, i + 1);
			}
			return result;
		}
	}
}
