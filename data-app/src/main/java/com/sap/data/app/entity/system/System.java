package com.sap.data.app.entity.system;


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
@Table(name = "DM_SYSTEM")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class System {

	private String systemGUID;
	private Company company;
	private Agent agent;
	private String systemDes;
	private String comments;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "SYSTEM_GUID")
	public String getSystemGUID() {
		return systemGUID;
	}
	public void setSystemGUID(String systemGUID) {
		this.systemGUID = systemGUID;
	}
	@ManyToOne
	@JoinColumn(name="COMPANY_GUID")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	@ManyToOne
	@JoinColumn(name="agent_GUID")
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public String getSystemDes() {
		return systemDes;
	}
	public void setSystemDes(String systemDes) {
		this.systemDes = systemDes;
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
	public String getAgentGUID() {
		return this.agent.getAgentGUID();
	}
}
