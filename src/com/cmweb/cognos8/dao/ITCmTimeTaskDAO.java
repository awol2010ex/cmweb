package com.cmweb.cognos8.dao;

import java.util.List;
import java.util.Map;

import com.cmweb.cognos8.vo.TCmTimeTaskVO;

//定时任务DAO接口
public interface ITCmTimeTaskDAO {
	// 取得任务
	public TCmTimeTaskVO getTask(String taskCode) throws Exception;
	
	//取得所有定时任务
	public List<TCmTimeTaskVO> getAll() throws Exception;
	
	// 保存任务
	public void save(TCmTimeTaskVO vo) throws Exception;
	
	//取得定时任务列表
	public List<Map<String,Object>> getTimeTaskList(Map<String,Object> map ,int offset  ,int pagesize) throws Exception;
	
	//取得定时任务行数
	public Integer getTimeTaskCount(Map<String,Object> map) throws Exception;
}
