package com.cmweb.cognos8.dao;

import com.cmweb.cognos8.vo.TCmTimeTaskVO;

//定时任务DAO接口
public interface ITCmTimeTaskDAO {
	// 取得任务
	public TCmTimeTaskVO getTask(String taskCode) throws Exception;
}
