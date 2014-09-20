package com.sap.data.app.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sap.data.app.entity.system.Company;
import com.sap.data.app.service.account.AccountManager;
import com.sap.data.app.service.system.SystemManager;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，

 * 真正登录的POST请求由Filter完成,
 * 
 */
@Controller
public class LoginController {
	
	@Autowired
	private AccountManager accountManager;
	
	@Autowired
	private SystemManager systemManager;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest req, Model model) {
		
		
		//model.addAttribute("companyId", req.getParameter("companyId"));
		Session session=SecurityUtils.getSubject().getSession();
		String sesCompanyId=(String)session.getAttribute("companyId");
		if(sesCompanyId!=null){
			return "index";
		}else{
		String companyId=req.getParameter("companyId");
		//Company company=systemManager.getCompany(companyId);
		session.setAttribute("companyId",companyId);
			return "login";
		}
		/*if(company==null){
			model.addAttribute("message", "can't find this company,please re-writting correct company GUID");
		}else{
			session.setAttribute("companyId",companyId);
		}*/
		//CompanyContextHolder.setCompany(companyId);
	    /* ServletContext sc=req.getSession().getServletContext();
         String companyId=req.getParameter("companyId");
         if(StringUtils.isNotBlank(companyId)){
		    BeanFactory beanFactory=(BeanFactory)sc.getAttribute(companyId);
         }*/
		/*
		if(StringUtils.isNotBlank(req.getParameter("companyGUID"))){
			Company company=systemManager.getCompany(req.getParameter("companyGUID"));
			WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
			AppDataSource ds=(AppDataSource)ctx.getBean("dataSource");
			ds.setUrl("jdbc:mysql://"+company.getSchemaServer()+"/"+company.getSchemaName()+"?useUnicode=true&characterEncoding=utf-8");
			ds.setUsername(company.getSchemaUser());
			ds.setPassword(company.getSchemaPwd());
		}*/
			
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return "login";
	}

}
