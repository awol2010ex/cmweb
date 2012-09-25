package com.cmweb.cognos8.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmweb.cognos8.dao.ITCmTimeTaskLogDtlDAO;
import com.cmweb.cognos8.vo.TCmTimeTaskLogDtlVO;
import com.cmweb.orm.hibernate.HibernateDao;

//发送邮件日志明细DAO
@Repository
public class TCmTimeTaskLogDtlDAOImpl extends
		HibernateDao<TCmTimeTaskLogDtlVO, String> implements
		ITCmTimeTaskLogDtlDAO {

}
