package com.cmweb.cognos8.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmweb.cognos8.vo.TCmTimeTaskVO;
//定时任务执行
public class TCmTimeTaskJob implements Job{
	private final static Logger logger = LoggerFactory
	.getLogger(TCmTimeTaskJob.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub

		TCmTimeTaskVO taskVO = (TCmTimeTaskVO)context.getJobDetail().getJobDataMap().get("taskVO");//定时任务ID
		
		logger.info("cron:"+taskVO.getCron());
	}

}
