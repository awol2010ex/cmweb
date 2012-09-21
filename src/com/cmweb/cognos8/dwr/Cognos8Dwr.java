package com.cmweb.cognos8.dwr;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.directwebremoting.annotations.RemoteProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmweb.cognos8.BaseClassWrapper;
import com.cmweb.cognos8.CRNConnect;
import com.cmweb.cognos8.service.ICognos8Service;
import com.cognos.developer.schemas.bibus._3.AddressSMTP;
import com.cognos.developer.schemas.bibus._3.BaseClass;

//cognos8 操作相关
@RemoteProxy
public class Cognos8Dwr {
	private final static Logger logger = LoggerFactory
			.getLogger(Cognos8Dwr.class);

	@Autowired
	ICognos8Service cognos8Service;// 目录操作

	public String emailReport(String emails, String searchPath, int type,
			String body, String subject) {
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

				
				if(emails!=null  && !"".equals(emails)){
					
					
					String[] emailsTokens =emails.split(";");
					if(emailsTokens .length>0){
						
						//邮箱地址列表
						AddressSMTP[] smtpList =new AddressSMTP[emailsTokens .length];
						for(int i=0,s=emailsTokens.length;i<s;i++){
							smtpList[i]=new AddressSMTP(emailsTokens[i]);
						}
						
						// 发送邮件
						return cognos8Service.emailReport(connection, reportObject,
								body, subject, type,smtpList, null);
					}else{
						return "邮件地址不正确";
					}
				
				}else{
					return "邮件地址不正确";
				}
			} else {
				return "找不到报表";
			}

		} else

			return "连接超时或未登录";
	}
}
