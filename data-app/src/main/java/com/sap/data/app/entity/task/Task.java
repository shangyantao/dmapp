package com.sap.data.app.entity.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sap.data.app.entity.IdEntity;

@Entity
@Table(name = "task_message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Task extends IdEntity {

	private String eventType;
	private String triggerTime;
	private String userId;
	private int runId;
	private int taskPriority;
	private String taskStatus;
	private String inputParas;
	private Date startTime;
	private Date endTime;
	private long executionTime;
	private List<TaskLog> listTaskLogs = new ArrayList<TaskLog>();

	// private User user;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/*
	 * @ManyToOne(cascade=CascadeType.ALL)
	 * 
	 * @JoinColumn(name="user_id") public User getUser() { return user; }
	 * 
	 * public void setUser(User user) { this.user = user; }
	 */

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "task")
	@OrderBy(value = "id DESC")
	public List<TaskLog> getListTaskLogs() {
		return listTaskLogs;
	}

	public void setListTaskLogs(List<TaskLog> listTaskLogs) {
		this.listTaskLogs = listTaskLogs;
	}

	public String getInputParas() {
		return inputParas;
	}

	public void setInputParas(String inputParas) {
		this.inputParas = inputParas;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
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

}
