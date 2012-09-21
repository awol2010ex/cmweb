package com.cmweb.cognos8.quartz;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

//cognos8发邮件定时任务
public class TCmTimeTaskExecutor extends BaseTask {

	private String taskScheduleId;

	public TCmTimeTaskExecutor(String taskScheduleId) {
		this.taskScheduleId = taskScheduleId;
	}

	//覆盖启动任务方法,express 定时任务 cron表达式
	@Override
	public void startTask(String express) throws Exception {
		// TODO Auto-generated method stub

		if (express == null) {

			return;
		}

		SchedulerFactory schedulerFactory = new StdSchedulerFactory();

		this.scheduler = schedulerFactory.getScheduler();

		JobDetail jobDetail = new JobDetail(taskScheduleId,
				"cognos8.4 time email", TCmTimeTaskJob.class);
		JobDataMap map = new JobDataMap();
		// 注入定时任务ID
		map.put("taskScheduleId", taskScheduleId);
		jobDetail.setJobDataMap(map);

		CronTrigger cronTrigger = new CronTrigger(taskScheduleId,
				"bizImportTaskGroup");

		CronExpression cexp = new CronExpression(express);

		cronTrigger.setCronExpression(cexp);

		this.scheduler.scheduleJob(jobDetail, cronTrigger);

		this.scheduler.start();
	}

	//停止
	public void stopTask() throws Exception {
		if (this.scheduler != null) {
			this.scheduler.shutdown();
		}
	}
    //中止
	public void pauseTask() throws Exception {
		if (this.scheduler != null) {
			this.scheduler.standby();
		}
	}
	//立刻停止
	public void stopTask(boolean immediately) throws Exception {
		if (this.scheduler != null) {
			this.scheduler.shutdown(immediately);
		}
	}
    //恢复
	public void resumeTask() throws Exception {
		if (this.scheduler != null) {
			this.scheduler.start();
		}
	}

}
