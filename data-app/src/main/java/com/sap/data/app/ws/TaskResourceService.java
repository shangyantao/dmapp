package com.sap.data.app.ws;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.data.app.datasource.CompanyContextHolder;
import com.sap.data.app.entity.system.Agent;
import com.sap.data.app.entity.task.Task;
import com.sap.data.app.entity.task.TaskLog;
import com.sap.data.app.service.firebase.FbConstants;
import com.sap.data.app.service.firebase.FirebaseService;
import com.sap.data.app.service.system.SystemContants;
import com.sap.data.app.service.system.SystemManager;
import com.sap.data.app.service.task.TaskManager;
import com.sap.data.app.web.system.AgentContants;
import com.sap.data.app.ws.dto.DataDTO;
import com.sap.data.app.ws.dto.TaskDTO;
import com.sap.data.app.ws.dto.TaskLogDTO;
import com.sap.data.core.mapper.BeanMapper;
import com.sap.data.core.mapper.JsonMapper;
import com.sap.data.core.rest.RsResponse;
import com.sap.data.db.dao.BapiDD03LDao;
import com.sap.data.db.dao.BapiDD04TDao;
import com.sap.data.db.dao.BapiFUPARAREFDao;
import com.sap.data.db.dao.EventDao;
import com.sap.data.db.dao.StructureDao;
import com.sap.data.db.pojo.BapiDD03LPojo;
import com.sap.data.db.pojo.BapiDD04TPojo;
import com.sap.data.db.pojo.BapiFUPARAREFPojo;
import com.sap.data.db.pojo.EventPojo;
import com.sap.data.db.util.ClassUtil;
import com.sap.data.db.util.ThreadLocalUtil;
import com.sap.data.ws.WsConstants;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

/**
 * 
 * @author C5208985
 * 
 *         implement a task Service
 */
@Component
@Path("/task")
public class TaskResourceService {

	private TaskManager taskManager;
	private SystemManager systemManager;
	private FirebaseService firebaseService;

	
	@Autowired
	public void setFirebaseService(FirebaseService firebaseService) {
		this.firebaseService = firebaseService;
	}

	@Autowired
	public void setSystemManager(SystemManager systemManager) {
		this.systemManager = systemManager;
	}

	// private GenericType<List<IDto>> taskListType = new
	// GenericType<List<IDto>>() {
	// };

	/**
	 * this restful service implements how to create a task.
	 * @param taskDTO
	 * @return
	 */
	@SuppressWarnings("static-access")
	@POST
	@Path("/createTask")
	@Produces({ MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML + WsConstants.CHARSET })
	public TaskDTO createTask(TaskDTO taskDTO) {
		/** change dataSource begin **/
		String companyId = taskDTO.getCompanyId();
		if (StringUtils.isNotBlank(companyId)) {
			CompanyContextHolder.setCompanyType(companyId);

		} else {
			String message = "company id isn't null";
			throw RsResponse.buildException(Status.BAD_REQUEST, message);
		}
		/** change dataSource end **/
		try {
			Task task = new Task();
			BeanMapper beanMapper = new BeanMapper();
			beanMapper.copy(taskDTO, task);
			taskManager.saveTask(task);
			TaskDTO tempTaskDTO = new TaskDTO();
			beanMapper.copy(task, tempTaskDTO);
			return tempTaskDTO;
		} catch (RuntimeException e) {
			throw RsResponse.buildDefaultException(e);
		}
	}

