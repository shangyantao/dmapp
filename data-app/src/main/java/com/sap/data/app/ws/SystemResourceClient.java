package com.sap.data.app.ws;

import javax.servlet.http.Cookie;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.sap.data.app.ws.dto.AgentDTO;
import com.sap.data.core.web.Servlets;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Component
public class SystemResourceClient {

	private WebResource client;

	public void setBaseUrl(String baseUrl) {
		client = Client.create().resource(baseUrl);
	}

	public void connAgent(AgentDTO agentDTO) {
		String authentication = Servlets.encodeHttpBasic("platformAdmin", "admin");
		client.path("/system/agent").header(Servlets.AUTHENTICATION_HEADER, authentication).accept(MediaType.APPLICATION_JSON)
				.entity(agentDTO, MediaType.APPLICATION_JSON)
				.put(AgentDTO.class);
	}

	public static void main(String[] args) {

		SystemResourceClient client = new SystemResourceClient();
		client.setBaseUrl("http://localhost:8080/data-app/rs");
		AgentDTO agentDTO=new AgentDTO();
		agentDTO.setAgentGUID("40288b8147139de40147139ef4c50000");
		client.connAgent(agentDTO);
	}
}
