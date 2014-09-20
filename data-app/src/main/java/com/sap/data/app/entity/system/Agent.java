package com.sap.data.app.entity.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "DM_AGENT")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Agent {

	private String agentGUID;
	private String serverUrl;
	private Company company;
	private String agentDes;
	private String agentStatus;
	private Date lastConnDate;
	private String comments;
	private String agentHost;
	private String agentIP;
	@Transient
	private long unConnTime;
	
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "AGENT_GUID")
	public String getAgentGUID() {
		return agentGUID;
	}
	public void setAgentGUID(String agentGUID) {
		this.agentGUID = agentGUID;
	}
	@ManyToOne
	@JoinColumn(name="COMPANY_GUID")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Column(name = "AGENT_HOST")
	public String getAgentHost() {
		return agentHost;
	}
	public void setAgentHost(String agentHost) {
		this.agentHost = agentHost;
	}
	@Column(name = "AGENT_IP")
	public String getAgentIP() {
		return agentIP;
	}
	public void setAgentIP(String agentIP) {
		this.agentIP = agentIP;
	}
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public String getAgentDes() {
		return agentDes;
	}
	public void setAgentDes(String agentDes) {
		this.agentDes = agentDes;
	}
	public String getAgentStatus() {
		return agentStatus;
	}
	public void setAgentStatus(String agentStatus) {
		this.agentStatus = agentStatus;
	}
	public Date getLastConnDate() {
		return lastConnDate;
	}
	public void setLastConnDate(Date lastConnDate) {
		this.lastConnDate = lastConnDate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	// 非持久化属性.
	@Transient
	public String getCmyName() {
		return company.getCmyName();
	}
	
	@Transient
	public long getUnConnTime() {
		return unConnTime;
	}
	@Transient
	public void setUnConnTime(long unConnTime) {
		this.unConnTime = unConnTime;
	}
	
	

	
	
	
	
}
