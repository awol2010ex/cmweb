package com.cmweb.cognos8.service;

import com.cmweb.cognos8.CRNConnect;

//cognos 8 逻辑层操作
public interface ICognos8Service {

	//登陆cognos
	public void quickLogon(
			CRNConnect connection,
				String namespace,
				String uid,
				String pwd)
				throws Exception;
}
