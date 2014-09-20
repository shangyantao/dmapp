package com.sap.data.ws.filter;

import java.io.IOException;

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

import com.sap.data.app.datasource.CompanyContextHolder;
import com.sap.data.app.service.system.SystemManager;

public class DataSourceFilter implements Filter {
	
	@Autowired
	private SystemManager systemManager;
	private WebApplicationContext ctx ;
	private ServletContext sc;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		if(ctx==null){
			ctx= ContextLoader.getCurrentWebApplicationContext();	
			 sc=ctx.getServletContext();
		}
		systemManager=(SystemManager)ctx.getBean("systemManager");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		Session session=SecurityUtils.getSubject().getSession();
		if(session!=null){
			String companyId=(String)session.getAttribute("companyId");
			
			if(StringUtils.isNotBlank(companyId) ){
				CompanyContextHolder.setCompanyType(companyId);
			}
			
			//System.out.println(companyId+"------------------------");
		}
		//Company company=systemManager.getCompany(companyId);
		//CompanyContextHolder.setCompany(companyId);
		/*sc=req.getSession().getServletContext();
		
		if (!StringUtils.isEmpty(companyId)) {
			*/
			/*if(sc.getAttribute("beanMap")==null){*/
	/*			ClassPathXmlApplicationContext factory = new ClassPathXmlApplicationContext("applicationContext.xml");
				HashMap<String,BeanFactory> beanMap=new  HashMap<String,BeanFactory>();
				beanMap.put(companyId, factory);
				Properties props = new Properties();
				props.load(DataSourceFilter.class.getResourceAsStream("/application_"+companyId+".properties"));
				PropertyPlaceholderConfigurer configurer = new  PropertyPlaceholderConfigurer();  
				configurer.setProperties(props);  
				factory.addBeanFactoryPostProcessor(configurer);
				factory.refresh(); 
				sc.setAttribute("beanMap", beanMap);*/
			/*}else{
				HashMap<String,BeanFactory> beanMap=(HashMap<String,BeanFactory>)sc.getAttribute("beanMap");
				if(!beanMap.containsKey(companyId)){
					ClassPathXmlApplicationContext factory = new ClassPathXmlApplicationContext("applicationContext.xml");
					Properties props = new Properties();
					props.load(DataSourceFilter.class.getResourceAsStream("/application_"+companyId+".properties"));
					PropertyPlaceholderConfigurer configurer = new  PropertyPlaceholderConfigurer();  
					configurer.setProperties(props);  
					factory.addBeanFactoryPostProcessor(configurer);
					factory.refresh(); 
					beanMap.put(companyId, factory);
					sc.setAttribute("beanMap", beanMap);
				}
				
			}
		}*/
			
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}
