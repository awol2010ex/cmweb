package com.cmweb.cognos8.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmweb.cognos8.dao.ITCmTimeTaskDAO;
import com.cmweb.cognos8.vo.TCmTimeTaskVO;
import com.cmweb.orm.hibernate.HibernateDao;

//定时任务DAO
@Repository
public class TCmTimeTaskDAOImpl extends HibernateDao<TCmTimeTaskVO, String>
		implements ITCmTimeTaskDAO {

	// 取得任务
	public TCmTimeTaskVO getTask(String taskCode) throws Exception {
		return this.get(taskCode);
	}
}
