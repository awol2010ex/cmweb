package com.cmweb.cognos8.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.cmweb.cognos8.quartz.ITask;
import com.cmweb.cognos8.vo.TCmTimeTaskDtlVO;
import com.cmweb.cognos8.vo.TCmTimeTaskVO;

//cognos8 定时任务逻辑层
public interface ICognos8TimeService {

	//初始化定时任务
	public void init() throws Exception;

	//停止定时任务
	public void shutdown(String taskCode) throws Exception;

	//启动某一任务
	public ITask startTask(String taskCode, String express) throws Exception;
	
	//添加某一个任务
	public void addTask(String taskCode, ITask task);
	
	
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
	
	//删除定时任务
	public void removeTimeTask(String taskCode) throws Exception;
	
	
	//保存定时任务明细
	public void saveTimeTaskDtl(List<TCmTimeTaskDtlVO> list) throws Exception;
	
////删除定时任务明细
	public void deleteTimeTaskDtl(String taskId) throws Exception;
	//取得定时任务明细
	public List<TCmTimeTaskDtlVO>  getAllTimeTaskDtlList(String taskId) throws Exception ;
}
