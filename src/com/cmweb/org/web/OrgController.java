package com.cmweb.org.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmweb.sso.SSOAuthManager;

//机构相关操作展示类
@Controller
@RequestMapping(value = "/org")
public class OrgController {
	private final static Logger logger = LoggerFactory
			.getLogger(OrgController.class);
	@Autowired
	private SSOAuthManager ssoAuthManager;// SSO相关信息操作类

	// 取得机构列表
	@RequestMapping(value = "/list")
	public void getOrgList(HttpServletRequest request,
			HttpServletResponse response) {
		String pid = request.getParameter("pid");// 父节点ID

		Map<String, Object> map = new HashMap<String, Object>();
		if (pid != null) {
			map.put("PID", pid);// 父节点ID
		}

		// 搜索机构列表
		List<Map<String, Object>> orgList = null;
		try {
			orgList = ssoAuthManager.searchUnit(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}
		JSONArray result = new JSONArray();
		if (orgList != null && orgList.size() > 0) {

			for (Map<String, Object> m : orgList) {
				result.add(JSONObject.fromObject(m)
						.element("id", (String) m.get("ORG_CODE"))// 机构32位code
						.element("name", (String) m.get("NAME"))// 机构名
						.element("isexpand", false)
						// 不自动展开
						.element("children", new JSONArray()));
			}

		}

		try {// 输出机构列表
			response.getWriter().print(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}
	}
}
