package com.cmweb.cognos8.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.cmweb.cognos8.BaseClassWrapper;
import com.cmweb.cognos8.CRNConnect;
import com.cmweb.cognos8.quartz.ITask;
import com.cmweb.cognos8.vo.TCmTimeTaskLogDtlVO;
import com.cmweb.cognos8.vo.TCmTimeTaskLogVO;
import com.cmweb.cognos8.vo.TCmTimeTaskVO;
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
	
	
	// 保存发送日志
	public void saveLog(TCmTimeTaskLogVO vo) throws Exception;
	
	//保存发送日志明细
	public void saveLogDtl(List<TCmTimeTaskLogDtlVO> list) throws Exception;
	
	
	//取得日志列表 
	public JSONObject getLogList(Map<String,Object> map,int offset ,int pagesize) throws Exception; 
	
	//取得日志明细列表 
	public JSONObject getLogDtlList(Map<String,Object> map,int offset ,int pagesize) throws Exception; 
	
	//取得定时任务
	public TCmTimeTaskVO  getTimeTask(String id)throws Exception; 
	
	//取得所有定时任务
	public List<TCmTimeTaskVO>  getAllTimeTask()throws Exception; 
	
	//保存定时任务
	public void  saveTimeTask(TCmTimeTaskVO  vo)throws Exception; 
	
	
	//取得定时任务列表 
	public JSONObject getTimeTaskList(Map<String,Object> map,int offset ,int pagesize) throws Exception; 
	
	//启动定时任务
	public void initTimeTasks() ;
}
