package org.bgi.file2db.database;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.springframework.context.ApplicationContext;

public class OracleDatabaseSchemaLoader extends AbstractDatabaseSchemaLoader {

	public OracleDatabaseSchemaLoader(ApplicationContext ctx) {
		super(ctx);
	}

	@Override
	protected Dialect getHibernateDialect() {
		return new Oracle10gDialect();
	}
	
	

}
