package com.cmweb.cognos8.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.cmweb.cognos8.dao.ITCmTimeTaskLogDtlDAO;
import com.cmweb.cognos8.vo.TCmTimeTaskLogDtlVO;
import com.cmweb.cognos8.vo.TCmTimeTaskLogVO;
import com.cmweb.orm.hibernate.HibernateDao;

//发送邮件日志明细DAO
@Repository
public class TCmTimeTaskLogDtlDAOImpl extends
		HibernateDao<TCmTimeTaskLogDtlVO, String> implements
		ITCmTimeTaskLogDtlDAO {
	@Autowired
	@Qualifier("sqlSession")
	private SqlSession sqlSession;

	// 查询日志
	@SuppressWarnings("unchecked")
	public List<TCmTimeTaskLogVO> getLogDtlList(Map<String, Object> map,
			int offset, int pagesize) throws Exception {
		return sqlSession.selectList("com.cmweb.cognos8.getLogDtlList", map,
				new RowBounds(offset, pagesize));
	}

	// 查询日志行数
	public Integer getLogDtlCount(Map<String, Object> map) throws Exception {
		return (Integer) sqlSession.selectOne(
				"com.cmweb.cognos8.getLogDtlCount", map);
	}
}
