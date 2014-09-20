package com.sap.data.app.dao.task;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.sap.data.app.entity.task.Task;

@Component
public class TaskDaoCustomImpl implements TaskDaoCustom {

	//private static final String QUERY_TASK_BY= "select t from Task t where t.userId=? and t.id=?";
	private static final String QUERY_TASK_BY_STATUS = "select t from Task t where t.taskStatus=?";
	private static final String QUERY_TASK_FOR_WS = "select t from Task t where t.taskStatus='0' order by t.taskPriority";
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Task> findTasksByStatus(String status) {

		List<Task> list = em.createQuery(QUERY_TASK_BY_STATUS)
				.setParameter(1, status).getResultList();

		return list;
	}

	@Override
	public List<Task> findTasksforRS() {

		List<Task> list = em.createQuery(QUERY_TASK_FOR_WS).getResultList();

		return list;
	}

	@Override
	public List<Task> searchTasksBy(String userId, String taskId,String runId,String startTime,String endTime) {
		//List<Task> list
		
		String QUERY_TASK_BY="select t from Task t where  1=1";
	
		if (StringUtils.isNotBlank(userId)){
			QUERY_TASK_BY+=" and t.userId='"+userId+"'";
		}
		
		if(StringUtils.isNotBlank(taskId)){
			QUERY_TASK_BY+=" and t.id="+taskId;
		}
		
		if(StringUtils.isNotBlank(runId)){
			QUERY_TASK_BY+=" and t.runId="+runId;
		}
		
		if(StringUtils.isNotBlank(taskId)){
			QUERY_TASK_BY+=" and t.startTime="+startTime;
		}
		if(StringUtils.isNotBlank(endTime)){
			QUERY_TASK_BY+=" and t.endTime="+endTime;
		}
		QUERY_TASK_BY+=" order by t.id desc";
		Query query = em.createQuery(QUERY_TASK_BY);
		return query.getResultList();
	}

	@Override
	public List<Task> searchTaskByEventType(String eventType) {
		String QUERY_TASK_BY="select t from Task t where  1=1";
		
		if (StringUtils.isNotBlank(eventType)){
			QUERY_TASK_BY+=" and t.eventType='"+eventType+"'";
		}
		
		Query query = em.createQuery(QUERY_TASK_BY);
		return query.getResultList();
	}

	
	
}
