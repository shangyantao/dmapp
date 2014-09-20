package com.sap.data.app.ws.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.sap.data.app.entity.system.Company;

@XmlRootElement
public class AgentDTO {

	private String agentGUID;
	private String serverUrl;
	private Company company;
	private String agentDes;
	private String agentStatus;
	private Date lastConnDate;
	private String comments;
	public String getAgentGUID() {
		return agentGUID;
	}
	public void setAgentGUID(String agentGUID) {
		this.agentGUID = agentGUID;
	}
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
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
	
	
}
