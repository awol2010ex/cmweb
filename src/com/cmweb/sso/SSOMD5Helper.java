package com.cmweb.sso;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSOMD5Helper {
	private final static Logger logger = LoggerFactory
	.getLogger(SSOMD5Helper.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        System.out.println("dgd999_sysadmin");
        try {
			System.out.println(SSOMD5Helper.hash_md5("dgd999_sysadmin"));
			System.out.println("5D434DAA87A4897FCA9EE4BA81958402".equals(SSOMD5Helper.hash_md5("dgd999_sysadmin")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		}
        
	}

	public static String hash_md5(String paramString) throws Exception {
		if (paramString == null)
			return null;
		if (paramString.equals(""))
			return null;

		char[] arrayOfChar1 = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'A', 'B', 'C', 'D', 'E', 'F' };
		String str = null;
		try {
			byte[] arrayOfByte1 = paramString.getBytes();
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(arrayOfByte1);
			byte[] arrayOfByte2 = localMessageDigest.digest();
			int i = arrayOfByte2.length;
			char[] arrayOfChar2 = new char[i * 2];
			int j = 0;
			for (int k = 0; k < i; k++) {
				int m = arrayOfByte2[k];
				arrayOfChar2[(j++)] = arrayOfChar1[(m >>> 4 & 0xF)];
				arrayOfChar2[(j++)] = arrayOfChar1[(m & 0xF)];
			}
			str = new String(arrayOfChar2);
		} catch (Exception localException) {
			logger.error("",localException);
		}
		return str;
	}
}
