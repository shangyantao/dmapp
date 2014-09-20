package com.sap.data.app.dao.system;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sap.data.app.entity.system.Agent;

public interface AgentDao extends PagingAndSortingRepository<Agent, String> {

}
