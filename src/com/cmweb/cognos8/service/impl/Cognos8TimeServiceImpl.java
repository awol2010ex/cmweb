package com.cmweb.cognos8.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmweb.cognos8.dao.ITCmTimeTaskDAO;
import com.cmweb.cognos8.dao.ITCmTimeTaskDtlDAO;
import com.cmweb.cognos8.quartz.ITask;
import com.cmweb.cognos8.quartz.TCmTimeTaskExecutor;
import com.cmweb.cognos8.service.ICognos8LogService;
import com.cmweb.cognos8.service.ICognos8Service;
import com.cmweb.cognos8.service.ICognos8TimeService;
import com.cmweb.cognos8.vo.TCmTimeTaskDtlVO;
import com.cmweb.cognos8.vo.TCmTimeTaskVO;
import com.cmweb.sso.SSOAuthManager;

//cognos8 定时任务逻辑层
@Service
public class Cognos8TimeServiceImpl implements ICognos8TimeService {
	private final static Logger logger = LoggerFactory
			.getLogger(Cognos8TimeServiceImpl.class);
	private static Map<String, ITask> tasks = new HashMap<String, ITask>();// 当前定时任务列表
	@Autowired
	private ITCmTimeTaskDAO tCmTimeTaskDAO; // 定时任务DAO

	@Autowired
	private ITCmTimeTaskDtlDAO tCmTimeTaskDtlDAO; // 定时任务明细DAO

	@Autowired
	private ICognos8LogService cognos8LogService;// 日志操作
	@Autowired
	private SSOAuthManager ssoAuthManager;// SSO相关信息操作类
	@Autowired
	private ICognos8Service cognos8Service;// 报表操作
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

			task = new TCmTimeTaskExecutor(svo, this.getAllTimeTaskDtlList(svo
					.getId()), cognos8LogService,cognos8Service,ssoAuthManager);// 取得定时任务实例
			task.startTask(svo.getCron());
			addTask(taskCode, task);
		}

		return null;
	}

	// 取得定时任务
	public TCmTimeTaskVO getTimeTask(String id) throws Exception {
		return tCmTimeTaskDAO.getTask(id);

	}

	// 保存定时任务
	@Transactional
	public void saveTimeTask(TCmTimeTaskVO vo) throws Exception {
		tCmTimeTaskDAO.save(vo);

	}

	// 取得定时任务列表
	public JSONObject getTimeTaskList(Map<String, Object> map, int offset,
			int pagesize) throws Exception {
		JSONObject result = new JSONObject();

		result.put("Total", tCmTimeTaskDAO.getTimeTaskCount(map));// 总行数
		result.put("Rows", JSONArray.fromObject(tCmTimeTaskDAO.getTimeTaskList(
				map, offset, pagesize)));// 当前页查询结构

		return result;
	}

	// 取得所有定时任务
	@Transactional
	public List<TCmTimeTaskVO> getAllTimeTask() throws Exception {
		return tCmTimeTaskDAO.getAll();
	}

	// 启动定时任务
	@PostConstruct
	public void initTimeTasks() {
		List<TCmTimeTaskVO> list = null;// 定时任务列表
		try {
			list = this.getAllTimeTask();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.error("", e1);
		}

		if (list != null && list.size() > 0) {

			for (int i = 0, s = list.size(); i < s; i++) {
				TCmTimeTaskVO vo = (TCmTimeTaskVO) list.get(i);

				// 防止重复加载
				Object obj = tasks.get(vo.getId());

				if (obj != null) {
					continue;
				}

				String taskName = vo.getTaskname();// 任务名称
				logger.info("开始启动定时任务--" + taskName);
				try {
					this.startTask(vo.getId(), vo.getCron());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("", e);
				}
				logger.info("结束启动定时任务--" + taskName);
			}
		}
	}

	// 删除定时任务
	@Transactional
	public void removeTimeTask(String taskCode) throws Exception {
		tCmTimeTaskDAO.delete(taskCode);
	}

	// 取得定时任务明细
	public List<TCmTimeTaskDtlVO> getAllTimeTaskDtlList(String taskId)
			throws Exception {
		return tCmTimeTaskDtlDAO.getAllTimeTaskDtlList(taskId);
	}

	// 保存定时任务明细
	@Transactional
	public void saveTimeTaskDtl(List<TCmTimeTaskDtlVO> list) throws Exception {
		tCmTimeTaskDtlDAO.saveAll(list);
	}

	// //删除定时任务明细
	@Transactional
	public void deleteTimeTaskDtl(String taskId) throws Exception {
		tCmTimeTaskDtlDAO.deleteTimeTaskDtl(taskId);
	}

}
