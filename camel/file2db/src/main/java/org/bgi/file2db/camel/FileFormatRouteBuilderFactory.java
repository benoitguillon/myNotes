package org.bgi.file2db.camel;

import org.apache.camel.builder.RouteBuilder;
import org.bgi.file2db.format.FileFormat;

public interface FileFormatRouteBuilderFactory {
	
	public RouteBuilder createRouteBuilder(FileFormat format);

}
