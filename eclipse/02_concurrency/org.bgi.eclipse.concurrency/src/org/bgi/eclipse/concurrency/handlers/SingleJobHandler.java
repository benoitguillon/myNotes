package org.bgi.eclipse.concurrency.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Schedules a single job
 */
public class SingleJobHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public SingleJobHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		SampleJob job = new SampleJob("Single sample job", 50);
		job.setPriority(Job.LONG);
		job.setUser(true);
		job.schedule();
		return null;
	}
}
