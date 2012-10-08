package com.cmweb.cognos8.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import com.cmweb.cognos8.vo.TCmTimeTaskVO;
import com.cognos.developer.schemas.bibus._3.Analysis;
import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.Folder;
import com.cognos.developer.schemas.bibus._3.Pagelet;
import com.cognos.developer.schemas.bibus._3.Report;
import com.cognos.developer.schemas.bibus._3.ReportView;
import com.cognos.developer.schemas.bibus._3.Shortcut;
import com.cognos.developer.schemas.bibus._3.URL;
import com.cognos.developer.schemas.bibus._3._package;

//cognos 8相关 restful 服务类
@Controller
@RequestMapping(value = "/cognos8")
public class Cognos8Controller {
	private final static Logger logger = LoggerFactory
			.getLogger(Cognos8Controller.class);

	@Autowired
	ICognos8Service cognos8Service;// 目录操作

	// 子节点列表
	@RequestMapping(value = "/path/list")
	public void getChildren(HttpServletRequest request,
			HttpServletResponse response) {
		String searchPath = request.getParameter("searchPath");// 搜索路径

		/*
		 * 第一层 searchPath =/content/* 第二层 searchPath
		 * =/content/folder[@name='总经理组']/*
		 */
		CRNConnect connection = this.getConnect();// cognos8连接
		if (connection != null) {

			try {

				JSONArray result = new JSONArray();// 返回结果
				if (searchPath != null) {// 非根节点
					BaseClass[] queryList = cognos8Service.getChildren(
							connection, searchPath);// 查询子节点

					if (queryList != null && queryList.length > 0) {

						for (BaseClass c : queryList) {
							result.add(new JSONObject()
									.element("searchPath",
											c.getSearchPath().getValue())
									// searchPath
									.element("name",
											c.getDefaultName().getValue())
									// 节点名称
									.element("isexpand", false)
									// 不自动展开
									.element("children", new JSONArray())
									// 类型
									.element("className",
											c.getClass().getName())
									.element(
											"id",
											c.getStoreID().getValue()
													.getValue()));// ID
						}

					}
				} else {// 根节点
					result.add(new JSONObject()
							.element("searchPath", "/content")
							// searchPath
							.element("name", "公共文件夹")
							// 节点名称
							.element("isexpand", false)
							// 不自动展开
							.element("children", new JSONArray())
							// 类型
							.element("className",
									"com.cognos.developer.schemas.bibus._3.Folder")
							.element("id", "-1"));
				}

				response.getWriter().print(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("", e);
			}
		}
	}

	// 取得节点ICON
	public String getIconURL(HttpServletRequest request, BaseClass c) {
		if (c instanceof ReportView) { // 报表视图
			return request.getContextPath()
					+ "/static/images/icon_result_html_sub.gif";
		} else if (c instanceof Report) {// 报表
			return request.getContextPath()
					+ "/static/images/icon_result_html.gif";
		} else if (c instanceof Folder) {// 目录
			return request.getContextPath() + "/static/images/icon_folder.gif";
		} else if (c instanceof _package) {// 数据包
			return request.getContextPath() + "/static/images/icon_package.gif";
		} else if (c instanceof Shortcut) {// 链接
			return request.getContextPath()
					+ "/static/images/icon_result_html_sub_ref.gif";
		} else if (c instanceof URL) {// URL
			return request.getContextPath() + "/static/images/icon_url.gif";
		} else if (c instanceof Analysis) {// 分析
			return request.getContextPath()
					+ "/static/images/icon_ps_analysis.gif";
		} else if (c instanceof Pagelet) {// 页面
			return request.getContextPath() + "/static/images/icon_page.gif";
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

	// 子节点列表
	@RequestMapping(value = "/report/list")
	public void getChildren(HttpServletRequest request,
			HttpServletResponse response, int page, int pagesize) {
		String searchPath = request.getParameter("searchPath");// 搜索路径
		CRNConnect connection = this.getConnect();// cognos8连接

		if (connection != null) {
			/*
			 * 第一层 searchPath =/content/* 第二层 searchPath
			 * =/content/folder[@name='总经理组']/*
			 */
			try {
				JSONObject result = new JSONObject();

				JSONArray Rows = new JSONArray();// 返回结果列表
				if (searchPath != null) {// 非根节点
					BaseClass[] queryList = cognos8Service.getChildren(
							connection, searchPath);

					if (queryList != null && queryList.length > 0) {

						for (int i = (page * pagesize - pagesize); i < (page * pagesize)
								&& i < queryList.length; i++) {

							BaseClass c = queryList[i];// 节点
							Rows.add(new JSONObject()
									.element("searchPath",
											c.getSearchPath().getValue())
									// searchPath
									.element("reportName",
											c.getDefaultName().getValue())
									// 节点名称
									.element("isexpand", false)
									// 不自动展开
									.element("icon",
											this.getIconURL(request, c))
									// 图标
									.element("children", new JSONArray())
									// 类型
									.element("className",
											c.getClass().getName())
									.element(
											"id",
											c.getStoreID().getValue()
													.getValue()));// ID
						}
						result.put("Total", queryList.length);// 总行数
					} else {
						result.put("Total", 0);// 总行数
					}
				} else {// 根节点
					Rows.add(new JSONObject()
							.element("searchPath", "/content")
							// searchPath
							.element("reportName", "公共文件夹")
							// 节点名称
							.element("isexpand", false)
							// 不自动展开
							.element("children", new JSONArray())
							// 类型
							.element("className",
									"com.cognos.developer.schemas.bibus._3.Folder")
							.element(
									"icon",
									request.getContextPath()
											+ "/static/images/icon_folder.gif")// 图标
							.element("id", "-1"));

					result.put("Total", 1);// 总行数
				}

				result.put("Rows", Rows);// 查询结果列表

				response.getWriter().print(result.toString());// 输出
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("", e);
			}
		}

	}

	// 日志列表
	@RequestMapping(value = "/log/list")
	public void getLogList(HttpServletRequest request,
			HttpServletResponse response, int page, int pagesize) {

		String reportid = request.getParameter("reportid");// 报表ID
		JSONObject result = new JSONObject().element("Total", 0).element(
				"Rows", new JSONArray());
		if (reportid != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("reportid", reportid);

			// 分页查询结果
			try {
				result = cognos8Service.getLogList(map, (page - 1) * pagesize,
						pagesize);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("", e);
			}

		}
		try {
			response.getWriter().print(result.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}

	}

	// 日志明细列表
	@RequestMapping(value = "/log/dtl/list")
	public void getLogDtlList(HttpServletRequest request,
			HttpServletResponse response, int page, int pagesize) {

		String logid = request.getParameter("logid");// 日志ID
		JSONObject result = new JSONObject().element("Total", 0).element(
				"Rows", new JSONArray());
		if (logid != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("logid", logid);

			// 分页查询结果
			try {
				result = cognos8Service.getLogDtlList(map, (page - 1)
						* pagesize, pagesize);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("", e);
			}

		}
		try {
			response.getWriter().print(result.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}
	}

	// 定时任务修改页面
	@RequestMapping(value = "/timetask/edit")
	public String timeTaskEdit(HttpServletRequest request) {

		String id = request.getParameter("id");
		TCmTimeTaskVO bean = null;
		if (id != null) {
			try {
				bean = cognos8Service.getTimeTask(id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("", e);
			}
		}
		if (bean != null) {
			request.setAttribute("bean", bean);
		} else {
			request.setAttribute("bean", new TCmTimeTaskVO());
		}
		return "timetask/timeTaskEdit";
	}

	// 定时任务列表
	@RequestMapping(value = "/timetask/list")
	public void getTimeTaskList(HttpServletRequest request,
			HttpServletResponse response, int page, int pagesize) {

		JSONObject result = new JSONObject().element("Total", 0).element(
				"Rows", new JSONArray());
		Map<String, Object> map = new HashMap<String, Object>();

		// 分页查询结果
		try {
			result = cognos8Service.getTimeTaskList(map, (page - 1) * pagesize,
					pagesize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}

		try {
			response.getWriter().print(result.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}
	}
}
