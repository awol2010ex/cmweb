package com.cmweb.cognos8.service;

import com.cmweb.cognos8.CRNConnect;
import com.cognos.developer.schemas.bibus._3.BaseClass;

//cognos 8 逻辑层操作
public interface ICognos8Service {

	//登陆cognos
	public void quickLogon(
			CRNConnect connection,
				String namespace,
				String uid,
				String pwd)
				throws Exception;
	
	//取得子节点
	public BaseClass[]  getChildren(CRNConnect connection,String searchPath ) throws Exception;
	
	
}
