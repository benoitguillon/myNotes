package org.bgi.eclipse.concurrency.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Schedules 3 jobs in a group
 */
public class JobGroupHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public JobGroupHandler() {
		
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IJobManager jobManager = Job.getJobManager();
		IProgressMonitor groupMonitor = jobManager.createProgressGroup();
		
		final int nbJobs = 3;
		final int nbIterationsPerJob = 20;
		
		for(int i=0; i<nbJobs; i++){
			SampleJob job = new SampleJob("Job " + (i+1), nbIterationsPerJob);
			job.setProgressGroup(groupMonitor, (nbJobs * nbIterationsPerJob));
			job.setPriority(Job.LONG);
			job.schedule();
		}
		return null;
	}
}
