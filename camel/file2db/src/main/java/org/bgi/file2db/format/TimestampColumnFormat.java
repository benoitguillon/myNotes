package org.bgi.file2db.format;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

public class TimestampColumnFormat extends DateColumnFormat {

	@Override
	public int getJdbcDataType() {
		return Types.TIMESTAMP;
	}

	@Override
	public Date extractFromResultSet(ResultSet resultSet, int position) throws SQLException {
		return resultSet.getTimestamp(position);
	}

}
