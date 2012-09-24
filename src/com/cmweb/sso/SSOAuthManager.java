package com.cmweb.sso;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.NamingEnumeration;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//SSO验证类
public class SSOAuthManager {
	private final static Logger logger = LoggerFactory
			.getLogger(SSOMD5Helper.class);
	public final static String BEAN_ID = "SSOAuthManager";
	private static int LDAP_DEFAULT_PORT = 389;// AD端口

	private static String ATTRIBUTE_USER_ACCOUNT = "SAMAccountName";// AD域用户属性

	private SqlSession ssoSqlSession;// SSO数据库ibatis

	// 密码验证
	public boolean authDB(String username, String password) {

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("login_id", username);
			map.put("password", SSOMD5Helper.hash_md5(password));
			int count = (Integer) ssoSqlSession.selectOne(
					"com.cmweb.sso.authDB", map);// 数据库密码验证

			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);

			return false;
		}

	}

	// 取得域名
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDomainName() {
		try {
			return ssoSqlSession.selectList("com.cmweb.sso.getDomainName");
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	// 取得验证服务器列表
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getSvrList(String DOMAIN_NAME) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("DOMAIN_NAME", DOMAIN_NAME);// 域名
			return ssoSqlSession.selectList("com.cmweb.sso.getSvrList", map);
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}

	}

	// AD域验证
	public boolean authAD(String username, String password) {
		List<Map<String, Object>> domainNameList = this.getDomainName();// 取得域名
		if (domainNameList != null && domainNameList.size() > 0) {// 逐台域名验证
			for (Map<String, Object> d : domainNameList) {
				String DOMAIN_NAME = (String) d.get("DOMAIN_NAME");// 域名

				List<Map<String, Object>> svrList = this
						.getSvrList(DOMAIN_NAME);// 验证服务器列表

				if (svrList != null && svrList.size() > 0) {// 逐台服务器验证
					for (Map<String, Object> svr : svrList) {
						String SVR_IP = (String) svr.get("SVR_IP");
						if (this.authenAD(DOMAIN_NAME, SVR_IP, username,
								password, null)) {
							return true;
						}
					}

				}
			}
		}

		return false;
	}

	// 单个服务器 AD域验证
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean authenAD(String DOMAIN_NAME, String SVR_IP,
			String p_CAS_User_Name, String p_CAS_User_Pswd, String paramString5) {

		logger.info("验证服务器:" + SVR_IP);

		String[] arrayOfString = { "cn", "sn", "givenName",
				"userPrincipalName", "canonicalName", "AdsPath" };
		String str1 = "(&(objectcategory=Person)(objectclass=user)("
				+ ATTRIBUTE_USER_ACCOUNT + "=" + p_CAS_User_Name + "))";

		SearchControls localSearchControls = new SearchControls();
		localSearchControls.setReturningAttributes(arrayOfString);

		localSearchControls.setSearchScope(2);

		DOMAIN_NAME = DOMAIN_NAME.trim().toLowerCase();
		String str2;
		if ((paramString5 == null) || (paramString5.equals("")))
			str2 = ("." + DOMAIN_NAME).replaceAll("\\.", ",DC=").substring(1);
		else {
			str2 = paramString5;
		}
		SVR_IP = SVR_IP.trim().toLowerCase();
		if ((SVR_IP == null) || ("".equals(SVR_IP))) {
			SVR_IP = DOMAIN_NAME;
		}
		Hashtable localHashtable = new Hashtable();
		localHashtable.put("java.naming.factory.initial",
				"com.sun.jndi.ldap.LdapCtxFactory");

		localHashtable.put("java.naming.provider.url", "LDAP://" + SVR_IP + ":"
				+ String.valueOf(LDAP_DEFAULT_PORT));
		localHashtable.put("java.naming.security.authentication", "simple");
		localHashtable.put("java.naming.security.principal", p_CAS_User_Name
				+ "@" + DOMAIN_NAME);
		localHashtable.put("java.naming.security.credentials", p_CAS_User_Pswd);

		Object localObject = null;
		InitialLdapContext localInitialLdapContext = null;
		try {
			localInitialLdapContext = new InitialLdapContext(localHashtable,
					null);

			NamingEnumeration localNamingEnumeration = localInitialLdapContext
					.search(str2, str1, localSearchControls);
			if (localNamingEnumeration.hasMoreElements()) {
				SearchResult localSearchResult = (SearchResult) localNamingEnumeration
						.next();
				if (localSearchResult != null)
					localObject = localSearchResult.getAttributes();

				if (localObject != null) {
					logger.info("", localObject);
				}
			}

			return true;
		} catch (Exception e) {
			logger.error("", e);
			return false;
		}

	}

	public SqlSession getSsoSqlSession() {
		return ssoSqlSession;
	}

	public void setSsoSqlSession(SqlSession ssoSqlSession) {
		this.ssoSqlSession = ssoSqlSession;
	}

	// 取得账户信息
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getStaffList(Map<String, Object> map)
			throws Exception {
		return ssoSqlSession.selectList("com.cmweb.sso.getStaffList", map);
	}

	// 用户搜索
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> searchUser(Map<String, Object> map)
			throws Exception {
		return ssoSqlSession.selectList("com.cmweb.sso.searchUser", map);
	}

	// 机构部门搜索
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> searchUnit(Map<String, Object> map)
			throws Exception {
		return ssoSqlSession.selectList("com.cmweb.sso.searchUnit", map);
	}
}
