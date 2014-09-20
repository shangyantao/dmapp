package com.sap.data.app.entity.system;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.sap.data.app.entity.task.Task;

@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "DM_EVENT")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Event {

	private String eventId;
	private String eventName;
/*	private Task task;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="task_id")
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}*/
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	private String eventDes;
	private String comments;
	

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventDes() {
		return eventDes;
	}
	public void setEventDes(String eventDes) {
		this.eventDes = eventDes;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
