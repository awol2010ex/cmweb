package com.cmweb.cognos8.quartz;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.cmweb.cognos8.vo.TCmTimeTaskVO;

//cognos8发邮件定时任务
public class TCmTimeTaskExecutor extends BaseTask {

	private TCmTimeTaskVO taskVO;

	public TCmTimeTaskExecutor(TCmTimeTaskVO taskVO) {
		this.taskVO = taskVO;
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

		JobDetail jobDetail = new JobDetail(taskVO.getId(),
				"cognos8.4 time email", TCmTimeTaskJob.class);
		JobDataMap map = new JobDataMap();
		// 注入定时任务ID
		map.put("taskVO", taskVO);
		jobDetail.setJobDataMap(map);

		CronTrigger cronTrigger = new CronTrigger(taskVO.getId(),
				"cognos8.4 time email cron");

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
