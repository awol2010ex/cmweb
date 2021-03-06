package com.cmweb.cognos8.dwr;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.directwebremoting.annotations.RemoteProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmweb.cognos8.BaseClassWrapper;
import com.cmweb.cognos8.CRNConnect;
import com.cmweb.cognos8.ReportParameters;
import com.cmweb.cognos8.service.ICognos8LogService;
import com.cmweb.cognos8.service.ICognos8Service;
import com.cmweb.cognos8.service.ICognos8TimeService;
import com.cmweb.cognos8.vo.TCmTimeTaskDtlVO;
import com.cmweb.cognos8.vo.TCmTimeTaskLogDtlVO;
import com.cmweb.cognos8.vo.TCmTimeTaskLogVO;
import com.cmweb.cognos8.vo.TCmTimeTaskVO;
import com.cmweb.sso.SSOAuthManager;
import com.cmweb.utils.UUIDGenerator;
import com.cognos.developer.schemas.bibus._3.AddressSMTP;
import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.BaseParameter;

//cognos8 操作相关
@RemoteProxy
public class Cognos8Dwr {
	private final static Logger logger = LoggerFactory
			.getLogger(Cognos8Dwr.class);

	@Autowired
	ICognos8Service cognos8Service;// 目录操作
	@Autowired
	ICognos8LogService cognos8LogService;// 日志操作
	@Autowired
	ICognos8TimeService cognos8TimeService;// 定时任务操作
	@Autowired
	private SSOAuthManager ssoAuthManager;// SSO相关信息操作类

