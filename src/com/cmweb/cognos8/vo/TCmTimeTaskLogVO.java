package com.cmweb.cognos8.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;

@Entity
@Table(name = "T_CM_TIME_TASK_LOG")
@DataTransferObject(converter = BeanConverter.class)
public class TCmTimeTaskLogVO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7271715768230020911L;
	private String id;
	private String reportid;// 报表ID
	private String searchPath;// 搜索路径

	private String reportName;// 报表名

	private Timestamp createdDatetime;// 发送时间

	private String logresult;// 日志信息

	private String sender;// 发送人

	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "searchpath")
	public String getSearchPath() {
		return searchPath;
	}

	public void setSearchPath(String searchPath) {
		this.searchPath = searchPath;
	}

	@Column(name = "reportname")
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	@Column(name = "createddatetime")
	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}

	public void setCreatedDatetime(Timestamp createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	@Column(name = "logresult")
	public String getLogresult() {
		return logresult;
	}

	public void setLogresult(String logresult) {
		this.logresult = logresult;
	}

	@Column(name = "sender")
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	@Column(name = "reportid")
	public String getReportid() {
		return reportid;
	}

	public void setReportid(String reportid) {
		this.reportid = reportid;
	}

}
