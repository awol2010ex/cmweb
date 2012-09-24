package com.cmweb.cognos8.dwr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.directwebremoting.annotations.RemoteProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmweb.cognos8.BaseClassWrapper;
import com.cmweb.cognos8.CRNConnect;
import com.cmweb.cognos8.service.ICognos8Service;
import com.cmweb.sso.SSOAuthManager;
import com.cognos.developer.schemas.bibus._3.AddressSMTP;
import com.cognos.developer.schemas.bibus._3.BaseClass;

//cognos8 操作相关
@RemoteProxy
public class Cognos8Dwr {
	private final static Logger logger = LoggerFactory
			.getLogger(Cognos8Dwr.class);

	@Autowired
	ICognos8Service cognos8Service;// 目录操作
	@Autowired
	private SSOAuthManager ssoAuthManager;// SSO相关信息操作类

	public String emailReport(String emails, String searchPath, int type,
			String body, String subject, String orgs) {
		Subject currentUser = SecurityUtils.getSubject();

		CRNConnect connection = (CRNConnect) currentUser.getSession()
				.getAttribute("connection");
		if (connection != null) {
			// 取得报表对象
			BaseClass report = null;// 报表对象
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
						AddressSMTP[] smtpList = new AddressSMTP[len];
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
						// 发送邮件
						return cognos8Service.emailReport(connection,
								reportObject, body, subject, type, smtpList,
								null);
					} else {
						return "邮件地址不正确";
					}

				} else {
					return "邮件地址不正确";
				}
			} else {
				return "找不到报表";
			}

		} else

			return "连接超时或未登录";
	}
}
