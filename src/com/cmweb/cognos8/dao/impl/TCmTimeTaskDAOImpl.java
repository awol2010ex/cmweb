package com.cmweb.cognos8.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.cmweb.cognos8.dao.ITCmTimeTaskDAO;
import com.cmweb.cognos8.vo.TCmTimeTaskVO;
import com.cmweb.orm.hibernate.HibernateDao;

//定时任务DAO
@Repository
public class TCmTimeTaskDAOImpl extends HibernateDao<TCmTimeTaskVO, String>
		implements ITCmTimeTaskDAO {
	@Autowired
	@Qualifier("sqlSession")
	private SqlSession sqlSession;

	// 取得任务
	public TCmTimeTaskVO getTask(String taskCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ID", taskCode);
		return (TCmTimeTaskVO) sqlSession.selectOne(
				"com.cmweb.cognos8.getAllTimeTaskList", map);
	}

	// 取得定时任务列表
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTimeTaskList(Map<String, Object> map,
			int offset, int pagesize) throws Exception {
		return sqlSession.selectList("com.cmweb.cognos8.getTimeTaskList", map,
				new RowBounds(offset, pagesize));

	}

	// 取得定时任务行数
	public Integer getTimeTaskCount(Map<String, Object> map) throws Exception {
		return (Integer) sqlSession.selectOne(
				"com.cmweb.cognos8.getTimeTaskCount", map);
	}

	// 取得所有定时任务列表
	@SuppressWarnings("unchecked")
	public List<TCmTimeTaskVO> getAll() {
		return sqlSession.selectList("com.cmweb.cognos8.getAllTimeTaskList",
				new HashMap<String, Object>());
	}
}
