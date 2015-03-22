# Concurrency

## Jobs

Unit of work are represented by jobs. Jobs can run asynchronously. 
When a job is scheduled, it is added to a job queue managed by the platform.
When a job is executed by the plaform its run method is invoked.

```
TrivialJob job = new TrivialJob();
job.schedule();
```

```
class TrivialJob extends Job {
      public TrivialJob() {
         super("Trivial Job");
      }
      public IStatus run(IProgressMonitor monitor) {
         System.out.println("This is a job");
         return Status.OK_STATUS;
      }
 }
 ```

Other useful methods:
```
// block the caller until the job is completed
job.join(); 
// cancels a job if it has not started yet. If it has already started, it's up to the job to stop
// the boolean will be true in the first case, false otherwise.
boolean result = job.cancel(); 
// makes the job is put on hold indefinitely  if the job has not started yet
job.sleep();
// puts a sleeping job back in the queue
job.wakeUp();
```

## Jobs states

Job states:
* WAITING : is scheduled and waiting to be executed
* RUNNING : its run method is being executed
* SLEEPING : sleeping due to a sleep request
* NONE : not scheduled yet

```
job.addJobChangeListener(IJobChangeListener)
```

Listeners are provided with a IJobChangeEvent object.

## JobManager

To access running jobs and manage them

```
IJobManager jobMan = Platform.getJobManager();
```

## IProgressMonitor

```
public IStatus run(IProgressMonitor monitor) {
	monitor.beginTask("Doing some work", ticks);
	if (monitor.isCanceled()){
		return Status.CANCEL_STATUS;
	}
	monitor.subTask("Processing tick #" + i);
	monitor.worked(1);
	...
	monitor.worked();
	return Status.OK_STATUS;
}
```

If a job must not be displayed, `job.setSystem(true)`. Must be made before the job is scheduled.

If a job is initiated by a user action, `job.setUser(true)`. Then a modal dialog is displayed.


