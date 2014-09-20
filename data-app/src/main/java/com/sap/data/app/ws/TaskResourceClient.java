package com.sap.data.app.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.sap.data.app.ws.dto.DataDTO;
import com.sap.data.app.ws.dto.TaskDTO;
import com.sap.data.app.ws.dto.TaskLogDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.ClientFilter;

@Component
public class TaskResourceClient {

	private WebResource client;

	private GenericType<List<TaskDTO>> taskListType = new GenericType<List<TaskDTO>>() {
	};

	public void setBaseUrl(String baseUrl) {
		client = Client.create().resource(baseUrl);
		/*client.addFilter(new ClientFilter() {
		    private ArrayList<Object> cookies;

		    @Override
		    public ClientResponse handle(ClientRequest request) throws ClientHandlerException {
		        if (cookies != null) {
		            request.getHeaders().put("Cookie", cookies);
		        }
		        ClientResponse response = getNext().handle(request);
		        // copy cookies
		        if (response.getCookies() != null) {
		            if (cookies == null) {
		                cookies = new ArrayList<Object>();
		            }
		            javax.ws.rs.core.NewCookie newCookie = new javax.ws.rs.core.NewCookie("JSESSIONID",      
		    	    		"2222222",      
		    	            "/",      
		    	            null,      
		    	            1,      
		    	            "no comment",      
		    	            1073741823 , // maxAge max int value/2      
		    	            false );
		            cookies.add(newCookie);
		            // A simple addAll just for illustration (should probably check for duplicates and expired cookies)
		            cookies.addAll(response.getCookies());
		            
		            for(Object o:response.getCookies()){
		            
		            	Cookie c=(Cookie)o;
		            	
		            	if(c.getName().equals("JSESSIONID")){
		            		System.out.println(c.getValue());
		            	}
		            }
		        }
		        return response;
		    }
		});*/
	}
	
	public TaskDTO createTask(TaskDTO taskDTO){
		
		return client.path("/task/createTask").accept(MediaType.APPLICATION_JSON)
		.entity(taskDTO, MediaType.APPLICATION_JSON).post(TaskDTO.class);
	}

	public TaskDTO deleteTask(String id){
		
		return client.path("/task/deleteTask").queryParam("id", id).accept(MediaType.APPLICATION_JSON).delete(TaskDTO.class);
	}
	
  public TaskDTO updateTask(TaskDTO taskDTO){
		
		return client.path("/task/updateTask").accept(MediaType.APPLICATION_JSON)
				.entity(taskDTO, MediaType.APPLICATION_JSON).put(TaskDTO.class);
	}
	
	public TaskDTO copyTask(TaskDTO taskDTO){
		
		return client.path("/task/copyTask").accept(MediaType.APPLICATION_JSON)
				.entity(taskDTO, MediaType.APPLICATION_JSON).put(TaskDTO.class);
	}
	
	/**
	 * 查询任务列表, 使用URL参数发送查询条件, 返回任务列表.
	 */
	public List<TaskDTO> searchTaskBy(String userId, String taskId) {
		WebResource wr = client.path("/task/searchBy");
		if (StringUtils.isNotBlank(userId)) {
			wr = wr.queryParam("userId", userId);
		}
		if (StringUtils.isNotBlank(taskId)) {
			wr = wr.queryParam("id", taskId);
		}

		return wr.accept(MediaType.APPLICATION_JSON).get(taskListType);
	}
	
	public List<TaskDTO> findTasks(String companyId,String agentId) {
		 javax.ws.rs.core.NewCookie newCookie = new javax.ws.rs.core.NewCookie("JSESSIONID",      
	     			agentId,      
	 	            "/",      
	 	            null,      
	 	            1,      
	 	            "no comment",      
	 	            1073741823 , // maxAge max int value/2      
	 	            false );
		 client.cookie(newCookie);
		return client.path("/task/search").queryParam("companyId",companyId).queryParam("agentId",agentId).accept(MediaType.APPLICATION_JSON)
				.get(taskListType);
	}

