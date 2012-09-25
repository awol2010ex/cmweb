package com.cmweb.cognos8.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
//发送邮件日志明细VO
@Entity
@Table(name = "T_CM_TIME_TASK_LOG_DTL")
@DataTransferObject(converter=BeanConverter.class)
public class TCmTimeTaskLogDtlVO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3237194745833332842L;

	private String id;
	
	private String logid ;//日志ID
	
	private String email ;//发送邮箱地址
	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "logid")
	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}
	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
