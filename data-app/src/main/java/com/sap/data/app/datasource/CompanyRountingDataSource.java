package com.sap.data.app.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class CompanyRountingDataSource extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		
		return CompanyContextHolder.getCompanyType();
	}

}
