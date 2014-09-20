package com.sap.data.db.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertyUtil {
	
	private static Properties properties = null;
	private static boolean reload = false;
	private static final String rescouceUrl = File.separator + "application.properties";
	
	private static Resource resource() {
		return new ClassPathResource(rescouceUrl);
	}
	
	private static Properties load() {
		try {
			if(properties == null || reload) {
				properties = PropertiesLoaderUtils.loadProperties(resource());
				reload = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return properties;
    }
	
	private static void store(Properties property) {
		try {
			FileOutputStream fos = new FileOutputStream(resource().getFile());
			property.store(fos, "");
	        fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void add(String key, String value) {
		Properties property = new Properties();
		for(Object keySet : load().keySet()) {
			property.put(keySet, load().getProperty((String)keySet));
		}
		property.put(key, value);
		store(property);
        reload = true;
	}
	
	public static String getProperty(String name) {
		return load().getProperty(name);
	}
	
	public static String getCompanyId() {
		return load().getProperty("data.companyId");
	}
	
	public static void setCompanyId(String companyId) {
		add("data.companyId", companyId);
	}
	
	public static String getAgentId() {
		return load().getProperty("data.agentId");
	}
	
	public static void setAgentId(String agentId) {
		add("data.agentId", agentId);
	}
	
	public static String getServerUrl() {
		return load().getProperty("data.serverUrl");
	}
	
	public static void setServerUrl(String serverUrl) {
		add("data.serverUrl", serverUrl);
	}
	
	public static String getWebApplication() {
		return load().getProperty("spring.web.application");
	}
	
	public static String getSpringAppContext() {
		return load().getProperty("spring.application.context");
	}
	
	public static String getShowSql() {
		return load().getProperty("Hibernate.show_sql");
	}

    public static String getHbDtoXml() {
    	return load().getProperty("hibernate.dto.xml");
    }
    
    public static String getHbPjPkgn() {
    	return load().getProperty("hibernate.pojo.pkgn");
    }
    
    public static String getHbPjResource() {
    	return load().getProperty("hibernate.pojo.resource");
    }
    
}