	/**
	 * this restful service implements how to update a Task 
	 * 
	 * @param taskDTO
	 * @return
	 */
	@PUT
	@Path("/updateTask")
	@Produces({ MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML + WsConstants.CHARSET })
	public TaskDTO updateTask(TaskDTO taskDTO) {

		/** change dataSource begin **/
		String companyId = taskDTO.getCompanyId();
		if (StringUtils.isNotBlank(companyId)) {
			CompanyContextHolder.setCompanyType(companyId);
		} else {
			String message = "company id isn't null";
			throw RsResponse.buildException(Status.BAD_REQUEST, message);
		}
		/** change dataSource end **/
		try {
			Task task = taskManager.getTaskById(taskDTO.getId());
			task.setTaskStatus(taskDTO.getTaskStatus());
			task.setEventType(taskDTO.getEventType());
			task.setTriggerTime(taskDTO.getTriggerTime());
			task.setInputParas(taskDTO.getInputParas());
			task.setTaskPriority(taskDTO.getTaskPriority());
			taskManager.saveTask(task);
			return taskDTO;
		} catch (RuntimeException e) {
			throw RsResponse.buildDefaultException(e);
		}

	}

	/**
	 * this restful service implements how to copy a task
	 * @param taskDTO
	 * @return
	 */
	@SuppressWarnings("static-access")
	@PUT
	@Path("/copyTask")
	@Produces({ MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML + WsConstants.CHARSET })
	public TaskDTO copyTask(TaskDTO taskDTO) {
		if (taskDTO == null) {
			String message = "TaskDTO can't be null";
			throw RsResponse.buildException(Status.BAD_REQUEST, message);
		}

		/** change dataSource begin **/
		String companyId = taskDTO.getCompanyId();
		if (StringUtils.isNotBlank(companyId)) {
			CompanyContextHolder.setCompanyType(companyId);
		} else {
			String message = "company id can't be null";
			throw RsResponse.buildException(Status.BAD_REQUEST, message);
		}
		BeanMapper beanMapper = new BeanMapper();
		Task task = taskManager.getTaskById(taskDTO.getId());

		if (task == null) {
			String message = "task  can't be null";
			throw RsResponse.buildException(Status.BAD_REQUEST, message);
		}

		try {
			Task newTask = new Task();
			newTask.setEventType(task.getEventType());
			newTask.setInputParas(task.getInputParas());
			newTask.setTaskPriority(task.getTaskPriority());
			newTask.setTaskStatus(TaskConstant.TASK_STATUS_NOT_STARTED);
			newTask.setTriggerTime(task.getTriggerTime());
			newTask.setUserId(task.getUserId());
			taskManager.saveTask(newTask);
			TaskDTO copyTaskDTO = new TaskDTO();
			beanMapper.copy(newTask, copyTaskDTO);
			return copyTaskDTO;
		} catch (RuntimeException e) {
			throw RsResponse.buildDefaultException(e);
		}

	}

	/**
	 *  this restful service implements how to delete a task.
	 * @param companyId
	 * @param id
	 * @return
	 */
	@SuppressWarnings("static-access")
	@DELETE
	@Path("/deleteTask")
	@Produces({ MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML + WsConstants.CHARSET })
	public TaskDTO deleteTask(@QueryParam("companyId") String companyId,
			@QueryParam("id") String id) {

		/** change dataSource begin **/
		if (StringUtils.isNotBlank(companyId)) {
			CompanyContextHolder.setCompanyType(companyId);
		} else {
			String message = "company id  can't be null";
			throw RsResponse.buildException(Status.BAD_REQUEST, message);
		}
		Task task = taskManager.getTaskById(Long.valueOf(id));
		TaskDTO taskDTO = new TaskDTO();
		BeanMapper beanMapper = new BeanMapper();
		beanMapper.copy(task, taskDTO);
		if (StringUtils.isNotBlank(id)) {
			taskManager.deleteTask(Long.valueOf(id));
			return taskDTO;
		} else {
			String message = "Task id  can't be null";
			throw RsResponse.buildException(Status.BAD_REQUEST, message);
		}

	}

