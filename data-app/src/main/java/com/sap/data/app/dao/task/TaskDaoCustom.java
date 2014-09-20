package com.sap.data.app.dao.task;

import java.util.List;

import com.sap.data.app.entity.task.Task;

public interface TaskDaoCustom {

	List<Task> searchTasksBy(String userId, String taskId,String runId,String startTime,String endTime);
	List<Task> findTasksByStatus(String status);
	List<Task> findTasksforRS();
	List<Task> searchTaskByEventType(String eventType);
}
