package org.bgi.file2db.format;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;

public class DateColumnFormat extends ColumnFormat<Date> implements InitializingBean {

	private String datePattern;
	
	private DateFormat dateFormat;
	
	private boolean lenient = false;

	@Override
	public Date fromString(String str) throws InvalidColumnFormatException {
		try {
			return this.dateFormat.parse(str);
		}
		catch(ParseException e){
			throw new InvalidColumnFormatException("Invalid date [" + str + "] for pattern [" + datePattern + "]", e);
		}
	}

	@Override
	public String fromObject(Date input) {
		return this.dateFormat.format(input);
	}

	public void afterPropertiesSet() throws Exception {
		if(this.datePattern == null){
			this.datePattern = "yyyy-MM-dd";
		}
		this.dateFormat = new SimpleDateFormat(this.datePattern);
		this.dateFormat.setLenient(this.lenient);
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}
	
	public boolean isLenient() {
		return lenient;
	}

	public void setLenient(boolean lenient) {
		this.lenient = lenient;
	}

	@Override
	public int getJdbcDataType() {
		return Types.DATE;
	}

	@Override
	public Date extractFromResultSet(ResultSet resultSet, int position) throws SQLException {
		return resultSet.getDate(position);
	}

}
