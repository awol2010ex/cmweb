package com.cmweb.cognos8.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.cognos.developer.schemas.bibus._3.Folder;
import com.cognos.developer.schemas.bibus._3.Report;
import com.cognos.developer.schemas.bibus._3.ReportView;

//cognos 8相关 restful 服务类
@Controller
@RequestMapping(value = "/cognos8")
public class Cognos8Controller {
	private final static Logger logger = LoggerFactory
			.getLogger(Cognos8Controller.class);

	@Autowired
	ICognos8Service ICognos8Service;// 目录操作

	// 子节点列表
	@RequestMapping(value = "/path/list")
	public void getChildren(HttpServletRequest request,
			HttpServletResponse response) {
		String searchPath = request.getParameter("searchPath");// 搜索路径

		/*
		 * 第一层 searchPath =/content/* 第二层 searchPath
		 * =/content/folder[@name='总经理组']/*
		 */
		try {

			JSONArray result = new JSONArray();// 返回结果
			if (searchPath != null) {// 非根节点
				BaseClass[] queryList = ICognos8Service.getChildren(
						this.getConnect(), searchPath);

				if (queryList != null && queryList.length > 0) {

					for (BaseClass c : queryList) {
						result.add(new JSONObject()
								.element("searchPath",
										c.getSearchPath().getValue())
								// searchPath
								.element("name", c.getDefaultName().getValue())
								// 节点名称
								.element("isexpand", false)
								// 不自动展开
								.element("icon", this.getIconURL(request, c))
								// 图标
								.element("children", new JSONArray())
								.element("id",
										c.getStoreID().getValue().getValue()));// ID
					}

				}
			} else {// 根节点
				result.add(new JSONObject().element("searchPath", "/content")
						// searchPath
						.element("name", "公共文件夹")
						// 节点名称
						.element("isexpand", false)
						// 不自动展开
						.element("children", new JSONArray())
						.element(
								"icon",
								request.getContextPath()
										+ "/static/images/icon_folder.gif")// 图标
						.element("id", "-1"));
			}

			response.getWriter().print(result.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}

	}

	// 取得节点ICON
	public String getIconURL(HttpServletRequest request, BaseClass c) {
		if (c instanceof ReportView) {
			return request.getContextPath() + "/static/images/icon_package.gif";
		} else if (c instanceof Report) {// 报表
			return request.getContextPath() + "/static/images/icon_report.gif";
		} else if (c instanceof Folder) {// 目录
			return request.getContextPath() + "/static/images/icon_folder.gif";
		}
		return null;
	}

	// 取得Cognos8连接
	public CRNConnect getConnect() {
		Subject currentUser = SecurityUtils.getSubject();

		CRNConnect connection = (CRNConnect) currentUser.getSession()
				.getAttribute("connection");

		return connection;

	}
}
