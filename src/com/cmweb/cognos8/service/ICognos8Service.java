package com.cmweb.cognos8.service;

import com.cmweb.cognos8.BaseClassWrapper;
import com.cmweb.cognos8.CRNConnect;
import com.cmweb.cognos8.quartz.ITask;
import com.cognos.developer.schemas.bibus._3.AddressSMTP;
import com.cognos.developer.schemas.bibus._3.AsynchRequest;
import com.cognos.developer.schemas.bibus._3.BaseClass;

//cognos 8 逻辑层操作
public interface ICognos8Service {

	// 登陆cognos
	public void quickLogon(CRNConnect connection, String namespace, String uid,
			String pwd) throws Exception;

	// 取得子节点
	public BaseClass[] getChildren(CRNConnect connection, String searchPath)
			throws Exception;

	// 根据报表发邮件
	public String emailReport(CRNConnect connection, BaseClassWrapper report,
			String bodyText, String emailSubject, int emailFormat,
			AddressSMTP[] emails, AsynchRequest response);
	
	//初始化定时任务
	public void init() throws Exception;

	//停止定时任务
	public void shutdown(String taskCode) throws Exception;

	//启动某一任务
	public ITask startTask(String taskCode, String express) throws Exception;
	
	//添加某一个任务
	public void addTask(String taskCode, ITask task);
}
