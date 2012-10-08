package com.cmweb.cognos8.quartz;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmweb.cognos8.vo.TCmTimeTaskDtlVO;
import com.cmweb.cognos8.vo.TCmTimeTaskVO;
//定时任务执行
public class TCmTimeTaskJob implements Job{
	private final static Logger logger = LoggerFactory
	.getLogger(TCmTimeTaskJob.class);
	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub

		TCmTimeTaskVO taskVO = (TCmTimeTaskVO)context.getJobDetail().getJobDataMap().get("taskVO");//定时任务VO
		
		 List<TCmTimeTaskDtlVO> dtlList = ( List<TCmTimeTaskDtlVO>)context.getJobDetail().getJobDataMap().get("dtlList");//定时任务DTL
		
		
		logger.info("cron:"+taskVO.getCron());//定时字符串
		
		
		if(dtlList!=null && dtlList.size()>0){
			for(TCmTimeTaskDtlVO dtlVO:dtlList){
				logger.info("ID:"+dtlVO.getId());
			}
		}
		
	}

}
