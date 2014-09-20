package com.sap.data.app.dao.task;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sap.data.app.entity.task.TaskLog;

public interface TaskLogDao extends PagingAndSortingRepository<TaskLog, Long>{

}
