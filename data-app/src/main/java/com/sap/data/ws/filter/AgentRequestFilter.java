package com.sap.data.ws.filter;

import java.util.ArrayList;

import javax.ws.rs.core.NewCookie;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;

public class AgentRequestFilter extends ClientFilter {

	private String agentId;
	private ArrayList<Object> cookies;

	public AgentRequestFilter(String agentId) {
		this.agentId = agentId;
	}

	@Override
	public ClientResponse handle(ClientRequest request)
			throws ClientHandlerException {
		/*
		 * javax.ws.rs.core.NewCookie newCookie = new
		 * javax.ws.rs.core.NewCookie("JSESSIONID", agentId, "/", null, 1,
		 * "no comment", 1073741823 , // maxAge max int value/2 false );
		 * request.getHeaders().putSingle("newCookie", newCookie);
		 */
		if (cookies != null) {
			request.getHeaders().put("Cookie", cookies);
		}
		ClientResponse response = getNext().handle(request);
		// copy cookies
		if (response.getCookies() != null) {
			if (cookies == null) {
				cookies = new ArrayList<Object>();
			}
			
			/*
			 * javax.ws.rs.core.NewCookie newCookie = new
			 * javax.ws.rs.core.NewCookie("JSESSIONID", agentId, "/", null, 1,
			 * "no comment", 1073741823 , // maxAge max int value/2 false );
			 * cookies.add(newCookie);
			 */
			// A simple addAll just for illustration (should probably check for
			// duplicates and expired cookies)
			cookies.addAll(response.getCookies());
			for (NewCookie c : response.getCookies()) {
				if(c.getName().equals("JSESSIONID")){
					System.out.println(c.getValue());
				}
				
			}
		}

		return response;
	}

}
