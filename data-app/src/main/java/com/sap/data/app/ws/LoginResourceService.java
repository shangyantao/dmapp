package com.sap.data.app.ws;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.data.app.entity.account.User;
import com.sap.data.app.service.system.SystemManager;
import com.sap.data.core.rest.RsResponse;
import com.sap.data.ws.WsConstants;


@Component
@Path("/login")
public class LoginResourceService {
	
	
	private SystemManager systemManager;
	
	@Autowired
	public void setSystemManager(SystemManager systemManager) {
		this.systemManager = systemManager;
	}
	/**
	 * 获取所有用户, 演示与Shiro结合的方法级权限检查.
	 */
	@GET
	@Path("/login")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + WsConstants.CHARSET })
	public Response login(@QueryParam("companyId") String companyId,@QueryParam("agentId") String agentId) {
		
		if(StringUtils.isBlank(companyId) || StringUtils.isBlank(agentId)){
			String message = "companyId is blank or agentId is blank!";
			throw RsResponse.buildException(Status.NOT_FOUND, message);
		}
			
			/*Agent agent=systemManager.getAgent(agentId);
			agent.setAgentStatus(AgentContants.AGENT_CONNECTIONED_STATUS);
			agent.setLastConnDate(new Date());
			systemManager.saveAgent(agent);*/
			
			Subject subject = SecurityUtils.getSubject();
	        Session session = subject.getSession();
	        System.out.println(" session.getId():"+ session.getId());
	        User user=(User)session.getAttribute("CURRENT_USER_KEY");
	        String message;
	        System.out.println("login session:"+session);
	        if(user!=null){
	        	session.setAttribute("currentAgent", agentId);
	        	 message = "have connected current agent: " + agentId;
	        }else{
	        	message = "login fail";
	        }
		
           return Response.ok(message).build();
	}

	@GET
	@Path("/logout")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + WsConstants.CHARSET })
	public Response logout(@QueryParam("companyId") String companyId,@QueryParam("agentId") String agentId) {
		
		if(StringUtils.isBlank(companyId) || StringUtils.isBlank(agentId)){
			String message = "companyId is blank or agentId is blank!";
			throw RsResponse.buildException(Status.NOT_FOUND, message);
		}
			
			
			Subject subject = SecurityUtils.getSubject();
	        Session session = subject.getSession();
	        String currentAgent=(String)session.getAttribute("currentAgent");
	        String message;
        	if(StringUtils.isNotBlank(currentAgent)){
        		session.stop();
        		 message = "have unconnected current agent: " + agentId;
        	}else{
        		
        		message = "unavailble agent: " + agentId;
        	}
	    
	     
	        return Response.ok(message).build();
	}
}
