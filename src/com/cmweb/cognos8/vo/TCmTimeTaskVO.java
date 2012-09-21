package com.cmweb.cognos8.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;

@Entity
@Table(name = "T_CM_TIME_TASK")
@DataTransferObject(converter=BeanConverter.class)
public class TCmTimeTaskVO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4744767185880863218L;
	
	private String id;
	
	private String taskname ;//任务名
	
	private Timestamp createddatetime ;// 创建时间
	
	private String username ;
	
	private String password ;

	private String cron ;//定时表达式
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
}
