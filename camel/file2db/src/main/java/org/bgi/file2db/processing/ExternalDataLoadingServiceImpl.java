package org.bgi.file2db.processing;

import java.util.Random;

public class ExternalDataLoadingServiceImpl implements
		ExternalDataLoadingService {

	private Random randomGenerator = new Random(System.currentTimeMillis());
	
	public long assignNextJobId() {
		return randomGenerator.nextLong();
	}
}
