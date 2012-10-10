package com.cmweb.cognos8.quartz;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmweb.cognos8.BaseClassWrapper;
import com.cmweb.cognos8.CRNConnect;
import com.cmweb.cognos8.service.ICognos8LogService;
import com.cmweb.cognos8.service.ICognos8Service;
import com.cmweb.cognos8.vo.TCmTimeTaskDtlVO;
import com.cmweb.cognos8.vo.TCmTimeTaskLogDtlVO;
import com.cmweb.cognos8.vo.TCmTimeTaskLogVO;
import com.cmweb.cognos8.vo.TCmTimeTaskVO;
import com.cmweb.sso.SSOAuthManager;
import com.cmweb.utils.UUIDGenerator;
import com.cognos.developer.schemas.bibus._3.AddressSMTP;
import com.cognos.developer.schemas.bibus._3.BaseClass;

//定时任务执行
public class TCmTimeTaskJob implements Job {
	private final static Logger logger = LoggerFactory
			.getLogger(TCmTimeTaskJob.class);

	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub

		TCmTimeTaskVO taskVO = (TCmTimeTaskVO) context.getJobDetail()
				.getJobDataMap().get("taskVO");// 定时任务VO

		ICognos8LogService cognos8LogService = (ICognos8LogService) context
				.getJobDetail().getJobDataMap().get("cognos8LogService");// 日志操作
		ICognos8Service cognos8Service = (ICognos8Service) context
				.getJobDetail().getJobDataMap().get("cognos8Service");// 日志操作
		SSOAuthManager ssoAuthManager = (SSOAuthManager) context.getJobDetail()
				.getJobDataMap().get("ssoAuthManager");// SSO
		CRNConnect connection = null;
		try {
			// 建立cognos8 连接
			connection = cognos8Service.createConnect();
			cognos8Service.quickLogon(connection, "SSOAuth",
					taskVO.getUsername(), taskVO.getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("创建连接失败:", e);

			return;
		}

		if (connection != null) {

			List<TCmTimeTaskDtlVO> dtlList = (List<TCmTimeTaskDtlVO>) context
					.getJobDetail().getJobDataMap().get("dtlList");// 定时任务DTL
			logger.info("taskname:" + taskVO.getTaskname());// 定时任务名
			logger.info("cron:" + taskVO.getCron());// 定时字符串
			logger.info("--------------------------------");

			if (dtlList != null && dtlList.size() > 0) {
				for (TCmTimeTaskDtlVO dtlVO : dtlList) {
					logger.info("ID:" + dtlVO.getId());
					logger.info("REPORTID:" + dtlVO.getReportid());
					logger.info("SearchPath:" + dtlVO.getSearchPath());

					String returnStr = null;// 返回结果

					AddressSMTP[] smtpList = null;// 邮箱列表
					AddressSMTP[] ccsmtpList = null;// 抄送邮箱列表
					BaseClass report = null;// 报表对象

					try {
						BaseClass[] queryList = cognos8Service.getChildren(
								connection, dtlVO.getSearchPath());

						if (queryList != null && queryList.length > 0) {
							report = queryList[0];
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("", e);
					}
					if (report != null) {
						BaseClassWrapper reportObject = new BaseClassWrapper(
								report);

						if (taskVO.getSendmailaddr() != null
								&& !"".equals(taskVO.getSendmailaddr())) {

							String[] emailsTokens = taskVO.getSendmailaddr()
									.split(";");
							if (emailsTokens.length > 0) {

								int len = emailsTokens.length;// 发送人列表长度

								List<Map<String, Object>> orgStaffList = null;

								if (taskVO.getSendmailorg() != null
										&& !"".equals(taskVO.getSendmailorg()
												.trim())) {
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("PID", taskVO.getSendmailorg());// 选择的部门
									try {
										orgStaffList = ssoAuthManager
												.searchUser(map);// 选择部门的人员
									} catch (Exception e) {
										// TODO Auto-generated catch block
										logger.error("", e);
									}
								}
								if (orgStaffList != null
										&& orgStaffList.size() > 0) {
									len += orgStaffList.size();
								}

								// 邮箱地址列表
								smtpList = new AddressSMTP[len];
								for (int i = 0, s = emailsTokens.length; i < s; i++) {// 填写的邮箱地址
									smtpList[i] = new AddressSMTP(
											emailsTokens[i]);
								}
								if (orgStaffList != null
										&& orgStaffList.size() > 0) {
									for (int i = emailsTokens.length, s = len; i < s; i++) {// 自动添加的邮箱地址
										smtpList[i] = new AddressSMTP(
												(String) orgStaffList
														.get(i
																- emailsTokens.length)
														.get("EMAIL"));
									}
								}

								// 抄送邮箱地址
								String[] ccemailsTokens = taskVO
										.getCcmailaddr().split(";");
								// 抄送邮箱地址列表
								ccsmtpList = new AddressSMTP[len];
								for (int i = 0, s = ccemailsTokens.length; i < s; i++) {// 填写的邮箱地址
									ccsmtpList[i] = new AddressSMTP(
											ccemailsTokens[i]);
								}

								// 发送邮件
								returnStr = cognos8Service.emailReport(
										connection, reportObject, dtlVO
												.getReportName(), dtlVO
												.getReportName(), Integer
												.parseInt(taskVO
														.getSendmailtype()),
										smtpList,// 发送地址
										ccsmtpList// 抄送地址
										, null

										, JSONArray.fromObject(dtlVO
												.getParams())// 报表参数
										);
							} else {
								returnStr = "邮件地址不正确";
							}

						} else {
							returnStr = "邮件地址不正确";
						}
					} else {
						returnStr = "找不到报表";
					}

					// 保存日志
					TCmTimeTaskLogVO log = new TCmTimeTaskLogVO();
					log.setId(UUIDGenerator.generate());
					log.setCreatedDatetime(new Timestamp(new Date().getTime()));
					log.setSender(taskVO.getUsername());// 发送人
					if (report != null) {
						log.setReportName(report.getDefaultName().getValue());// 报表名
						log.setSearchPath(report.getSearchPath().getValue());// 搜索路径
						log.setReportid(report.getStoreID().getValue()
								.getValue());// 报表ID
					}
					log.setLogresult(returnStr);// 日志信息

					try {
						cognos8LogService.saveLog(log);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("", e);
					}
					// 保存日志明细
					if (smtpList != null && smtpList.length > 0) {
						List<TCmTimeTaskLogDtlVO> dtl_list = new ArrayList<TCmTimeTaskLogDtlVO>();
						for (AddressSMTP smtp : smtpList) {
							TCmTimeTaskLogDtlVO log_dtl = new TCmTimeTaskLogDtlVO();
							log_dtl.setId(UUIDGenerator.generate());
							log_dtl.setLogid(log.getId());
							log_dtl.setEmail(smtp.getValue());// 邮箱地址
							dtl_list.add(log_dtl);
						}
						try {
							cognos8LogService.saveLogDtl(dtl_list);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							logger.error("", e);
						}
					}
				}
			}
		}
	}

}
