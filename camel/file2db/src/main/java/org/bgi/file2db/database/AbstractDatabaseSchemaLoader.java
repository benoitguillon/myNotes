package org.bgi.file2db.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.bgi.file2db.format.ColumnFormat;
import org.bgi.file2db.format.FileFormat;
import org.hibernate.dialect.Dialect;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import com.healthmarketscience.sqlbuilder.CreateTableQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;

public abstract class AbstractDatabaseSchemaLoader implements DatabaseSchemaLoader {

	private ApplicationContext ctx;
	
	public AbstractDatabaseSchemaLoader(ApplicationContext ctx){
		this.ctx = ctx;
	}
	
	public void createDatabaseObjects(FileFormat format) throws Exception {
		Assert.notNull(format, "Null format passed as parameter");
		Assert.notNull(format.getName(), "format has a null name");
		Assert.notNull(format.getTargetTableName(), "format has a null targetTableName");
		Assert.notNull(format.getTargetDataSourceName(), "format has a null targetDataSourceName");
		DataSource targetDatasource = this.ctx.getBean(format.getTargetDataSourceName(), DataSource.class);
		Assert.notNull(targetDatasource, "targetDataSourceName " + format.getTargetDataSourceName() + " does not refer to an existing datasource");
		if(!this.checkTableExists(format, targetDatasource)){
			this.createTable(format, targetDatasource);
		}
	}
	
	protected abstract Dialect getHibernateDialect();
	
	
	protected boolean checkTableExists(FileFormat format, DataSource datasource) throws Exception {
		Connection connection = datasource.getConnection();
		DatabaseMetaData metadata = connection.getMetaData();
		ResultSet tablesResultSet = metadata.getTables(null, null, null, new String[]{"TABLE"});
		try {
			while(tablesResultSet.next()){
				String tableName = tablesResultSet.getString("TABLE_NAME");
				if(format.getTargetTableName().equalsIgnoreCase(tableName)){
					return true;
				}
			}
		}
		finally {
			if(connection != null){
				connection.close();
			}
		}
		return false;
	}
	
	protected void createTable(FileFormat format, DataSource datasource) throws Exception {
		DbSpec spec = new DbSpec();
	    DbSchema schema = spec.addDefaultSchema();
	    
	    Dialect dialect = getHibernateDialect();
	    
	    DbTable table = schema.addTable(format.getTargetTableName());
	    for(ColumnFormat<?> columnFormat : format.getColumns()){
	    	int jdbcType = columnFormat.getJdbcDataType();
	    	String sqlType = dialect.getTypeName(jdbcType, 255, 0, 0);
	    	table.addColumn(columnFormat.getName(), sqlType, null);
	    }
	    String createTableQuery = new CreateTableQuery(table, true).validate().toString();
	    
	    Connection connection = datasource.getConnection();
	    
	    try {
	    	connection.createStatement().executeUpdate(createTableQuery);
	    }
	    finally {
	    	if(connection != null){
	    		connection.close();
	    	}
	    }
	}

}
