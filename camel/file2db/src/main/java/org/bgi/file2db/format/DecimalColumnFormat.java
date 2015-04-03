package org.bgi.file2db.format;

import java.math.BigDecimal;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.ParseException;

import org.springframework.beans.factory.InitializingBean;

public class DecimalColumnFormat extends ColumnFormat<BigDecimal> implements InitializingBean {

	private DecimalFormat decimalFormat;
	
	private String decimalPattern;
	
	@Override
	public BigDecimal fromString(String str) throws InvalidColumnFormatException {
		try {
			return (BigDecimal)decimalFormat.parse(str);
		} catch (ParseException e) {
			throw new InvalidColumnFormatException("Invalid number [" + str + "] for format [" + decimalPattern + "]", e);
		}
	}

	@Override
	public String fromObject(BigDecimal input) {
		return decimalFormat.format(input);
	}

	public void afterPropertiesSet() throws Exception {
		if(this.decimalPattern == null){
			this.decimalPattern = "";
		}
		this.decimalFormat = new DecimalFormat(this.decimalPattern);
		this.decimalFormat.setParseBigDecimal(true);
	}

	public String getDecimalPattern() {
		return decimalPattern;
	}

	public void setDecimalPattern(String decimalPattern) {
		this.decimalPattern = decimalPattern;
	}

	@Override
	public int getJdbcDataType() {
		return Types.DECIMAL;
	}

}
