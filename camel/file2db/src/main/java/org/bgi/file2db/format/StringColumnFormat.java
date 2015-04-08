package org.bgi.file2db.format;

import java.sql.ResultSet;
import java.sql.SQLException;
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

	@Override
	public String extractFromResultSet(ResultSet resultSet, int position)
			throws SQLException {
		return resultSet.getString(position);
	}

}
