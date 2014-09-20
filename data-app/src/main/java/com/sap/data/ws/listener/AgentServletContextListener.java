package com.sap.data.ws.listener;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.sap.data.app.entity.system.Agent;
import com.sap.data.app.service.system.SystemManager;
import com.sap.data.app.web.system.AgentContants;

public class AgentServletContextListener implements
		ServletContextAttributeListener {

	private SystemManager systemManager;
	private WebApplicationContext ctx;

	@Override
	public void attributeAdded(ServletContextAttributeEvent scab) {

		System.out.println("attributeAdded");
		method(scab);
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent scab) {
		System.out.println("attributeRemoved");
		method(scab);
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent scab) {
		// TODO Auto-generated method stub
		System.out.println("attributeReplaced");
		method(scab);
	}

	public void method( ServletContextAttributeEvent scab){
		
		ServletContext sc = scab.getServletContext();
		Enumeration<String> enu = sc.getAttributeNames();
		while (enu.hasMoreElements()) {

			Object elem = sc.getAttribute(enu.nextElement());
			if (elem instanceof Agent) {
				if (ctx == null) {
					ctx = ContextLoader.getCurrentWebApplicationContext();
				}
				if (ctx != null && systemManager == null) {
					systemManager = (SystemManager) ctx
							.getBean("systemManager");
				}
				Agent agent = (Agent) elem;
				Calendar c = Calendar.getInstance();
				//c.setTimeInMillis(System.currentTimeMillis()
					//	- agent.getConnectDate());
				System.out
						.println("System.currentTimeMillis()-agent.getConnectDate()="
								+ c.get(Calendar.SECOND));
				if (c.get(Calendar.SECOND) > 60*2) {
					agent.setAgentStatus(AgentContants.AGENT_UNCONNECTION_STATUS);
					agent.setLastConnDate(new Date());
					systemManager.saveAgent(agent);
					System.out.println(agent.getAgentGUID()
							+ " updated unconnected status");
					sc.removeAttribute(agent.getAgentGUID());
				}
			}
		}
	}
}
