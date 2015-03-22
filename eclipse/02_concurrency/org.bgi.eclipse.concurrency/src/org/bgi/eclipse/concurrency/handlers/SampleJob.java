package org.bgi.eclipse.concurrency.handlers;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class SampleJob extends Job {

	private int nbIterations;
	
	public SampleJob(String name, int nbIterations) {
		super(name);
		this.nbIterations = nbIterations;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		monitor.beginTask(this.getName(), this.nbIterations);
		for(int i=0; i<nbIterations; i++){
			if(monitor.isCanceled()){
				return Status.CANCEL_STATUS;
			}
			try {
				monitor.subTask("Starting iteration " + i);
				makeIteration(nbIterations);
				monitor.worked(1);
			}
			catch(InterruptedException e){
				return Status.CANCEL_STATUS;
			}
		}
		monitor.done();
		return Status.OK_STATUS;
	}
	
	private void makeIteration(int iteration) throws InterruptedException{
		Thread.sleep(1000);
	}

}
