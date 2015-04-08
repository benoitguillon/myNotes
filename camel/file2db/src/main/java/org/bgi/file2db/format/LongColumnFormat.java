package org.bgi.file2db.format;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


public class LongColumnFormat extends ColumnFormat<Long> {

	@Override
	public Long fromString(String str) throws InvalidColumnFormatException {
		return Long.valueOf(str);
	}

	@Override
	public String fromObject(Long input) {
		return String.valueOf(input);
	}

	@Override
	public int getJdbcDataType() {
		return Types.BIGINT;
	}

	@Override
	public Long extractFromResultSet(ResultSet resultSet, int position)
			throws SQLException {
		return resultSet.getLong(position);
	}

}
