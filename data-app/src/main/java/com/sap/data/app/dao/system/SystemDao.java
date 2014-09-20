package com.sap.data.app.dao.system;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sap.data.app.entity.system.System;

public interface SystemDao extends PagingAndSortingRepository<System, String> {

}