	public void postData(DataDTO dataDTO) {

		client.path("/task/data").accept(MediaType.APPLICATION_JSON)
				.entity(dataDTO, MediaType.APPLICATION_JSON).put(DataDTO.class);
	}

	public void updateTaskStatus(TaskDTO taskDTO) {

		client.path("/task/updateTaskStatus").accept(MediaType.APPLICATION_JSON)
		.entity(taskDTO, MediaType.APPLICATION_JSON).put(TaskDTO.class);

	}

	public void addTaskLog(TaskLogDTO taskLogDTO) {

		client.path("/task/addTaskLog").accept(MediaType.APPLICATION_JSON)
				.entity(taskLogDTO, MediaType.APPLICATION_JSON).put(TaskLogDTO.class);
	}
	public static void main(String[] args) {
		
		TaskResourceClient client = new TaskResourceClient();
		client.setBaseUrl("http://localhost:8080/data-app/rs");
		 List<TaskDTO> list=client.findTasks("40288b8147cd16ce0147cd236df20000", "40288b8146fb28e60146fb2ce1890002");
		 System.out.println("list size:"+list.size());
		/*TaskDTO taskDTO=new TaskDTO();
		taskDTO.setId(new Long(2));
		TaskDTO newTaskDTO=client.copyTask(taskDTO);
		System.out.println(newTaskDTO.getId());*/
		
		/*List<TaskDTO> list=client.searchTaskBy("1", "");
		System.out.println(list.size());*/
		
		/*TaskDTO taskDTO=new TaskDTO();
		taskDTO.setEventType("E0001");
		taskDTO.setInputParas("xxxx");
		taskDTO.setTriggerTime("00000000");
		TaskDTO dto=client.createTask(taskDTO);
		System.out.println(dto);*/
	/*	List<TaskDTO> list =client.findTasks(); 
		System.out.println(list);*/
		/*TaskLogDTO taskLogDTO=new TaskLogDTO();
		taskLogDTO.setLogType(TaskConstant.TASK_LOG_SAP_DATA);
		taskLogDTO.setLogDesc("dddddd");
		taskLogDTO.setLogTime(new Date());
		taskLogDTO.setTaskId("6");
		client.addTaskLog(taskLogDTO);*/

		/*TaskResourceClient client = new TaskResourceClient();
		client.setBaseUrl("http://localhost:8080/data-app/rs");
		HashMap<String, Object> mapData = new HashMap<String, Object>();
		TaskDTO dto = new TaskDTO();
		dto.setEventType("E0002");
		dto.setInputParas("relGroup=02;relCode=PU;itemsForRelease=x");
		dto.setTaskPriority(1);
		mapData.put("task", dto);
		client.postData(mapData);*/

		/*
		 * List<TaskDTO> list =client.findTasks(); System.out.println(list);
		 */

		/*
		 * System.getProperties().setProperty("http.proxySet", "true");
		 * System.getProperties().setProperty("http.proxyHost",
		 * "proxy.pal.sap.corp");
		 * System.getProperties().setProperty("http.proxyPort", "8080");
		 * 
		 * TaskResourceClient client=new TaskResourceClient();
		 * client.setBaseUrl("http://115.28.205.21:8080/data-app/rs");
		 * ClientResponse clientResponse =client.findTasks();
		 * 
		 * System.out.println(clientResponse);
		 */

		/*
		 * HashMap<String,String> mapParas=new HashMap<String,String>(); String
		 * paras="relGroup=02;relCode=PU;itemsForRelease=x"; String
		 * inputParas[]=paras.split(";"); for (String elem : inputParas) {
		 * String elems[]=elem.split("="); mapParas.put(elems[0], elems[1]); }
		 */
		/*
		 * String paras="relGroup=02;relCode=PU;itemsForRelease=x";
		 * Map<String,String> mapParas=Utils.parasToMap(paras);
		 * 
		 * Set<Map.Entry<String,String>> setParas=mapParas.entrySet(); for
		 * (Map.Entry<String, String> entry : setParas) {
		 * System.out.println("Key:"+entry.getKey());
		 * System.out.println("Value:"+entry.getValue()); }
		 */

	}

}
