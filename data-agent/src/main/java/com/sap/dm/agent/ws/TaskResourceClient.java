package com.sap.dm.agent.ws;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.sap.data.db.util.PropertyUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

import java.util.Date;

@Component
public class TaskResourceClient {
    
//    private final String baseUrl = "http://10.59.153.242:8080/data-app/rs"; //115.28.205.21 10.59.154.191 10.59.153.242
    
    private WebResource client;

    private GenericType<List<TaskDTO>> taskListType = new GenericType<List<TaskDTO>>() {
    };
    
    
    public TaskResourceClient(String baseUrl) {
    	
    	client = new Client(new URLConnectionClientHandler(
                new HttpURLConnectionFactory() {
            Proxy p = null;
			@Override
			public HttpURLConnection getHttpURLConnection(URL url)
					throws IOException {
				// TODO Auto-generated method stub
				if (p == null) {
					if("true".equals(PropertyUtil.getProperty("proxySet"))) {
						String proxyHost = PropertyUtil.getProperty("http.proxyHost");
				    	String proxyPort = PropertyUtil.getProperty("http.proxyPort");
                        p = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, Integer.valueOf(proxyPort)));
                    } else {
                        p = Proxy.NO_PROXY;
                    }
                }
                return (HttpURLConnection) url.openConnection(p);
			}
        })).resource(baseUrl);
    }
    
    
    public TaskDTO createTask(TaskDTO taskDTO) {

        return client.path("/task/createTask").accept(MediaType.APPLICATION_JSON)
                .entity(taskDTO, MediaType.APPLICATION_JSON).post(TaskDTO.class);
    }

    public TaskDTO deleteTask(String id) {

        return client.path("/task/deleteTask").queryParam("id", id).accept(MediaType.APPLICATION_JSON).delete(TaskDTO.class);
    }

    public TaskDTO updateTask(TaskDTO taskDTO) {

        return client.path("/task/updateTask").accept(MediaType.APPLICATION_JSON)
                .entity(taskDTO, MediaType.APPLICATION_JSON).put(TaskDTO.class);
    }

    public TaskDTO copyTask(TaskDTO taskDTO) {

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

    public List<TaskDTO> findTasks(String companyId, String agentId, String hostAddress, String hostName) {
        return client.path("/task/search").queryParam("companyId", companyId)
        		.queryParam("agentId", agentId)
        		.queryParam("agentHostAddress", hostAddress)
        		.queryParam("agentHostName", hostName)
        		.accept(MediaType.APPLICATION_JSON)
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
    
    public void sendTaskLog(String taskId, String logDesc) {
        this.addTaskLog(this.getTaskLogDTO(taskId, TaskConstant.TASK_LOG_SAP_DATA, logDesc));
    }
    public void sendTaskStartLog(String taskId, String logDesc) {
        this.addTaskLog(this.getTaskLogDTO(taskId, TaskConstant.TASK_ACTION_SCHEDULE_START, logDesc));
    }
    
    public void sendTaskEndLog(String taskId, String logDesc) {
        this.addTaskLog(this.getTaskLogDTO(taskId, TaskConstant.TASK_STATUS_COMPLETED, logDesc));
    }
    
    public void sendTaskErrorLog(String taskId, String logDesc) {
        this.addTaskLog(this.getTaskLogDTO(taskId, TaskConstant.TASK_STATUS_ERROR_COMPLETED, logDesc));
    }
    
    public TaskLogDTO getTaskLogDTO(String taskId, String logType, String logDesc) {
        TaskLogDTO taskLogDTO = new TaskLogDTO();
        taskLogDTO.setCompanyId(PropertyUtil.getCompanyId());
        taskLogDTO.setLogType(logType);
        taskLogDTO.setTaskId(taskId);
        taskLogDTO.setLogDesc(logDesc);
        taskLogDTO.setLogTime(new Date());
        return taskLogDTO;
    }
    
    public static void main(String[] args) {

//        TaskResourceClient client = new TaskResourceClient();
//        client.setBaseUrl("http://localhost:8080/data-app/rs");
//        List<TaskDTO> list = client.findTasks("40288b8147cd16ce0147cd236df20000", "40288b8146fb28e60146fb2ce1890002");
//        System.out.println("LIST SIZE:" + list.size());
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
