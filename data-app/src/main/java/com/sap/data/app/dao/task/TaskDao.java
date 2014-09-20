package com.sap.data.app.dao.task;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sap.data.app.entity.task.Task;

public interface TaskDao extends PagingAndSortingRepository<Task, Long>{

	
}
