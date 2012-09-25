package com.cmweb.cognos8.dao;

import java.util.List;

import com.cmweb.cognos8.vo.TCmTimeTaskLogDtlVO;


//发送邮件日志明细DAO
public interface ITCmTimeTaskLogDtlDAO {

	//保存日志
	public void saveAll(final List<TCmTimeTaskLogDtlVO> list) throws Exception ;
}
