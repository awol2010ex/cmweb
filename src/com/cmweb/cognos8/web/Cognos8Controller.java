package com.cmweb.cognos8.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmweb.cognos8.CRNConnect;
import com.cmweb.cognos8.service.ICognos8Service;
import com.cognos.developer.schemas.bibus._3.BaseClass;
//cognos 8相关 restful 服务类
@Controller
@RequestMapping(value = "/cognos8")
public class Cognos8Controller {
	private final static Logger logger = LoggerFactory
	.getLogger(Cognos8Controller.class);
	
	@Autowired
	ICognos8Service ICognos8Service;//目录操作
	//子节点列表
	@RequestMapping(value = "/path/list")
	public  void  getChildren(HttpServletRequest request,	HttpServletResponse response) {
		String searchPath =request.getParameter("searchPath");//搜索路径
		
		if(searchPath!=null ){
			try {
				searchPath =new String(searchPath.getBytes("ISO-8859-1"),"GBK");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error("",e);
			}
		}
		/*  第一层 searchPath =/content/*
		 *  第二层 searchPath =/content/folder[@name='总经理组']/*
		 * 
		 * */
		try {
			BaseClass[]   queryList =ICognos8Service.getChildren(this.getConnect(), searchPath);
			
			if(queryList!= null  && queryList.length>0){
				for(BaseClass c :queryList){
					logger.error("",c);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		}
		
	}
	
	
	//取得Cognos8连接
	public CRNConnect getConnect(){
		Subject currentUser = SecurityUtils.getSubject();
		
		CRNConnect  connection=(CRNConnect)currentUser.getSession().getAttribute("connection");
		
		return connection;
		
	}
}
