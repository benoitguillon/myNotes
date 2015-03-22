package org.bgi.eclipse.concurrency.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ExclusiveJobsHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public ExclusiveJobsHandler() {
		
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final int nbJobs = 3;
		final int nbIterationsPerJob = 20;
		
		ISchedulingRule rule = new ExclusiveSchedulingRule();
		
		for(int i=0; i<nbJobs; i++){
			SampleJob job = new SampleJob("Job " + (i+1), nbIterationsPerJob);
			job.setRule(rule);
			job.schedule();
		}
		return null;
	}
	
	private class ExclusiveSchedulingRule implements ISchedulingRule{

		@Override
		public boolean contains(ISchedulingRule rule) {
			return rule == this;
		}

		@Override
		public boolean isConflicting(ISchedulingRule rule) {
			return rule == this;
		}
		
	}
}
