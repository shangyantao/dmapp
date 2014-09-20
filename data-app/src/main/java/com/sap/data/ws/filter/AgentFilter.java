package com.sap.data.ws.filter;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.sap.data.app.entity.system.Agent;
import com.sap.data.app.service.system.SystemManager;
import com.sap.data.app.web.system.AgentContants;

public class AgentFilter implements Filter {


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String agentId = req.getParameter("agentId");
		if (!StringUtils.isEmpty(agentId)) {
			Session session=SecurityUtils.getSubject().getSession();
			String currentAgentId=(String)session.getAttribute("currentAgent");
			if(!currentAgentId.equals(agentId)){
				session.setAttribute("currentAgent", agentId);
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
