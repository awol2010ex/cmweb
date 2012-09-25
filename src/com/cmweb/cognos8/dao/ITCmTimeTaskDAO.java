package com.cmweb.cognos8.dao;

import java.util.List;

import com.cmweb.cognos8.vo.TCmTimeTaskVO;

//定时任务DAO接口
public interface ITCmTimeTaskDAO {
	// 取得任务
	public TCmTimeTaskVO getTask(String taskCode) throws Exception;
	
	//取得所有定时任务
	public List<TCmTimeTaskVO> getAll() throws Exception;
}
