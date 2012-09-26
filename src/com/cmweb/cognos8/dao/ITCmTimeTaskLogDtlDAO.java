package com.cmweb.cognos8.dao;

import java.util.List;
import java.util.Map;

import com.cmweb.cognos8.vo.TCmTimeTaskLogDtlVO;
import com.cmweb.cognos8.vo.TCmTimeTaskLogVO;


//发送邮件日志明细DAO
public interface ITCmTimeTaskLogDtlDAO {

	//保存日志
	public void saveAll(final List<TCmTimeTaskLogDtlVO> list) throws Exception ;
	
	//查询日志明细
	public List<TCmTimeTaskLogVO> getLogDtlList(Map<String,Object> map ,int offset ,int pagesize) throws Exception ;
	//查询日志明细行数
	public  Integer getLogDtlCount(Map<String,Object> map ) throws Exception ;
}
