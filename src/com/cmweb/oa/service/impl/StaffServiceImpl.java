package com.cmweb.oa.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.cmweb.oa.service.IStaffService;
//OA人员操作
public class StaffServiceImpl implements IStaffService {
	protected SqlSession oaSqlSession; //OA数据库
	
	// 取得人员明细
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getStaffList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return oaSqlSession.selectList("com.cmweb.oa.getStaffList", map);
	}
	//内部登录名
	public String getInnerLoginId(String loginId){
		String _loginId=loginId;
		if(loginId.indexOf(".")!=-1){//店内
			_loginId=loginId.substring(loginId.indexOf(".")+1);
		}else{
			if(!"ADMIN".equals(loginId)){
				_loginId="MSAD$"+loginId;//厂内
			}
		}
		
		return _loginId;
	}
	public SqlSession getOaSqlSession() {
		return oaSqlSession;
	}

	public void setOaSqlSession(SqlSession oaSqlSession) {
		this.oaSqlSession = oaSqlSession;
	}

}
