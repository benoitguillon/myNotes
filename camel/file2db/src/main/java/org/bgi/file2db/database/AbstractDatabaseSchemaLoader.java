package org.bgi.file2db.database;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.bgi.file2db.format.ColumnFormat;
import org.bgi.file2db.format.FileFormat;
import org.hibernate.dialect.Dialect;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.DatabaseMetaDataCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.util.Assert;

import com.healthmarketscience.sqlbuilder.CreateTableQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;

public abstract class AbstractDatabaseSchemaLoader implements DatabaseSchemaLoader {

	private static final String DROP_TABLE_QUERY = "DROP TABLE %s";
	
	private ApplicationContext ctx;
	
	private boolean dropExistingObjects = false;
	
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
		boolean tableExists = this.checkTableExists(format, targetDatasource); 
		if(tableExists && dropExistingObjects){
			this.dropTable(format, targetDatasource);
			tableExists = false;
		}
		if(!tableExists){
			this.createTable(format, targetDatasource);
		}
	}
	
	protected abstract Dialect getHibernateDialect();
	
	
	protected boolean checkTableExists(final FileFormat format, DataSource datasource) throws Exception {
		return (Boolean)JdbcUtils.extractDatabaseMetaData(datasource, new DatabaseMetaDataCallback() {
			public Object processMetaData(DatabaseMetaData dbmd) throws SQLException, MetaDataAccessException {
				ResultSet tablesResultSet = dbmd.getTables(null, null, null, new String[]{"TABLE"});
				while(tablesResultSet.next()){
					String tableName = tablesResultSet.getString("TABLE_NAME");
					if(format.getTargetTableName().equalsIgnoreCase(tableName)){
						return true;
					}
				}
				return false;
			}
		});
	}
	
	protected void dropTable(FileFormat format, DataSource datasource) throws Exception {
		this.executeDdlQuery(this.generateDropTableQuery(format.getTargetTableName()), datasource);
	}
	
	protected String generateDropTableQuery(String tableName){
		return String.format(DROP_TABLE_QUERY, tableName);
	}
	
	protected void createTable(FileFormat format, DataSource datasource) throws Exception {
		this.executeDdlQuery(generateCreateTableQuery(format), datasource);
	}
	
	protected String generateCreateTableQuery(FileFormat format){
		DbSpec spec = new DbSpec();
	    DbSchema schema = spec.addDefaultSchema();
	    
	    DbTable table = schema.addTable(format.getTargetTableName());
	    for(ColumnFormat<?> columnFormat : format.getColumns()){
	    	String sqlType = generateSqlType(columnFormat);
	    	table.addColumn(columnFormat.getName(), sqlType, null);
	    }
	    return new CreateTableQuery(table, true).validate().toString();
	}
	
	protected String generateSqlType(ColumnFormat<?> columnFormat){
		Dialect dialect = getHibernateDialect();
		int jdbcType = columnFormat.getJdbcDataType();
    	return dialect.getTypeName(jdbcType, columnFormat.getLength(), 0, 0);
	}
	
	private void executeDdlQuery(String sql, DataSource datasource) throws Exception {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.execute(sql);
	}

	public boolean isDropExistingObjects() {
		return dropExistingObjects;
	}

	public void setDropExistingObjects(boolean dropExistingObjects) {
		this.dropExistingObjects = dropExistingObjects;
	}

}
