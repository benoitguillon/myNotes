package org.bgi.file2db.format;

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
	public String toOracleDbType() {
		return "VARCHAR2(255)";
	}

}
