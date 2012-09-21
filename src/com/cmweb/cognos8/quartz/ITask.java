package com.cmweb.cognos8.quartz;
//定时任务接口
public interface ITask {
	public void startTask(String  taskCode) throws Exception;//启动任务
	
	public void stopTask() throws Exception;//停止任务
	

	  public void stopTask(boolean immediately) throws Exception;//停止任务
	  
	  public void pauseTask() throws java.lang.Exception;//中止任务
	  
	  public void resumeTask() throws java.lang.Exception;//重启任务
}
