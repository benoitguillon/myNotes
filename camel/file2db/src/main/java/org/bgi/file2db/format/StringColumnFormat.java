package org.bgi.file2db.format;

import java.sql.Types;

public class StringColumnFormat extends ColumnFormat<String> {

	@Override
	public String fromString(String str) {
		return str;
	}

	@Override
	public String fromObject(String input) {
		return input;
	}

	@Override
	public int getJdbcDataType() {
		return Types.VARCHAR;
	}

}
