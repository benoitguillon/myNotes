package org.bgi.file2db.camel;

import org.apache.camel.builder.RouteBuilder;
import org.bgi.file2db.format.FileFormat;
import org.bgi.file2db.processing.ExternalDataLoadingService;

public class DefaultFileFormatRouteBuilderFactory implements
		FileFormatRouteBuilderFactory {

	private ExternalDataLoadingService dataLoadingService;
	
	public RouteBuilder createRouteBuilder(FileFormat format) {
		FileFormatRouteBuilder routeBuilder = new FileFormatRouteBuilder(format);
		routeBuilder.setDataLoadingService(this.dataLoadingService);
		return routeBuilder;
	}

	public ExternalDataLoadingService getDataLoadingService() {
		return dataLoadingService;
	}

	public void setDataLoadingService(
			ExternalDataLoadingService externalDataLoadingService) {
		this.dataLoadingService = externalDataLoadingService;
	}

}
