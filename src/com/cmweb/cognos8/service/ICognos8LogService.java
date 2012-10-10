package com.cmweb.cognos8.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.cmweb.cognos8.vo.TCmTimeTaskLogDtlVO;
import com.cmweb.cognos8.vo.TCmTimeTaskLogVO;

//cognos8日志相关接口
public interface ICognos8LogService {

	// 保存发送日志
	public void saveLog(TCmTimeTaskLogVO vo) throws Exception;

	// 保存发送日志明细
	public void saveLogDtl(List<TCmTimeTaskLogDtlVO> list) throws Exception;

	// 取得日志列表
	public JSONObject getLogList(Map<String, Object> map, int offset,
			int pagesize) throws Exception;

	// 取得日志明细列表
	public JSONObject getLogDtlList(Map<String, Object> map, int offset,
			int pagesize) throws Exception;

}
