package com.sap.data.app.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.sap.data.app.entity.account.User;

@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "DM_ACCOUNT_SYS_SAPUSER")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccountSysUser {

	private String userGUID;
	private Company company;
	private User account;
	private System system;
	private String sapUserId;
	private String language;
	private String comments;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "USER_GUID")
	public String getUserGUID() {
		return userGUID;
	}
	public void setUserGUID(String userGUID) {
		this.userGUID = userGUID;
	}
	
	@OneToOne
	@JoinColumn(name="COMPANY_GUID")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	@OneToOne
	@JoinColumn(name="ACCOUNT_ID")
	public User getAccount() {
		return account;
	}
	public void setAccount(User account) {
		this.account = account;
	}
	
	@OneToOne
	@JoinColumn(name="SYSTEM_GUID")
	public System getSystem() {
		return system;
	}
	public void setSystem(System system) {
		this.system = system;
	}
	public String getSapUserId() {
		return sapUserId;
	}
	public void setSapUserId(String sapUserId) {
		this.sapUserId = sapUserId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
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
	
	// 非持久化属性.
	@Transient
	public String getUserName() {
		return account.getName();
	}
	
	// 非持久化属性.
	@Transient
	public String getSystemDes() {
		return system.getSystemDes();
	}
	
}
