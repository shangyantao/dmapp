package com.sap.dm.agent;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sap.data.db.util.PropertyUtil;
import com.sap.dm.agent.ws.TimerExecutorJob;

@Controller
@RequestMapping(value = "/agent")
public class AgentController {
 
	@RequestMapping(value = { "manage"})
	public String manage(HttpServletRequest req, Model model, RedirectAttributes redirectAttributes) {
		System.out.println("Agent Manage");
		ServletContext servletContext = req.getSession().getServletContext();
		Object obj = servletContext.getAttribute(PropertyUtil.getAgentId());
		if(obj != null) {
			TimerExecutorJob job = (TimerExecutorJob)obj;
			if("connecting".equals(job.getConnStatus())) {
				model.addAttribute("agentStartStatus", "connecting");
			} else if("connected".equals(job.getConnStatus())) {
				model.addAttribute("agentStartStatus", "started");
			} else if("ununited".equals(job.getConnStatus())) {
				model.addAttribute("agentStartStatus", "stopped");
			}
		} else {
			model.addAttribute("agentStartStatus", "stopped");
		}
		this.setModel(model);
		return "agent/manage";
	}
	
	@RequestMapping(value = "start")
	public String start(HttpServletRequest req, Model model, RedirectAttributes redirectAttributes) {
		System.out.println("Start Agent");
		TimerExecutorJob job = null;
		ServletContext servletContext = req.getSession().getServletContext();
		Object obj = servletContext.getAttribute(PropertyUtil.getAgentId());
		if(obj != null) {
			job = (TimerExecutorJob)obj;
		} else {
			job = new TimerExecutorJob();
		}
		if("ununited".equals(job.getConnStatus())) {
			job.startTimer();
		}
		servletContext.setAttribute(PropertyUtil.getAgentId(), job);
		this.setModel(model);
		redirectAttributes.addFlashAttribute("message", "Agent has been started." );
		return "redirect:/agent/manage";
	}
	
	@RequestMapping(value = "stop")
	public String stop(HttpServletRequest req, Model model, RedirectAttributes redirectAttributes) {
		System.out.println("Stop Agent");
		ServletContext servletContext = req.getSession().getServletContext();
		Object obj = servletContext.getAttribute(PropertyUtil.getAgentId());
		if(obj != null) {
			TimerExecutorJob job = (TimerExecutorJob)obj;
			job.stopTimer();
			servletContext.removeAttribute(PropertyUtil.getAgentId());
		}
		this.setModel(model);
		redirectAttributes.addFlashAttribute("message", "Agent has been stoped." );
		return "redirect:/agent/manage";
	}
	
	private void setModel(Model model) {
		model.addAttribute("companyId", PropertyUtil.getCompanyId());
		model.addAttribute("agentId", PropertyUtil.getAgentId());
		model.addAttribute("serverUrl", PropertyUtil.getServerUrl());
	}
	
}
