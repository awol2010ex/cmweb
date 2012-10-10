package com.cmweb.cognos8.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.cmweb.cognos8.dao.ITCmTimeTaskDtlDAO;
import com.cmweb.cognos8.vo.TCmTimeTaskDtlVO;
import com.cmweb.orm.hibernate.HibernateDao;

//定时任务明细DAO
@Repository
public class TCmTimeTaskDtlDAOImpl extends
		HibernateDao<TCmTimeTaskDtlVO, String> implements ITCmTimeTaskDtlDAO {

	@Autowired
	@Qualifier("sqlSession")
	private SqlSession sqlSession;

	// 删除明细
	public void deleteTimeTaskDtl(String taskId) throws Exception {
		sqlSession.delete("com.cmweb.cognos8.deleteTimeTaskDtl", taskId);
	}

	// 取得定时任务明细
	@SuppressWarnings("unchecked")
	public List<TCmTimeTaskDtlVO> getAllTimeTaskDtlList(String taskId)
			throws Exception {
		return sqlSession.selectList("com.cmweb.cognos8.getAllTimeTaskDtlList",
				taskId);
	}
}
