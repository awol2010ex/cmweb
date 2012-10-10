package com.cmweb.cognos8.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;

//定时任务VO
@Entity
@Table(name = "T_CM_TIME_TASK")
@DataTransferObject(converter = BeanConverter.class)
public class TCmTimeTaskVO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4744767185880863218L;

	private String id;

	private String taskname;// 任务名

	private Timestamp createddatetime;// 创建时间

	private Timestamp lastupdateddatetime;// 最后修改时间

	private String username;

	private String password;

	private String cron;// 定时表达式

	private String sendmailtype; // 发送邮件类型（HTML,XLS,CSV...）

	private String sendmailtypename; // 发送邮件类型名（HTML,XLS,CSV...）

	private String sendmailaddr;// 发送邮箱地址(分号分隔)

	private String sendmailorg;// 发送部门ID(逗号分隔)

	private String sendmailorgname;// 发送部门名称(逗号分隔)

	private String ccmailaddr;// 抄送邮箱地址(分号分隔)

	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "taskname")
	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	@Column(name = "createddatetime")
	public Timestamp getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Timestamp createddatetime) {
		this.createddatetime = createddatetime;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "cron")
	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	@Column(name = "lastupdateddatetime")
	public Timestamp getLastupdateddatetime() {
		return lastupdateddatetime;
	}

	public void setLastupdateddatetime(Timestamp lastupdateddatetime) {
		this.lastupdateddatetime = lastupdateddatetime;
	}

	@Column(name = "sendmailtype")
	public String getSendmailtype() {
		return sendmailtype;
	}

	public void setSendmailtype(String sendmailtype) {
		this.sendmailtype = sendmailtype;
	}

	@Column(name = "sendmailaddr")
	public String getSendmailaddr() {
		return sendmailaddr;
	}

	public void setSendmailaddr(String sendmailaddr) {
		this.sendmailaddr = sendmailaddr;
	}

	@Column(name = "sendmailorg")
	public String getSendmailorg() {
		return sendmailorg;
	}

	public void setSendmailorg(String sendmailorg) {
		this.sendmailorg = sendmailorg;
	}

	@Column(name = "sendmailorgname")
	public String getSendmailorgname() {
		return sendmailorgname;
	}

	public void setSendmailorgname(String sendmailorgname) {
		this.sendmailorgname = sendmailorgname;
	}

	@Column(name = "sendmailtypename")
	public String getSendmailtypename() {
		return sendmailtypename;
	}

	public void setSendmailtypename(String sendmailtypename) {
		this.sendmailtypename = sendmailtypename;
	}

	@Column(name = "ccmailaddr")
	public String getCcmailaddr() {
		return ccmailaddr;
	}

	public void setCcmailaddr(String ccmailaddr) {
		this.ccmailaddr = ccmailaddr;
	}

}
