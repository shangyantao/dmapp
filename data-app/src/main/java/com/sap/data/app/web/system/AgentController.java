package com.sap.data.app.web.system;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sap.data.app.entity.account.User;
import com.sap.data.app.entity.system.Agent;
import com.sap.data.app.entity.system.Company;
import com.sap.data.app.service.system.SystemManager;
import com.sap.data.core.mapper.JaxbMapper;

@Controller
@RequestMapping(value = "/system/agent")
public class AgentController {
	
	@Autowired
	private SystemManager systemManager;
	
	private static JaxbMapper binder;
	
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		long millis=1407914660127l-1407913460000l;
		
		System.out.println("millis:"+millis/1000);
		Date date=new Date();
		date.setTime(millis);
		c.setTime(date);
		int seconds=c.get(Calendar.SECOND);
		System.out.println(seconds);
		
	}
	
	@RequestMapping(value = { "list", "" })
	public String list(Model model,HttpServletRequest req) {
		List<Agent> agents = systemManager.getAllAgent();
		for (Agent agent : agents) {
			long seconds=(new Date().getTime()-agent.getLastConnDate().getTime())/1000;
			if (seconds> 60*AgentContants.AGENT_INTERVAL_TIME) {
				agent.setAgentStatus(AgentContants.AGENT_UNCONNECTION_STATUS);
				agent.setUnConnTime(seconds);
				systemManager.saveAgent(agent);
			}
		}
		model.addAttribute("agents", agents);
		return "system/agentList";
	}
	
	@RequestMapping(value = "create")
	public String createForm(Model model) {
		model.addAttribute("agent", new Agent());
		model.addAttribute("allCompanys", systemManager.getAllCompany());
		return "system/agentForm";
	}
	
	@RequestMapping(value = "save")
	public String save(Agent agent,HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {
		Company company = systemManager.getCompany(agent.getCompany().getCompanyGUID());
		agent.setCompany(company);
		agent.setAgentStatus(AgentContants.AGENT_UNCONNECTION_STATUS);
		Agent agt=systemManager.saveAgent(agent);
		
		Document document = DocumentHelper.createDocument();
        Element root = document.addElement("agent").addAttribute("id", agt.getAgentGUID());
        root.addElement("cmyName").setText(agt.getCmyName());
        root.addElement("serverUrl").setText(agt.getServerUrl());
        root.addElement("agentDes").setText(agt.getAgentDes());
        root.addElement("comments").setText(agt.getComments());
        Session ses=SecurityUtils.getSubject().getSession();
		User user=(User)ses.getAttribute("user");
		Element userNode=root.addElement("user").addAttribute("id", String.valueOf(user.getId()));
		userNode.addElement("loginName").setText(user.getLoginName());
		userNode.addElement("password").setText(user.getPassword());
        OutputFormat format = OutputFormat.createPrettyPrint();//缩减型格式
        format.setEncoding("UTF-8");//设置文件内部文字的编码
        format.setExpandEmptyElements(true);
               format.setTrimText(false);
               format.setIndent(true);      // 设置是否缩进
               format.setIndent("   ");     // 以空格方式实现缩进
//                format.setNewlines(true);    // 设置是否换行
        String xmlPath=request.getRealPath("/");
		String filename=xmlPath+"/static/xmlfiles/"+agent.getAgentGUID()+".xml";
		String encoding = "UTF-8";//设置文件的编码！！和format不是一回事
		   OutputStreamWriter outstream = new OutputStreamWriter(new FileOutputStream(filename), encoding);
        XMLWriter writer=new XMLWriter(outstream,format);
        writer.write(document);
        writer.close();
		redirectAttributes.addFlashAttribute("message", "创建Agent" + agent.getAgentDes() + "成功");
		return "redirect:/system/agent/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		systemManager.deleteAgent(id);
		redirectAttributes.addFlashAttribute("message", "删除Agent成功");
		return "redirect:/system/agent/";
	}
	
}
