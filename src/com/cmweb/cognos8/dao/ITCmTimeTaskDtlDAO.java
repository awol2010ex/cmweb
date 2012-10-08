package com.cmweb.cognos8.dao;

import java.util.List;

import com.cmweb.cognos8.vo.TCmTimeTaskDtlVO;

//定时任务明细DAO接口
public interface ITCmTimeTaskDtlDAO {
	//保存定时任务明细
	public void saveAll(final List<TCmTimeTaskDtlVO> list) throws Exception ;
	//删除明细
	public void deleteTimeTaskDtl(String taskId) throws Exception ;
	
	//取得定时任务明细
	public List<TCmTimeTaskDtlVO>  getAllTimeTaskDtlList(String taskId) throws Exception ;
}
