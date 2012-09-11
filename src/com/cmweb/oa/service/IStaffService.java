package com.cmweb.oa.service;

import java.util.List;
import java.util.Map;

//OA人员操作
public interface IStaffService {
   public  List<Map<String,Object>> getStaffList(Map<String,Object> map) throws Exception;// 取得人员明细
 //内部登录名
	public String getInnerLoginId(String loginId);
}
