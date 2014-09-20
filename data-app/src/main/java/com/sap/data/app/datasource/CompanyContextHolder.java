package com.sap.data.app.datasource;

import org.springframework.util.Assert;

public class CompanyContextHolder {

	private static final ThreadLocal<String> contextHolder = 
            new ThreadLocal<String>();
	
	   public static void setCompanyType(String companyType) {
		      Assert.notNull(companyType, "companyType cannot be null");
		    	  contextHolder.set(companyType);  
		   }

		   public static String getCompanyType() {
		      return (String) contextHolder.get();
		   }

		   public static void clearCompanyType() {
		      contextHolder.remove();
		   }
}
