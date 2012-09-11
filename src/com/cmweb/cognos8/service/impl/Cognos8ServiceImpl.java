package com.cmweb.cognos8.service.impl;

import org.springframework.stereotype.Service;

import com.cmweb.cognos8.CRNConnect;
import com.cmweb.cognos8.service.ICognos8Service;
import com.cognos.developer.schemas.bibus._3.SearchPathSingleObject;
import com.cognos.developer.schemas.bibus._3.XmlEncodedXML;
//cognos 8 逻辑层操作
@Service
public class Cognos8ServiceImpl implements ICognos8Service {
	//登陆cognos
	@Override
	public void quickLogon(CRNConnect connection, String namespace,
			String uid, String pwd) throws Exception {
		// TODO Auto-generated method stub
		// sn_dg_prm_sdk_method_contentManagerService_logon_start_1
		StringBuffer credentialXML = new StringBuffer();

		credentialXML.append("<credential>");

		credentialXML.append("<namespace>");
		credentialXML.append(namespace);
		credentialXML.append("</namespace>");

		credentialXML.append("<username>");
		credentialXML.append(uid);
		credentialXML.append("</username>");

		credentialXML.append("<password>");
		credentialXML.append(pwd);
		credentialXML.append("</password>");

		credentialXML.append("</credential>");

		String encodedCredentials = credentialXML.toString();

		connection.getCMService().logon(new XmlEncodedXML(encodedCredentials), new SearchPathSingleObject[] {});
	}

}
