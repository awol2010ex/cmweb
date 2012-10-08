package com.cmweb.cognos8.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;

@Entity
@Table(name = "T_CM_TIME_TASK_DTL")
@DataTransferObject(converter=BeanConverter.class)
public class TCmTimeTaskDtlVO  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1053715943705570919L;
	
	
	private String id ; //ID
	
	private String taskid ;// 定时任务ID
	
	private String reportid ; //报表ID
	
	private String reportName ;//报表名
	
	private String searchPath ; //搜索路径
	
	private String params ;//参数值
	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "taskid")
	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	@Column(name = "reportid")
	public String getReportid() {
		return reportid;
	}

	public void setReportid(String reportid) {
		this.reportid = reportid;
	}
	@Column(name = "reportname")
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	@Column(name = "searchpath")
	public String getSearchPath() {
		return searchPath;
	}

	public void setSearchPath(String searchPath) {
		this.searchPath = searchPath;
	}
	@Column(name = "params")
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
