package com.cmweb.cognos8;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CRNConnectFactory {
	@Value("#{properties['cognos8.CM_URL']}")
	private String CM_URL;// congos 8 webservice 地址

	// 取得连接
	public CRNConnect Connect() {
		CRNConnect connect = new CRNConnect();
		connect.connectToCognosServer(CM_URL);// congos 8 webservice 地址

		return connect;
	}

}
