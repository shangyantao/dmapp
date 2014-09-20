package com.sap.data.app.ws;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.data.app.entity.system.Agent;
import com.sap.data.app.service.system.SystemManager;
import com.sap.data.app.web.system.AgentContants;
import com.sap.data.app.ws.dto.AgentDTO;
import com.sap.data.ws.WsConstants;

@Component
@Path("/system")
public class SystemResourceService {

	private SystemManager systemManager;
	
	private static HashMap<String,Agent> agentMaps=new HashMap<String,Agent>();
	
	@PUT
	@Path("/agent")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML + WsConstants.CHARSET })
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML + WsConstants.CHARSET })
	public AgentDTO connAgent(AgentDTO agentDTO,final @Context HttpServletRequest req) {
		
		Agent agent=systemManager.getAgent(agentDTO.getAgentGUID());
		agent.setAgentStatus(AgentContants.AGENT_CONNECTIONED_STATUS);
		agent.setLastConnDate(new Date());
		systemManager.saveAgent(agent);
	/*	HttpSession ses=req.getSession();
		ses.setMaxInactiveInterval(60*2);
		if(!agentMaps.containsKey(agent.getAgentGUID())){
			agentMaps.put(agent.getAgentGUID(), agent);
		}
		ses.setAttribute("agents", agentMaps);*/
		//System.out.println("agent:"+agent.getAgentGUID());
		return agentDTO;

	}

	@Autowired
	public void setSystemManager(SystemManager systemManager) {
		this.systemManager = systemManager;
	}
	
}
