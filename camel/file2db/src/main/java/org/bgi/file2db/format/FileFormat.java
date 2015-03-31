package org.bgi.file2db.format;

import java.util.List;

public abstract class FileFormat {
	
	private List<ColumnFormat<?>> columns;

	public List<ColumnFormat<?>> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnFormat<?>> columns) {
		this.columns = columns;
	}

}
