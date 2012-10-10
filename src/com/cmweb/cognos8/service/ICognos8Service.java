package com.cmweb.cognos8.service;

import net.sf.json.JSONArray;

import com.cmweb.cognos8.BaseClassWrapper;
import com.cmweb.cognos8.CRNConnect;
import com.cognos.developer.schemas.bibus._3.AddressSMTP;
import com.cognos.developer.schemas.bibus._3.AsynchRequest;
import com.cognos.developer.schemas.bibus._3.BaseClass;

//cognos 8 逻辑层操作
public interface ICognos8Service {

	public CRNConnect createConnect();// 创建连接
	// 登陆cognos

	public void quickLogon(CRNConnect connection, String namespace, String uid,
			String pwd) throws Exception;

	// 取得子节点
	public BaseClass[] getChildren(CRNConnect connection, String searchPath)
			throws Exception;

	// 根据报表发邮件
	public String emailReport(CRNConnect connection, BaseClassWrapper report,
			String bodyText, String emailSubject, int emailFormat,
			AddressSMTP[] emails, // 发送地址
			AddressSMTP[] ccemails,// 抄送地址
			AsynchRequest response, JSONArray params// 报表参数
	);

}
