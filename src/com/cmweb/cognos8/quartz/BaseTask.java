package com.cmweb.cognos8.quartz;

import org.quartz.Scheduler;

//定时任务基类
public abstract class BaseTask implements ITask {
	protected Scheduler scheduler = null;

	public void stopTask() throws Exception {
		this.scheduler.shutdown();
	}

	public void pauseTask() throws Exception {
		this.scheduler.standby();
	}

	public void stopTask(boolean immediately) throws Exception {
		this.scheduler.shutdown(immediately);
	}

	public void resumeTask() throws Exception {
		this.scheduler.start();
	}

	public abstract void startTask(String taskCode) throws Exception;
}
