package com.sap.data.app.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sap.data.app.dao.account.UserDao;
import com.sap.data.app.dao.system.AccountSysUserDao;
import com.sap.data.app.dao.system.AgentDao;
import com.sap.data.app.dao.system.CompanyDao;
import com.sap.data.app.dao.system.EventDao;
import com.sap.data.app.dao.system.SystemDao;
import com.sap.data.app.entity.account.User;
import com.sap.data.app.entity.system.AccountSysUser;
import com.sap.data.app.entity.system.Agent;
import com.sap.data.app.entity.system.Company;
import com.sap.data.app.entity.system.Event;
import com.sap.data.app.entity.system.System;

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class SystemManager {
	
	private UserDao userDao;
	private CompanyDao companyDao;
	private EventDao eventDao;
	private SystemDao systemDao;
	private AgentDao agentDao;
	private AccountSysUserDao asuDao;
	
	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}
	
	public List<Company> getAllCompany(){
		
		return (List<Company>) companyDao.findAll();
	}
	
	public List<Event> getAllEvent(){
		
		return (List<Event>) eventDao.findAll();
	}
	
	public List<System> getAllSystem() {
		
		return (List<System>) systemDao.findAll();
	}
	
	public List<Agent> getAllAgent() {
		return (List<Agent>) agentDao.findAll();
	}
	
	public List<AccountSysUser> getAllAccountSysUser() {
		return(List<AccountSysUser>) asuDao.findAll();
	}
	
	
	@Transactional(readOnly = false)
	public User getUser(Long id) {
		
		return userDao.findOne(id);
	}
	
	@Transactional(readOnly = false)
	public void saveCompany(Company entity){
		
		companyDao.save(entity);
	}
	
	@Transactional(readOnly = false)
	public Company getCompany(String companyId){
		
		return companyDao.findOne(companyId);
	}
	
	@Transactional(readOnly = false)
	public void deleteCompany(String companyId){
		
		companyDao.delete(companyId);
	}
	
	public Event getEvent(String id){
		
		return eventDao.findOne(id);
	}
	@Transactional(readOnly = false)
	public void saveEvent(Event entity){
		
		eventDao.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void deleteEvent(String eventId){
		
		eventDao.delete(eventId);
	}
	
	@Transactional(readOnly = false)
	public void saveSystem(System entity) {
		systemDao.save(entity);
	}
	
	@Transactional(readOnly = false)
	public System getSystem(String systemGUID) {
		
		return systemDao.findOne(systemGUID);
	}
	
	@Transactional(readOnly = false)
	public void deleteSystem(String systemId) {
		systemDao.delete(systemId);
	}
	
	@Transactional(readOnly = false)
	public Agent saveAgent(Agent agent) {
		return agentDao.save(agent);
	}
	
	@Transactional(readOnly = false)
	public void deleteAgent(String agentId) {
		agentDao.delete(agentId);
	}
	
	public Agent getAgent(String id){
		return agentDao.findOne(id);
	}
	
	
	@Transactional(readOnly = false)
	public void saveAccountSysUser(AccountSysUser asu) {
		asuDao.save(asu);
	}
	
	@Transactional(readOnly = false)
	public void deleteAccountSysUser(String userGuid) {
		asuDao.delete(userGuid);
	}
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Autowired
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
	
	@Autowired
	public void setEventDao(EventDao eventDao) {
		this.eventDao = eventDao;
	}
	
	@Autowired
	public void setSystemDao(SystemDao systemDao) {
		this.systemDao = systemDao;
	}
	
	@Autowired
	public void setAgentDao(AgentDao agentDao) {
		this.agentDao = agentDao;
	}
	
	@Autowired
	public void setAccountSysUserDao(AccountSysUserDao asuDao) {
		this.asuDao = asuDao;
	}
}
