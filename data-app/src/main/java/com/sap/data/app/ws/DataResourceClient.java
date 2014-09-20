package com.sap.data.app.ws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.sap.data.app.ws.dto.DataDTO;
import com.sap.data.app.ws.dto.TaskDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

@Component
public class DataResourceClient {

	private WebResource client;
	private GenericType<HashMap<String,Object>> dataMapType = new GenericType<HashMap<String,Object>>() {
	};
	public void setBaseUrl(String baseUrl) {
		client = Client.create().resource(baseUrl);
	}
	
	public void postData(Map<String,Object> mapData){
		
		DataDTO dataDTO=new DataDTO();
		dataDTO.setData(mapData);
		client.path("data/postData").type(MediaType.APPLICATION_JSON).post(DataDTO.class, dataDTO);
	}
	
	public String searchData(String userId,String eventType,String tableName){
		System.out.println("userId"+userId+" eventType "+eventType);
		
		return client.path("data/search").queryParam("userId", userId).queryParam("eventType", eventType).queryParam("tableName", tableName).type(MediaType.APPLICATION_JSON).get(String.class);
	}

	public static void main(String[] args) {
		
		DataResourceClient client=new DataResourceClient();
		client.setBaseUrl("http://localhost:8080/data-app/rs");
		String dataString=client.searchData("1001","E0001","");
		System.out.println(dataString);
		/*Map<String,Object> mapData=new HashMap<String, Object>();
		mapData.put("ssss", new String("xx"));
		client.postData(mapData);*/
	}
}
