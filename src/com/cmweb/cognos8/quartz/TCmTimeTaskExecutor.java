package com.cmweb.cognos8.quartz;

import java.util.List;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.cmweb.cognos8.vo.TCmTimeTaskDtlVO;
import com.cmweb.cognos8.vo.TCmTimeTaskVO;

//cognos8发邮件定时任务
public class TCmTimeTaskExecutor extends BaseTask {

	private TCmTimeTaskVO taskVO;//定时任务信息对象
	
	private List<TCmTimeTaskDtlVO> dtlList;//定时任务信息对象

	public TCmTimeTaskExecutor(TCmTimeTaskVO taskVO, List<TCmTimeTaskDtlVO> dtlList) {
		this.taskVO = taskVO;
		this.dtlList =dtlList;
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
		map.put("dtlList", dtlList);
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

	public TCmTimeTaskVO getTaskVO() {
		return taskVO;
	}

	public void setTaskVO(TCmTimeTaskVO taskVO) {
		this.taskVO = taskVO;
	}

	public List<TCmTimeTaskDtlVO> getDtlList() {
		return dtlList;
	}

	public void setDtlList(List<TCmTimeTaskDtlVO> dtlList) {
		this.dtlList = dtlList;
	}

}
