package com.cmweb.cognos8.service.impl;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmweb.cognos8.dao.ITCmTimeTaskLogDAO;
import com.cmweb.cognos8.dao.ITCmTimeTaskLogDtlDAO;
import com.cmweb.cognos8.service.ICognos8LogService;
import com.cmweb.cognos8.vo.TCmTimeTaskLogDtlVO;
import com.cmweb.cognos8.vo.TCmTimeTaskLogVO;

//cognos8日志相关接口
@Service
public class Cognos8LogServiceImpl implements ICognos8LogService {
	
	@Autowired
	private ITCmTimeTaskLogDAO tCmTimeTaskLogDAO;// 发送邮件日志DAO
	@Autowired
	private ITCmTimeTaskLogDtlDAO tCmTimeTaskLogDtlDAO;// 发送邮件日志明细DAO
	
	
	// 保存发送日志
	@Transactional
	public void saveLog(TCmTimeTaskLogVO vo) throws Exception {
		tCmTimeTaskLogDAO.save(vo);
	}

	// 保存发送日志明细
	@Transactional
	public void saveLogDtl(List<TCmTimeTaskLogDtlVO> list) throws Exception {
		tCmTimeTaskLogDtlDAO.saveAll(list);
	}

	// 取得日志列表
	public JSONObject getLogList(Map<String, Object> map, int offset,
			int pagesize) throws Exception {
		JSONObject result = new JSONObject();

		result.put("Total", tCmTimeTaskLogDAO.getLogCount(map));// 总行数
		result.put("Rows", JSONArray.fromObject(tCmTimeTaskLogDAO.getLogList(
				map, offset, pagesize)));// 当前页查询结构

		return result;
	}

	// 取得日志明细列表
	public JSONObject getLogDtlList(Map<String, Object> map, int offset,
			int pagesize) throws Exception {
		JSONObject result = new JSONObject();

		result.put("Total", tCmTimeTaskLogDtlDAO.getLogDtlCount(map));// 总行数
		result.put("Rows", JSONArray.fromObject(tCmTimeTaskLogDtlDAO
				.getLogDtlList(map, offset, pagesize)));// 当前页查询结构

		return result;
	}

}
