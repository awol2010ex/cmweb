package com.cmweb.cognos8.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cmweb.cognos8.BaseClassWrapper;
import com.cmweb.cognos8.CRNConnect;
import com.cmweb.cognos8.Email;
import com.cmweb.cognos8.quartz.ITask;
import com.cmweb.cognos8.quartz.TCmTimeTaskExecutor;
import com.cmweb.cognos8.service.ICognos8Service;
import com.cognos.developer.schemas.bibus._3.AddressSMTP;
import com.cognos.developer.schemas.bibus._3.AsynchRequest;
import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.OrderEnum;
import com.cognos.developer.schemas.bibus._3.PropEnum;
import com.cognos.developer.schemas.bibus._3.QueryOptions;
import com.cognos.developer.schemas.bibus._3.SearchPathMultipleObject;
import com.cognos.developer.schemas.bibus._3.SearchPathSingleObject;
import com.cognos.developer.schemas.bibus._3.Sort;
import com.cognos.developer.schemas.bibus._3.XmlEncodedXML;
//cognos 8 逻辑层操作
@Service
public class Cognos8ServiceImpl implements ICognos8Service {
	//登陆cognos
	@Override
	public void quickLogon(CRNConnect connection, String namespace,
			String uid, String pwd) throws Exception {
		// TODO Auto-generated method stub
		// sn_dg_prm_sdk_method_contentManagerService_logon_start_1
		StringBuffer credentialXML = new StringBuffer();

		credentialXML.append("<credential>");

		credentialXML.append("<namespace>");
		credentialXML.append(namespace);
		credentialXML.append("</namespace>");

		credentialXML.append("<username>");
		credentialXML.append(uid);
		credentialXML.append("</username>");

		credentialXML.append("<password>");
		credentialXML.append(pwd);
		credentialXML.append("</password>");

		credentialXML.append("</credential>");

		String encodedCredentials = credentialXML.toString();

		connection.getCMService().logon(new XmlEncodedXML(encodedCredentials), new SearchPathSingleObject[] {});
	}

	
	//取得报表列表
	public BaseClass[]  getChildren(CRNConnect connection,String searchPath ) throws Exception{
		PropEnum props[] =
			new PropEnum[] { PropEnum.searchPath, PropEnum.defaultName,PropEnum.storeID};//显示字段
		Sort sortOptions[] = { new Sort()};//排序
		sortOptions[0].setOrder(OrderEnum.ascending);
		sortOptions[0].setPropName(PropEnum.defaultName);
		
		return connection.getCMService().query(
				new SearchPathMultipleObject(searchPath),
				props,
				sortOptions,
				new QueryOptions());
	}
	// 根据报表发邮件
	public String emailReport(CRNConnect connection, BaseClassWrapper report,
			String bodyText, String emailSubject, int emailFormat,
			AddressSMTP[] emails, AsynchRequest response) {
		return new Email().emailReport(connection, report, bodyText,
				emailSubject, emailFormat, emails, response);
	}
	
	
	private static Map<String,ITask> tasks = new HashMap<String,ITask>();//当前定时任务列表
	
	//停止并删除定时任务
	public synchronized void shutdown(String taskCode) throws Exception {
		// TODO Auto-generated method stub
		Object obj = tasks.get(taskCode);

		if (obj == null) {
			return;
		}

		ITask task = (ITask) obj;
		task.stopTask();
		tasks.remove(taskCode);
	}
	
	
	//加到定时任务队列
	public synchronized void addTask(String taskScheduleId, ITask task) {
		tasks.put(taskScheduleId, task);
	}
	//初始化定时任务
	public void init() throws Exception{
		
	}
	
	//启动某一任务
	public ITask startTask(String taskCode, String express) throws Exception{
		return null;
	}
	
	//取得一个定时任务实例
	public ITask getInstance(String taskScheduleId) throws Exception {
		
		ITask task = new TCmTimeTaskExecutor(taskScheduleId);
		return task;
	}
	
}
