package com.sap.data.app.dao.system;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sap.data.app.entity.system.Event;

public interface EventDao extends PagingAndSortingRepository<Event, String>{

}
