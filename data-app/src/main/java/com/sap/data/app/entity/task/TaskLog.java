package com.sap.data.app.entity.task;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sap.data.app.entity.IdEntity;

@Entity
@Table(name="task_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskLog extends IdEntity{
	
	private String logType;
	@Temporal(TemporalType.TIMESTAMP)
	private Date logTime;
	private String logDesc;
	private int runId;
	private Task task;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="task_id")
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	
	public String getLogDesc() {
		return logDesc;
	}
	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}

	
}
