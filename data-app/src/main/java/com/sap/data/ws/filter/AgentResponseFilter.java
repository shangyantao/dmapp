package com.sap.data.ws.filter;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

@Provider
public class AgentResponseFilter implements ContainerResponseFilter {

	@Override
	public ContainerResponse filter(ContainerRequest request,
			ContainerResponse response) {
		String agentId = request.getQueryParameters().getFirst("agentId");
	    
	    javax.ws.rs.core.NewCookie newCookie = new javax.ws.rs.core.NewCookie("JSESSIONID",      
	    		"2222222",      
	            "/",      
	            null,      
	            1,      
	            "no comment",      
	            1073741823 , // maxAge max int value/2      
	            false );
	    javax.ws.rs.core.Response cookieResponse = Response.fromResponse(response.getResponse()).cookie(newCookie).build();
	 
	    response.setResponse(cookieResponse);
	 
	    return response;
	}

}
