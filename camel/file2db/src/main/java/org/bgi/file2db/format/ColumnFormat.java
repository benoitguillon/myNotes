package org.bgi.file2db.format;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a column in a source file
 * 
 * @author Benoit.Guillon
 *
 * @param <T> type of java objects
 */
public abstract class ColumnFormat<T> {
	
	/**
	 * name of the column
	 */
	private String name;
	
	
	private int length = 255;
	
	/**
	 * name of the message header to get data from (instead of file content)
	 */
	private String messageHeaderName;
	
	/**
	 * @return an object given a string
	 * 
	 * @param str
	 * 
	 */
	public abstract T fromString(String str) throws InvalidColumnFormatException;
	
	/**
	 * @return a string given an object
	 * 
	 * @param input
	 * 
	 */
	public abstract String fromObject(T input);
	
	/**
	 * @return the jdbc data type.
	 * 
	 * One of java.sql.Types
	 */
	public abstract int getJdbcDataType();
	
	/**
	 * Extracts data from the provided resultSet at the provided position
	 * 
	 * A way to guess the proper getXXX method to use on the ResultSet depending on the 
	 * expected type. 
	 * 
	 * @param resultSet
	 * @param position
	 * @return
	 */
	public abstract T extractFromResultSet(final ResultSet resultSet, final int position) throws SQLException;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessageHeaderName() {
		return messageHeaderName;
	}

	public void setMessageHeaderName(String messageHeaderName) {
		this.messageHeaderName = messageHeaderName;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
}
