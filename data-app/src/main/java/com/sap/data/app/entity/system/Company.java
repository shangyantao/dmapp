package com.sap.data.app.entity.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "DM_COMPANY")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Company {
	
	private String companyGUID;
	private String cmyName;
	private String cmyDes;
	private String cmyStatus;
	private Date expirationDate;
	private String createdBy;
	private Date changedOn;
	private String changedBy;
	private String schemaServer;
	private String schemaName;
	private String schemaUser;
	private String schemaPwd;
	private String comments;
	//private List<Event> eventList = Lists.newArrayList();//有序的关联对象集合
	
	/*//多对多定义
		@ManyToMany
		@JoinTable(name = "DM_COMPANY_EVENT", joinColumns = { @JoinColumn(name = "COMPANY_GUID") }, inverseJoinColumns = { @JoinColumn(name = "EVENT_ID") })
		//Fecth策略定义
		@Fetch(FetchMode.SUBSELECT)
		//集合按id排序.
		@OrderBy("eventId")
		//集合中对象id的缓存.
		@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Event> getEventList() {
		return eventList;
	}
	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}*/
	
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name="COMPANY_GUID")
	public String getCompanyGUID() {
		return companyGUID;
	}
	public void setCompanyGUID(String companyGUID) {
		this.companyGUID = companyGUID;
	}
	public String getCmyName() {
		return cmyName;
	}
	public void setCmyName(String cmyName) {
		this.cmyName = cmyName;
	}
	public String getCmyDes() {
		return cmyDes;
	}
	public void setCmyDes(String cmyDes) {
		this.cmyDes = cmyDes;
	}
	public String getCmyStatus() {
		return cmyStatus;
	}
	public void setCmyStatus(String cmyStatus) {
		this.cmyStatus = cmyStatus;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getChangedOn() {
		return changedOn;
	}
	public void setChangedOn(Date changedOn) {
		this.changedOn = changedOn;
	}
	public String getChangedBy() {
		return changedBy;
	}
	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getSchemaServer() {
		return schemaServer;
	}
	public void setSchemaServer(String schemaServer) {
		this.schemaServer = schemaServer;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getSchemaUser() {
		return schemaUser;
	}
	public void setSchemaUser(String schemaUser) {
		this.schemaUser = schemaUser;
	}
	public String getSchemaPwd() {
		return schemaPwd;
	}
	public void setSchemaPwd(String schemaPwd) {
		this.schemaPwd = schemaPwd;
	}

	
	/**
	 * 公司对应event名称字符串, 多个事件名称用','分隔.
	 */
/*	//非持久化属性.
	@Transient
	public String getEventNames() {
		return Collections3.extractToString(eventList, "eventName", ", ");
	}*/
}
