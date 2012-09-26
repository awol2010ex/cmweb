package com.cmweb.cognos8.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmweb.cognos8.BaseClassWrapper;
import com.cmweb.cognos8.CRNConnect;
import com.cmweb.cognos8.Email;
import com.cmweb.cognos8.dao.ITCmTimeTaskDAO;
import com.cmweb.cognos8.dao.ITCmTimeTaskLogDAO;
import com.cmweb.cognos8.dao.ITCmTimeTaskLogDtlDAO;
import com.cmweb.cognos8.quartz.ITask;
import com.cmweb.cognos8.quartz.TCmTimeTaskExecutor;
import com.cmweb.cognos8.service.ICognos8Service;
import com.cmweb.cognos8.vo.TCmTimeTaskLogDtlVO;
import com.cmweb.cognos8.vo.TCmTimeTaskLogVO;
import com.cmweb.cognos8.vo.TCmTimeTaskVO;
import com.cognos.developer.schemas.bibus._3.AddressSMTP;
import com.cognos.developer.schemas.bibus._3.AsynchRequest;
import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.OrderEnum;
import com.cognos.developer.schemas.bibus._3.PropEnum;
import com.cognos.developer.schemas.bibus._3.QueryOptions;
import com.cognos.developer.schemas.bibus._3.SearchPathMultipleObject;
import com.cognos.developer.schemas.bibus._3.SearchPathSingleObject;
import com.cognos.developer.schemas.bibus._3.Sort;
import com.cognos.developer.schemas.bibus._3.XmlEncodedXML;

//cognos 8 逻辑层操作
@Service
public class Cognos8ServiceImpl implements ICognos8Service {
	private final static Logger logger = LoggerFactory
			.getLogger(Cognos8ServiceImpl.class);
	@Autowired
	private ITCmTimeTaskDAO tCmTimeTaskDAO; // 定时任务DAO

	@Autowired
	private ITCmTimeTaskLogDAO tCmTimeTaskLogDAO;// 发送邮件日志DAO
	@Autowired
	private ITCmTimeTaskLogDtlDAO tCmTimeTaskLogDtlDAO;// 发送邮件日志明细DAO

	// 登陆cognos
	@Override
	public void quickLogon(CRNConnect connection, String namespace, String uid,
			String pwd) throws Exception {
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

		connection.getCMService().logon(new XmlEncodedXML(encodedCredentials),
				new SearchPathSingleObject[] {});
	}

	// 取得报表列表
	public BaseClass[] getChildren(CRNConnect connection, String searchPath)
			throws Exception {
		PropEnum props[] = new PropEnum[] { PropEnum.searchPath,
				PropEnum.defaultName, PropEnum.storeID };// 显示字段
		Sort sortOptions[] = { new Sort() };// 排序
		sortOptions[0].setOrder(OrderEnum.ascending);
		sortOptions[0].setPropName(PropEnum.defaultName);

		return connection.getCMService().query(
				new SearchPathMultipleObject(searchPath), props, sortOptions,
				new QueryOptions());
	}

	// 根据报表发邮件
	public String emailReport(CRNConnect connection, BaseClassWrapper report,
			String bodyText, String emailSubject, int emailFormat,
			AddressSMTP[] emails, AsynchRequest response) {
		return new Email().emailReport(connection, report, bodyText,
				emailSubject, emailFormat, emails, response);
	}

	private static Map<String, ITask> tasks = new HashMap<String, ITask>();// 当前定时任务列表

	// 停止并删除定时任务
	public synchronized void shutdown(String taskCode) throws Exception {
		// TODO Auto-generated method stub
		Object obj = tasks.get(taskCode);

		if (obj == null) {
			return;
		}

		ITask task = (ITask) obj;
		task.stopTask();
		tasks.remove(taskCode);
	}

	// 加到定时任务队列
	public synchronized void addTask(String taskScheduleId, ITask task) {
		tasks.put(taskScheduleId, task);
	}

	// 启动服务时初始化定时任务
	public void init() throws Exception {
		// TODO Auto-generated method stub

		List<TCmTimeTaskVO> list = tCmTimeTaskDAO.getAll();

		if (list != null && list.size() > 0) {

			for (TCmTimeTaskVO vo : list) {
				String taskName = vo.getTaskname();
				logger.info("开始启动定时任务--" + taskName);
				this.startTask(vo.getId(), vo.getCron());
				logger.info("结束启动定时任务--" + taskName);
			}
		}
	}

	// 启动定时任务
	public ITask startTask(String taskCode, String express) throws Exception {
		// TODO Auto-generated method stub
		ITask task = null;

		TCmTimeTaskVO svo = null;
		try {
			svo = tCmTimeTaskDAO.getTask(taskCode);// 取得任务信息
		} catch (Exception e) {
			throw new Exception(e);
		}
		if (svo != null) {
			// 启动任务
			task = getInstance(taskCode);
			task.startTask(svo.getCron());
			addTask(taskCode, task);
		}

		return null;
	}

	// 取得一个定时任务实例
	public ITask getInstance(String taskCode) throws Exception {

		ITask task = new TCmTimeTaskExecutor(taskCode);
		return task;
	}

	// 保存发送日志
	@Transactional
	public void saveLog(TCmTimeTaskLogVO vo) throws Exception {
		tCmTimeTaskLogDAO.save(vo);
	}

	// 保存发送日志明细
	@Transactional
	public void saveLogDtl(List<TCmTimeTaskLogDtlVO> list) throws Exception {
		tCmTimeTaskLogDtlDAO.saveAll(list);
	}

	// 取得日志列表
	public JSONObject getLogList(Map<String, Object> map, int offset,
			int pagesize) throws Exception {
		JSONObject result = new JSONObject();

		result.put("Total", tCmTimeTaskLogDAO.getLogCount(map));// 总行数
		result.put("Rows", JSONArray.fromObject(tCmTimeTaskLogDAO.getLogList(
				map, offset, pagesize)));// 当前页查询结构

		return result;
	}

	// 取得日志明细列表
	public JSONObject getLogDtlList(Map<String, Object> map, int offset,
			int pagesize) throws Exception {
		JSONObject result = new JSONObject();

		result.put("Total", tCmTimeTaskLogDtlDAO.getLogDtlCount(map));// 总行数
		result.put("Rows", JSONArray.fromObject(tCmTimeTaskLogDtlDAO
				.getLogDtlList(map, offset, pagesize)));// 当前页查询结构

		return result;
	}
	
	//取得定时任务
	public TCmTimeTaskVO  getTimeTask(String id)throws Exception{
		return tCmTimeTaskDAO.getTask(id);
		
	}
	
	//保存定时任务
	@Transactional
	public void  saveTimeTask(TCmTimeTaskVO  vo)throws Exception{
		tCmTimeTaskDAO.save(vo);
	}
	
	//取得定时任务列表 
	public JSONObject getTimeTaskList(Map<String,Object> map,int offset ,int pagesize) throws Exception{
		JSONObject result = new JSONObject();

		result.put("Total", tCmTimeTaskDAO.getTimeTaskCount(map));// 总行数
		result.put("Rows", JSONArray.fromObject(tCmTimeTaskDAO
				.getTimeTaskList(map, offset, pagesize)));// 当前页查询结构

		return result;
	}
}
