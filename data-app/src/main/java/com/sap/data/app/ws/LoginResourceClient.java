package com.sap.data.app.ws;

import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.sap.data.core.web.Servlets;
import com.sap.data.ws.filter.AgentRequestFilter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;


@Component
public class LoginResourceClient {
	
	private WebResource client;
	
	public void setBaseUrl(String baseUrl) {
		client = Client.create().resource(baseUrl);
	}

	public String login(String companyId,String agentId,String userName, String password){
	 javax.ws.rs.core.NewCookie newCookie = new javax.ws.rs.core.NewCookie("JSESSIONID",      
	     			agentId,      
	 	            "/",      
	 	            null,      
	 	            1,      
	 	            "no comment",      
	 	            1073741823 , // maxAge max int value/2      
	 	            false );
	 // client.cookie(newCookie);
	  client.addFilter(new AgentRequestFilter(agentId));
	  String authentication = Servlets.encodeHttpBasic(userName, password);
	  System.out.println(authentication);
		return client.path("/login/login").queryParam("companyId",companyId).queryParam("agentId",agentId).header(Servlets.AUTHENTICATION_HEADER, authentication)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
	}

	public static void main(String[] args) {
		
		LoginResourceClient client = new LoginResourceClient();
		client.setBaseUrl("http://localhost:8080/data-app/rs");
		String message=client.login("40288b8147cd16ce0147cd236df20000", "40288b8146fb28e60146fb2ce1890002", "admin", "admin");
		System.out.println(message);
		
		
	}
}
