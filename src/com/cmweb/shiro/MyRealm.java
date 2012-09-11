package com.cmweb.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmweb.sso.SSOAuthManager;

//自定义验证
public class MyRealm extends AuthorizingRealm {
	@Autowired
	protected SSOAuthManager ssoAuthManager;// SSO验证类

	/**
	 * 授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		// TODO Auto-generated method stub

		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		String userName = token.getUsername();// 登录名
		if (userName != null && !"".equals(userName)) {

			// 先进行SSO域验证
			if (!ssoAuthManager.authDB(userName,
					String.valueOf(token.getPassword()))
					&& !ssoAuthManager.authAD(userName,
							String.valueOf(token.getPassword()))) {

				throw new IncorrectCredentialsException();

			}else{
				
				//返回验证结果
				return new SimpleAuthenticationInfo(userName,
						String.valueOf(token.getPassword()), getName());
			}
		} else {

			throw new UnknownAccountException();
		}

	}

}