	/**
	 * this restful service implements how to search task by all kinds of params 
	 * @param companyId
	 * @param userId
	 * @param taskId
	 * @param runId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@GET
	@Path("searchBy")
	@Produces({ MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML + WsConstants.CHARSET })
	public List<TaskDTO> searchTaskBy(
			@QueryParam("companyId") String companyId,
			@QueryParam("userId") String userId,
			@QueryParam("taskId") String taskId,
			@QueryParam("runId") String runId,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime) {
		try {

			/** change dataSource begin **/
			if (StringUtils.isNotBlank(companyId)) {
				CompanyContextHolder.setCompanyType(companyId);
			} else {
				String message = "company id  can't be null";
				throw RsResponse.buildException(Status.BAD_REQUEST, message);
			}
			/** change dataSource end **/

			List<Task> entityList = taskManager.searchTask(userId, taskId,
					runId, startTime, endTime);
			return BeanMapper.mapList(entityList, TaskDTO.class);
		} catch (RuntimeException e) {
			throw RsResponse.buildDefaultException(e);
		}
	}

	/**
	 * this restful service implements how to search all tasks that task status is 0
	 * this will provide service for agent 
	 * @param companyId
	 * @param agentId
	 * @return
	 */
	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML + WsConstants.CHARSET })
	public synchronized List<TaskDTO> searchTasks(
			@QueryParam("companyId") String companyId,
			@QueryParam("agentId") String agentId,@QueryParam("agentHostAddress") String agentHostAddress,@QueryParam("agentHostName") String agentHostName,@Context HttpServletRequest req) {

		if (StringUtils.isNotBlank(companyId)
				&& StringUtils.isNotBlank(agentId)) {
			
			/*
			 * Subject subject = SecurityUtils.getSubject(); Session session =
			 * subject.getSession();
			 * 
			 * String currentAgent=(String)session.getAttribute("currentAgent");
			 * System.out.println(" session.getId():"+ session.getId());
			 * System.out.println("task session:"+session.getHost());
			 * System.out.println("task currentAgent"+currentAgent);
			 * if(!agentId.equals(currentAgent)){ String message = agentId+
			 * "don't access this method,please firstly login restful web servies by invoking /rs/login"
			 * ; throw RsResponse.buildException(Status.FORBIDDEN, message); }
			 */
			//switch datasource
			CompanyContextHolder.setCompanyType(companyId);
			// modify to connected status 
			Agent agent = systemManager.getAgent(agentId);
			long seconds=(new Date().getTime()-agent.getLastConnDate().getTime())/1000;
			System.out.println(agentHostAddress+":"+agent.getAgentIP());
			System.out.println("seconds:"+seconds);
			if (!agentHostAddress.equalsIgnoreCase(agent.getAgentIP()) && seconds< 60*AgentContants.AGENT_INTERVAL_TIME) {
				String message = "server have connected by another agent IP:"+agent.getAgentIP();
				//throw RsResponse.buildException(WsConstants.STATUS_NOT_ACCEPTABLE, message);
				ResponseBuilderImpl builder = new ResponseBuilderImpl();
			    builder.status(Response.Status.CONFLICT);
			    builder.entity(message);
			    throw new WebApplicationException(builder.build());
			}else{
				agent.setAgentStatus(SystemContants.AGENT_CONNECTED);
				agent.setAgentHost(agentHostName);
				agent.setAgentIP(agentHostAddress);
				agent.setLastConnDate(new Date());
				systemManager.saveAgent(agent);
			}		
			
		} else {
			String message = "company id or agent Id  can't be null";
			throw RsResponse.buildException(Status.BAD_REQUEST, message);
		}
		List<Task> entityList = taskManager.findTasksforRS();
		// System.out.println("findTasksforRs......................"+entityList.size());
		// List<TaskDTO> taskDTOList = BeanMapper.mapList(entityList,
		// TaskDTO.class);
		List<TaskDTO> taskDTOList = new ArrayList<TaskDTO>();
		try {
			for (Task task : entityList) {
				task.setRunId(task.getRunId() + 1);
				taskDTOList.add(BeanMapper.map(task, TaskDTO.class));

				task.setTaskStatus(TaskConstant.TASK_STATUS_HAVE_STARTED);
				TaskLog taskLog = new TaskLog();
				taskLog.setTask(task);
				taskLog.setLogType(TaskConstant.TASK_LOG_TYPE_STATUS);
				taskLog.setLogTime(new Date());
				taskLog.setRunId(task.getRunId());
				if (true == StringUtils.equalsIgnoreCase(task.getTriggerTime(),
						"immediate")) {
					taskLog.setLogDesc("task of ID:" + task.getId() + " have executed immediately");
				} else {
					taskLog.setLogDesc("task of ID:" + task.getId() + "have started");
				}
				List<TaskLog> tasklogs = new ArrayList<TaskLog>();
				tasklogs.add(taskLog);
				task.setListTaskLogs(tasklogs);
				task.setStartTime(new Date());
				task.setEndTime(null);
				task.setExecutionTime(0l);
				taskManager.saveTask(task);
			}

			return taskDTOList;
		} catch (RuntimeException e) {
			throw RsResponse.buildDefaultException(e);
		}
	}

	/**
	 * the restful service implements how to update task status
	 * @param taskDTO
	 * @return
	 */
	@PUT
	@Path("/updateTaskStatus")
	@Produces({ MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML + WsConstants.CHARSET })
	public TaskDTO updateTaskStatus(TaskDTO taskDTO) {

		Task task = taskManager.getTaskById(taskDTO.getId());
		task.setTaskStatus(taskDTO.getTaskStatus());
		taskManager.saveTask(task);
		return taskDTO;

	}

	/**
	 * the restful service implements how to add task log
	 * @param taskLogDTO
	 * @return
	 */
	@PUT
	@Path("/addTaskLog")
	@Produces({ MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML + WsConstants.CHARSET })
	public  TaskLogDTO addTaskLog(TaskLogDTO taskLogDTO) {
		/** change dataSource begin **/
		String companyId = taskLogDTO.getCompanyId();
		if (StringUtils.isNotBlank(companyId)) {
			CompanyContextHolder.setCompanyType(companyId);
		} else {
			String message = "company id   can't be null";
			throw RsResponse.buildException(Status.BAD_REQUEST, message);
		}
		/** change dataSource end **/
		try {
			if (taskLogDTO.getLogType().equals(
					TaskConstant.TASK_STATUS_COMPLETED)
					|| taskLogDTO.getLogType().equals(
							TaskConstant.TASK_STATUS_ERROR_COMPLETED)) {
				List<TaskLog> tasklogs = new ArrayList<TaskLog>();
				Task task = taskManager.getTaskById(Long.parseLong(taskLogDTO
						.getTaskId()));
				if (!TaskConstant.TASK_STATUS_ERROR_COMPLETED.equals(task
						.getTaskStatus())) {
					task.setTaskStatus(taskLogDTO.getLogType());
				}
				TaskLog taskLog = new TaskLog();
				taskLog.setTask(task);
				taskLog.setLogType(TaskConstant.TASK_LOG_TYPE_STATUS);
				taskLog.setLogTime(new Date());
				taskLog.setLogDesc(taskLogDTO.getLogDesc());
				taskLog.setRunId(task.getRunId());
				tasklogs.add(taskLog);
				task.setListTaskLogs(tasklogs);
				Date endTime = new Date();
				if (task.getStartTime() != null) {
					task.setExecutionTime((endTime.getTime() - task
							.getStartTime().getTime()) / 1000);
				}
				task.setEndTime(endTime);
				taskManager.saveTask(task);
			} else {

				taskLogDTO.setLogTime(new Date());
				TaskLog taskLog = new TaskLog();
				taskLog.setLogDesc(taskLogDTO.getLogDesc());
				taskLog.setLogTime(taskLogDTO.getLogTime());
				Task task = taskManager.getTaskById(Long.parseLong(taskLogDTO
						.getTaskId()));
				if (taskLogDTO.getLogType().equals(
						TaskConstant.TASK_ACTION_SCHEDULE_START)) {
					task.setStartTime(new Date());
				}
				taskLog.setLogType(taskLogDTO.getLogType());
				taskLog.setRunId(task.getRunId());
				taskLog.setTask(task);
				taskManager.saveTaskLog(taskLog);
			}
			return taskLogDTO;
		} catch (RuntimeException e) {
			throw RsResponse.buildDefaultException(e);
		}
	}

	/**
	 * push data from agent
	 * @param dataDTO
	 * @return
	 */
	@PUT
	@Path("/data")
	@Produces({ MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML + WsConstants.CHARSET })
	@Consumes({ MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML + WsConstants.CHARSET })
	public synchronized DataDTO pushData(DataDTO dataDTO) {

		TaskDTO taskDTO = dataDTO.getTaskDTO();
		/** change dataSource begin **/
		String companyId = taskDTO.getCompanyId();
		if (StringUtils.isNotBlank(companyId)) {
			CompanyContextHolder.setCompanyType(companyId);
		} else {
			String message = "company id   can't be null";
			throw RsResponse.buildException(Status.BAD_REQUEST, message);
		}
		/** change dataSource end **/
		Task task = taskManager.getTaskById(taskDTO.getId());

		Map<String, Object> mapData = dataDTO.getData();
		String funcName = (String) mapData.get("FUNC_NAME");
		String eventType = (String) mapData.get("EVENT_NUMBER");
		String firstLoad = (String) mapData.get("FIRST_LOAD");
		/**
		 * getting Firebase Parameters
		 */
		
		String fbPush=(String)mapData.get(FbConstants.FB_PUSH_KEY);
		String fbLimit=(String)mapData.get(FbConstants.FB_LIMIT);
		
		String fbPath=(String)mapData.get(FbConstants.FB_PATH);
		if(StringUtils.isBlank(fbPath)){
			fbPath=FbConstants.FB_PATH_DEFAULT_VALUE;
		}
		System.out.println("map size:" + mapData.size());
		ThreadLocalUtil.setTaskDTO(eventType,
				String.valueOf(taskDTO.getUserId()), taskDTO.getInputParas(),
				String.valueOf(taskDTO.getId()),
				String.valueOf(taskDTO.getRunId()));
		if (mapData.size() == 0) {
			List<TaskLog> tasklogs = new ArrayList<TaskLog>();
			task.setTaskStatus(TaskConstant.TASK_STATUS_COMPLETED);
			TaskLog taskLog = new TaskLog();
			taskLog.setTask(task);
			taskLog.setLogType(TaskConstant.TASK_LOG_TYPE_STATUS);
			taskLog.setLogTime(new Date());
			taskLog.setLogDesc(" task of ID:" + task.getId() + "have finished, no any updated data");
			taskLog.setRunId(task.getRunId());
			tasklogs.add(taskLog);
			task.setListTaskLogs(tasklogs);
			taskManager.saveTask(task);
			return dataDTO;
		}

		if (mapData.containsKey("errorMessage")) {
			List<TaskLog> tasklogs = new ArrayList<TaskLog>();
			System.out.println(String.valueOf(mapData.get("errorMessage")));
			task.setTaskStatus(TaskConstant.TASK_STATUS_UNFINISHED);
			TaskLog taskLog = new TaskLog();
			taskLog.setTask(task);
			taskLog.setLogType(TaskConstant.TASK_LOG_SAP_DATA);
			taskLog.setLogTime(new Date());
			taskLog.setLogDesc(String.valueOf(mapData.get("errorMessage")));
			taskLog.setRunId(task.getRunId());
			tasklogs.add(taskLog);
			task.setListTaskLogs(tasklogs);
			taskManager.saveTask(task);
			return dataDTO;
		}
		try {

			JsonMapper jsonMapper = new JsonMapper();
			// dao.buildTable();
			Set<Map.Entry<String, Object>> setDatas = mapData.entrySet();
			for (Map.Entry<String, Object> entry : setDatas) {

				if (entry.getValue() instanceof List) {
					// 1.dynamic create and compile class  2.create configuration files, 3.create table
					System.out.println("entry.getKey:" + entry.getKey());
					ObjectMapper mapper = new ObjectMapper();
					List list = (List) entry.getValue();
					EventDao eventDao = new EventDao();
					if ("A0001".equals(eventType)) {
						for(Object obj : list) {
							if(obj instanceof EventPojo) {
								eventDao.save((EventPojo)obj);
							}
						}
					} else if ("E0000".equals(eventType)) {
						String jsonString = jsonMapper.toJson(list);
						//writing data to log from agent
						List<TaskLog> tasklogs1 = new ArrayList<TaskLog>();
						TaskLog taskLog = new TaskLog();
						taskLog.setTask(task);
						taskLog.setLogType(TaskConstant.TASK_LOG_TYPE_STATUS);
						taskLog.setLogTime(new Date());
						taskLog.setLogDesc("ID:" + task.getId()
								+ "got ：[" + entry.getKey() + "]"
								+ list.size() + " records from agent");
						taskLog.setRunId(task.getRunId());
						tasklogs1.add(taskLog);
						//syn basic table
						if ("FUPARAREF".equals(entry.getKey())) {
							JavaType type = mapper.getTypeFactory()
									.constructCollectionType(List.class,
											BapiFUPARAREFPojo.class);
							List<BapiFUPARAREFPojo> rayDTOs = (List<BapiFUPARAREFPojo>) jsonMapper
									.fromJson(jsonString, type);
							new BapiFUPARAREFDao().save(rayDTOs);

						} else if ("DD03L".equals(entry.getKey())) {
							JavaType type = mapper.getTypeFactory()
									.constructCollectionType(List.class,
											BapiDD03LPojo.class);
							List<BapiDD03LPojo> rayDTOs = (List<BapiDD03LPojo>) jsonMapper
									.fromJson(jsonString, type);
							new BapiDD03LDao().save(rayDTOs);
						} else if ("DD04T".equals(entry.getKey())) {
							JavaType type = mapper.getTypeFactory()
									.constructCollectionType(List.class,
											BapiDD04TPojo.class);
							List<BapiDD04TPojo> rayDTOs = (List<BapiDD04TPojo>) jsonMapper
									.fromJson(jsonString, type);
							new BapiDD04TDao().save(rayDTOs);
						}
						//记录来自Agent保存数据日志
						TaskLog taskLog2 = new TaskLog();
						taskLog2.setTask(task);
						taskLog2.setLogType(TaskConstant.TASK_LOG_TYPE_STATUS);
						taskLog2.setLogTime(new Date());
						taskLog2.setLogDesc("ID:" + task.getId()
								+ "save [" + entry.getKey() + "]"
								+ list.size() + " data from agent");
						taskLog2.setRunId(task.getRunId());
						tasklogs1.add(taskLog2);
						task.setListTaskLogs(tasklogs1);
						//save all record to logs
						taskManager.saveTask(task);
						
					} else {
						String tabName = null;
						if ("E0015".equals(eventType)) {
							tabName = entry.getKey();
						} else {
							tabName = new BapiFUPARAREFDao()
									.selectParameterStructure(funcName,
											entry.getKey());
						}
						//
						Class<?> clazz = new ClassUtil(eventType,
								entry.getKey()).load(tabName, firstLoad);
						StructureDao dao = new StructureDao(eventType + "_"
								+ entry.getKey());
						JavaType type = mapper.getTypeFactory()
								.constructCollectionType(List.class, clazz);
						/*
						 * System.out.println("Key:" + entry.getKey() +
						 * "   Value:" + entry.getValue());
						 */

						List<TaskLog> tasklogs1 = new ArrayList<TaskLog>();
						TaskLog taskLog = new TaskLog();
						taskLog.setTask(task);
						taskLog.setLogType(TaskConstant.TASK_LOG_TYPE_STATUS);
						taskLog.setLogTime(new Date());
						taskLog.setLogDesc("ID:" + task.getId()
								+ "got ：[" + entry.getKey() + "]"
								+ list.size() + " data records from agent");
						taskLog.setRunId(task.getRunId());
						tasklogs1.add(taskLog);
						task.setListTaskLogs(tasklogs1);
						taskManager.saveTask(task);
						try {
							if ("E0015".equals(eventType)) {
								String jsonString = jsonMapper.toJson(list);
								List<Object> rayDTOs = (List<Object>) jsonMapper
										.fromJson(jsonString, type);
								System.out.println(entry.getKey() + "------"
										+ rayDTOs.size());
								dao.saveEntityToList(rayDTOs);
							} else {
								
								//for (Object obj : list) {
								for(int i=0;i<list.size();i++){
									Object obj=list.get(i);
									String jsonString = jsonMapper.toJson(obj);
									Object rayDTO = jsonMapper.fromJson(
											jsonString, clazz);
									// if getRAY_STATUS=4  execute delete
									if ("3".equalsIgnoreCase(this
											.getMiscStatus(rayDTO))) {
										dao.deleteEntity(rayDTO);
										// if getRAY_STATUS=2 execute add or update
									} else if ("2".equalsIgnoreCase(this
											.getMiscStatus(rayDTO))) {
										dao.saveEntityToSingle(rayDTO);
										//writing data to FireBase
										if(StringUtils.isNotBlank(fbPush)){
											firebaseService.synDataToFb(fbPush, fbLimit, i, fbPath, rayDTO,task);
										}
										
									}
								}
							}
						} catch (HibernateException ex) {
							List<TaskLog> tasklogs = new ArrayList<TaskLog>();
							task.setTaskStatus(TaskConstant.TASK_STATUS_ERROR_COMPLETED);
							TaskLog taskLog3 = new TaskLog();
							taskLog3.setTask(task);
							taskLog3.setLogType(TaskConstant.TASK_LOG_SAP_DATA);
							taskLog3.setLogTime(new Date());
							taskLog3.setLogDesc("ID:" + task.getId() + ","
									+ ex.getMessage());
							taskLog3.setRunId(task.getRunId());
							tasklogs.add(taskLog3);
							task.setListTaskLogs(tasklogs);
							taskManager.saveTask(task);
						}
						List<TaskLog> tasklogs = new ArrayList<TaskLog>();
						TaskLog taskLog2 = new TaskLog();
						taskLog2.setTask(task);
						taskLog2.setLogType(TaskConstant.TASK_LOG_SAP_DATA);
						taskLog2.setLogTime(new Date());
						taskLog2.setLogDesc("ID:" + task.getId() + "save data：["
								+ entry.getKey() + "]" + dao.countSize()
								+ " records");
						taskLog2.setRunId(task.getRunId());
						tasklogs.add(taskLog2);
						task.setListTaskLogs(tasklogs);
						taskManager.saveTask(task);
					}
				}
			}
			

		} catch (Exception ex) {
			ex.printStackTrace();
			List<TaskLog> tasklogs = new ArrayList<TaskLog>();
			task.setTaskStatus(TaskConstant.TASK_STATUS_UNFINISHED);
			TaskLog taskLog = new TaskLog();
			taskLog.setTask(task);
			taskLog.setLogType(TaskConstant.TASK_LOG_SAP_DATA);
			taskLog.setLogTime(new Date());
			taskLog.setLogDesc(ex.getMessage());
			taskLog.setRunId(task.getRunId());
			tasklogs.add(taskLog);
			task.setListTaskLogs(tasklogs);

		}

		return dataDTO;

	}

	protected String getMiscStatus(Object obj) {
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if ("MISC_STATUS".equals(field.getName())) {
					return (String) field.get(obj);
				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Autowired
	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

}
