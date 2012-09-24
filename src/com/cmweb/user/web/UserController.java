package com.cmweb.user.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmweb.cognos8.CRNConnect;
import com.cmweb.cognos8.CRNConnectFactory;
import com.cmweb.cognos8.service.ICognos8Service;
//用户相关操作展示类
@Controller
@RequestMapping(value = "/user")
public class UserController {
	private final static Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	private ICognos8Service cognos8Service;// cognos8 操作

	@Autowired
	private CRNConnectFactory crnConnectFactory;// 生成连接

	/* 登录 */
	@RequestMapping(value = "/login")
	public String login(String j_username, String j_password,
			HttpServletRequest request) {

		UsernamePasswordToken token = new UsernamePasswordToken(j_username,
				j_password);
		token.setRememberMe(true);

		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
		} catch (UnknownAccountException e) {
			request.setAttribute("error", "用户不存在");
			logger.error("", e);
			return "index";
		} catch (IncorrectCredentialsException e) {
			request.setAttribute("error", "验证错误");
			logger.error("", e);
			return "index";
		} catch (LockedAccountException e) {
			request.setAttribute("error", "用户被锁住");
			logger.error("", e);
			return "index";
		} catch (ExcessiveAttemptsException e) {
			request.setAttribute("error", "多次登录不成功");
			logger.error("", e);
			return "index";
		} catch (AuthenticationException e) {
			request.setAttribute("error", "验证错误");
			logger.error("", e);
			return "index";
		}

		String cognos8_str = null;

		try {// cognos8登陆
			CRNConnect connection = crnConnectFactory.createConnect();
			cognos8Service.quickLogon(connection, "SSOAuth", j_username,
					j_password);
			cognos8_str = "cognos 8 登陆成功";
			currentUser.getSession().setAttribute("connection", connection);//存入连接到会话
			
			currentUser.getSession().setAttribute("j_username", j_username);//存入用户名到会话
			currentUser.getSession().setAttribute("j_password", j_password);//存入密码到会话
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
			cognos8_str = "cognos 8 登陆失败";
		}
		if (cognos8_str != null) {
			request.setAttribute("cognos8_str", cognos8_str);
		}
		return "main";
	}
}
