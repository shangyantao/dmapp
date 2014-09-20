package com.sap.data.ws.listener;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.sap.data.app.entity.system.Agent;
import com.sap.data.app.service.system.SystemManager;
import com.sap.data.app.web.system.AgentContants;

public class AgentSessionListener implements HttpSessionListener {

	private SystemManager systemManager;
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		HttpSession ses=se.getSession();
		System.out.println("session created.................."+ses.getId());
		
		/*Agent agent=(Agent)ses.getAttribute("agent");
		if(agent!=null){
			System.out.println(agent.getAgentGUID()+"session Created");	
		}else{
			System.out.println(agent);
		}*/
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		HttpSession ses=se.getSession();
		/*Agent agent=(Agent)ses.getAttribute("agent");
		if(agent!=null){
			
			if(systemManager==null){
				WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
				systemManager=(SystemManager)ctx.getBean("systemManager");
			}
			agent.setAgentStatus(AgentContants.AGENT_UNCONNECTION_STATUS);
			agent.setLastConnDate(new Date());
			systemManager.saveAgent(agent);
		}*/
		System.out.println("session destroyed.................."+ses.getId());
		
	}

	
}