	public String emailReport(
			String emails,// 发送邮箱
			String ccemails,// 抄送邮箱
			String searchPath, int type, String body, String subject,
			String orgs, String params) {
		Subject currentUser = SecurityUtils.getSubject();// 会话

		// 取得cognos连接
		CRNConnect connection = (CRNConnect) currentUser.getSession()
				.getAttribute("connection");

		String returnStr = null;// 返回结果

		AddressSMTP[] smtpList = null;// 邮箱列表

		AddressSMTP[] ccsmtpList = null;// 抄送邮箱列表

		BaseClass report = null;// 报表对象
		if (connection != null) {
			// 取得报表对象

			try {
				BaseClass[] queryList = cognos8Service.getChildren(connection,
						searchPath);

				if (queryList != null && queryList.length > 0) {
					report = queryList[0];
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("", e);
			}
			if (report != null) {
				BaseClassWrapper reportObject = new BaseClassWrapper(report);

				if (emails != null && !"".equals(emails)) {

					// 发送邮箱地址
					String[] emailsTokens = emails.split(";");
					if (emailsTokens.length > 0) {

						int len = emailsTokens.length;// 发送人列表长度

						List<Map<String, Object>> orgStaffList = null;

						if (orgs != null && !"".equals(orgs.trim())) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("PID", orgs);// 选择的部门
							try {
								orgStaffList = ssoAuthManager.searchUser(map);// 选择部门的人员
							} catch (Exception e) {
								// TODO Auto-generated catch block
								logger.error("", e);
							}
						}
						if (orgStaffList != null && orgStaffList.size() > 0) {
							len += orgStaffList.size();
						}

						// 邮箱地址列表
						smtpList = new AddressSMTP[len];
						for (int i = 0, s = emailsTokens.length; i < s; i++) {// 填写的邮箱地址
							smtpList[i] = new AddressSMTP(emailsTokens[i]);
						}
						if (orgStaffList != null && orgStaffList.size() > 0) {
							for (int i = emailsTokens.length, s = len; i < s; i++) {// 自动添加的邮箱地址
								smtpList[i] = new AddressSMTP(
										(String) orgStaffList.get(
												i - emailsTokens.length).get(
												"EMAIL"));
							}
						}

						// 抄送邮箱地址
						String[] ccemailsTokens = ccemails.split(";");
						
						
					   len = ccemailsTokens.length;// 抄送人列表长度
						// 抄送邮箱地址列表
						ccsmtpList = new AddressSMTP[len];
						for (int i = 0, s = ccemailsTokens.length; i < s; i++) {// 填写的邮箱地址
							ccsmtpList[i] = new AddressSMTP(ccemailsTokens[i]);
						}

						// 发送邮件
						returnStr = cognos8Service.emailReport(connection,
								reportObject, body, subject, type, smtpList,// 发送邮箱
								ccsmtpList,// 抄送邮箱
								null

								, JSONArray.fromObject(params)// 报表参数
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

		} else {

			returnStr = "连接超时或未登录";
		}

		// 保存日志
		TCmTimeTaskLogVO log = new TCmTimeTaskLogVO();
		log.setId(UUIDGenerator.generate());
		log.setCreatedDatetime(new Timestamp(new Date().getTime()));
		log.setSender((String) currentUser.getSession().getAttribute(
				"j_username"));// 发送人
		if (report != null) {
			log.setReportName(report.getDefaultName().getValue());// 报表名
			log.setSearchPath(report.getSearchPath().getValue());// 搜索路径
			log.setReportid(report.getStoreID().getValue().getValue());// 报表ID
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

		//
		return returnStr;
	}

	// 取得定时任务明细
	public List<TCmTimeTaskDtlVO> getAllTimeTaskDtlList(String taskId) {
		try {
			return cognos8TimeService.getAllTimeTaskDtlList(taskId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);

			return null;
		}
	}

	// 保存定时任务
	public boolean saveTimeTask(TCmTimeTaskVO vo, List<TCmTimeTaskDtlVO> dtlList) {
		try {
			TCmTimeTaskVO bean = new TCmTimeTaskVO();
			if (vo.getId() == null || "".equals(vo.getId().trim())) {// 新建
				bean = vo;
				bean.setId(UUIDGenerator.generate());
				bean.setCreateddatetime(new Timestamp(new Date().getTime()));// 新建时间

			} else {// 修改
				try {
					bean = cognos8TimeService.getTimeTask(vo.getId().trim());// 取得定时任务信息
					bean.setCron(vo.getCron());// cron表达式
					bean.setLastupdateddatetime(new Timestamp(new Date()
							.getTime()));// 最后修改时间
					bean.setTaskname(vo.getTaskname());// 任务名
					bean.setSendmailaddr(vo.getSendmailaddr());// 发送邮箱地址
					bean.setSendmailorg(vo.getSendmailorg());// 发送部门ID
					bean.setSendmailorgname(vo.getSendmailorgname());// 发送部门名称
					bean.setSendmailtype(vo.getSendmailtype());// 发送 类型
					bean.setSendmailtypename(vo.getSendmailtypename());// 发送类型名称
					bean.setCcmailaddr(vo.getCcmailaddr());// 抄送邮箱地址
				} catch (Exception e) {
					logger.error("", e);
				}
			}

			Subject currentUser = SecurityUtils.getSubject();
			bean.setUsername((String) currentUser.getSession().getAttribute(
					"j_username"));// 登陆用户名
			bean.setPassword((String) currentUser.getSession().getAttribute(
					"j_password"));// 登陆密码

			cognos8TimeService.saveTimeTask(bean);// 保存到数据库

			// 删除已有明细
			cognos8TimeService.deleteTimeTaskDtl(bean.getId());
			// 保存明细
			if (dtlList != null && dtlList.size() > 0) {
				for (TCmTimeTaskDtlVO dtlVO : dtlList) {
					if (dtlVO.getReportid() == null) {
						dtlVO.setReportid(vo.getId());// 报表ID

					}
					dtlVO.setId(UUIDGenerator.generate());
					dtlVO.setTaskid(bean.getId());

				}
				cognos8TimeService.saveTimeTaskDtl(dtlList);

			}

			// 重启定时任务
			cognos8TimeService.shutdown(bean.getId());
			cognos8TimeService.startTask(bean.getId(), bean.getCron());
		} catch (Exception e) {
			logger.error("", e);
			return false;
		}
		return true;
	}

	// 删除定时任务
	public boolean removeTimeTask(String taskCode) {
		try {
			cognos8TimeService.deleteTimeTaskDtl(taskCode);// 删除定时任务明细
			cognos8TimeService.removeTimeTask(taskCode);// 删除定时任务

			// 关闭定时任务
			cognos8TimeService.shutdown(taskCode);
		} catch (Exception e) {
			logger.error("", e);
			return false;
		}
		return true;
	}

	// 取得报表参数列表
	public JSONArray getReportParamters(String searchPath) {

		Subject currentUser = SecurityUtils.getSubject();// 会话

		// 取得cognos连接
		CRNConnect connection = (CRNConnect) currentUser.getSession()
				.getAttribute("connection");
		BaseClass report = null;// 报表对象
		if (connection != null) {
			// 取得报表对象

			try {
				BaseClass[] queryList = cognos8Service.getChildren(connection,
						searchPath);

				if (queryList != null && queryList.length > 0) {
					report = queryList[0];
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("", e);
			}
		}

		if (report != null) {
			BaseClassWrapper reportObject = new BaseClassWrapper(report);

			try {// 查询出参数列表
				BaseParameter[] params = new ReportParameters()
						.getReportParameters(reportObject, connection);

				if (params != null && params.length > 0) {

					JSONArray result = new JSONArray();

					for (BaseParameter param : params) {
						result.add(new JSONObject().element("name",
								param.getName())// 参数名
								.element("type", param.getType().getValue())// 参数类型
						);

					}

					return result;
				}

			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				logger.error("", e);
			}
		}

		return null;
	}
}
