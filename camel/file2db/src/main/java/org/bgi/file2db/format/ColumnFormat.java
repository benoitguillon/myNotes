package org.bgi.file2db.format;

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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
