/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.data.db.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.sap.data.db.util.NotFoundException;
import com.sap.data.db.util.PropertyUtil;

/**
 *
 * @author C5178395
 */
public class HibernateUtil {
	
	private static final ThreadLocal<Configuration> threadLocal = new ThreadLocal<Configuration>();
    private static SessionFactory sessionFactory = null;
    private static Object lockUtil = new Object();
        
    private static Configuration configuration(String firstLoad) {
    	Configuration configuration = threadLocal.get();
    	if(null == configuration || "x".equalsIgnoreCase(firstLoad)) {
    		if("true".equals(PropertyUtil.getWebApplication())) {
    			WebApplicationContext webAppCtx = ContextLoader.getCurrentWebApplicationContext();	
    			LocalSessionFactoryBean sfb = (LocalSessionFactoryBean)webAppCtx.getBean("&sessionFactory",LocalSessionFactoryBean.class);
            	configuration = sfb.getConfiguration();
    		} else {
	    		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(PropertyUtil.getSpringAppContext());
	    		LocalSessionFactoryBean localSessionFactoryBean = context.getBean("&sessionFactory", LocalSessionFactoryBean.class);
	        	configuration = localSessionFactoryBean.getConfiguration();
    		}
		}
    	threadLocal.set(configuration);
    	return configuration;
    }
    
	public static SessionFactory getSessionFactory() {
        try {
            if(sessionFactory == null || sessionFactory.isClosed()) {
            	Configuration configuration = configuration("NO_FIRST_LOAD");
            	ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return sessionFactory;
    }
	
    public static void incarnate(String entity, String firstLoad) throws NotFoundException {
    	synchronized (lockUtil) {
    		try{
		    	Configuration configuration = configuration(firstLoad);
		    	if(null != entity && null != configuration) {
		    		if(null == configuration.getClassMapping(entity)) {
		    			if(StructureUtil.exist(entity)) {
		    				configuration.addResource(PropertyUtil.getHbPjResource() + entity + ".dto.xml");
		    				configuration.setProperty(Environment.FORMAT_SQL, "true");
		    				if("true".equals(PropertyUtil.getShowSql())) {
		    					configuration.setProperty(Environment.SHOW_SQL, "true");
		    				}
		    	            SchemaUpdate su = new SchemaUpdate(configuration);
		    	            su.execute(true, true);
		    	            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		    	    		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		    	        	threadLocal.set(configuration);
		    			} else {
		    				throw new NotFoundException(PropertyUtil.getHbPjResource() + entity + ".dto.xml not exist.");
		    			}
		    		}
		    	}
    		} catch (Exception ex) {
    			throw new NotFoundException(ex.getMessage());
    		}
		}
    }
    
    public static Session getSession(){
        return getSessionFactory().openSession();
    }
    
    public static void close(Session session) {
        if(session != null && session.isOpen()) {
            session.close();
        }
    }
    
}

