package com.cmweb.cognos8.dao;

import java.util.List;
import java.util.Map;

import com.cmweb.cognos8.vo.TCmTimeTaskLogVO;

//发送邮件日志DAO
public interface ITCmTimeTaskLogDAO {

	// 保存日志
	public void save(TCmTimeTaskLogVO vo) throws Exception;

	// 查询日志
	public List<TCmTimeTaskLogVO> getLogList(Map<String, Object> map,
			int offset, int pagesize) throws Exception;

	// 查询日志行数
	public Integer getLogCount(Map<String, Object> map) throws Exception;
}
