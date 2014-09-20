package com.sap.dm.agent.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TaskDTO {

	private Long id;
	private String eventType;
	private int userId;
	private int runId;
	private String triggerTime;
	private int taskPriority;
	private String taskStatus;
	private String inputParas;
	private Date startTime;
	private Date endTime;
	private long executionTime;
	private String companyId;
	
	private List<TaskLogDTO> listTaskLogs=new ArrayList<TaskLogDTO>();
	
	
	public List<TaskLogDTO> getListTaskLogs() {
		return listTaskLogs;
	}
	public void setListTaskLogs(List<TaskLogDTO> listTaskLogs) {
		this.listTaskLogs = listTaskLogs;
	}
	
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public String getTriggerTime() {
		return triggerTime;
	}
	public void setTriggerTime(String triggerTime) {
		this.triggerTime = triggerTime;
	}
	public int getTaskPriority() {
		return taskPriority;
	}
	public void setTaskPriority(int taskPriority) {
		this.taskPriority = taskPriority;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getInputParas() {
		return inputParas;
	}
	public void setInputParas(String inputParas) {
		this.inputParas = inputParas;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public long getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
}
