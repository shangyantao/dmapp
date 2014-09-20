package com.sap.data.app.web.task;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sap.data.app.entity.account.User;
import com.sap.data.app.entity.task.Task;
import com.sap.data.app.entity.task.TaskLog;
import com.sap.data.app.service.task.TaskManager;
import com.sap.data.app.ws.DataResourceClient;
import com.sap.data.app.ws.TaskConstant;

@Controller
@RequestMapping(value = "/task")
public class TaskController {

	@Autowired
	TaskManager taskManager;
	
	@Autowired
	DataResourceClient client;
	
	@RequestMapping(value = { "list", "" })
	public String list(Model model) {
		
		List<Task> tasks=taskManager.findAll();
		model.addAttribute("tasks", tasks);
		return "task/taskList";
	}
	
	@RequestMapping(value = { "searchTask/{eventType}", "" })
	public String searchTask(Model model,@PathVariable("eventType") String eventType) {
		
		List<Task> tasks=taskManager.searchTaskByEventType(eventType);
		model.addAttribute("tasks", tasks);
		return "task/taskList";
	}
	
	@RequestMapping(value = "searchData/{userId}/{id}/{inputParams}")
	public String searchData(Model model,@PathVariable("userId") Long userId,@PathVariable("id") Long id,@PathVariable("inputParams") String inputParams) {
		String[] params =inputParams.split("=");
		String tableName=params[1];
		client.setBaseUrl("http://localhost:8080/data-app/rs");
		Task task=taskManager.getTaskById(id);
		String dataString=client.searchData(String.valueOf(userId),task.getEventType(),tableName);
		model.addAttribute("dataString", dataString);
		System.out.println(dataString);
		return "task/viewData";
	} 
	
	@RequestMapping(value = "logs/{id}")
	public String listLogs(Model model,@PathVariable("id") Long id) {
		List<TaskLog> taskLogs=taskManager.findAllTaskLog(id);
		model.addAttribute("taskLogs", taskLogs);
		return "task/logList";
	} 
	
	@RequestMapping(value = "create")
	public String createForm(Model model) {
		/*Session ses=SecurityUtils.getSubject().getSession();
		User user=(User)ses.getAttribute("user");*/
		//System.out.println("createForm"+user.getLoginName());
		model.addAttribute("task", new Task());
		//model.addAttribute("user", user);
		return "task/taskForm";
	} 
	
	@RequestMapping(value = "save")
	public String save(Task task, RedirectAttributes redirectAttributes) {
		task.setTaskStatus(TaskConstant.TASK_STATUS_NOT_STARTED);
		taskManager.saveTask(task);
		redirectAttributes.addFlashAttribute("message", "创建任务" + task.getId()+ "成功");
		return "redirect:/task/list/";
	}
	
	@RequestMapping(value = "update/{id}")
	public String updateForm(Model model,@PathVariable("id") Long id) {
		Task task=taskManager.getTaskById(id);
		model.addAttribute("task", task);
		return "task/updateForm";
	}
	
	@RequestMapping(value = "updateTask")
	public String updateTask(@ModelAttribute("task") Task task, RedirectAttributes redirectAttributes) {
		taskManager.saveTask(task);
		redirectAttributes.addFlashAttribute("message", "修改任务" + task.getId()+ "成功");
		return "redirect:/task/list/";
	}
	
	@RequestMapping(value = "execute/{id}")
	public String executeTask(Model model,@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		Task task=taskManager.getTaskById(id);
		task.setTriggerTime(TaskConstant.TASK_ACTION_IMMEDIATE);
		task.setTaskStatus(TaskConstant.TASK_STATUS_NOT_STARTED);
		taskManager.saveTask(task);
		redirectAttributes.addFlashAttribute("message", "已经成功发出执行任务ID为"+task.getId()+"的命令" );
		return "redirect:/task/list/";
	}
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		taskManager.deleteTask(id);
		redirectAttributes.addFlashAttribute("message", "删除任务成功");
		return "redirect:/task/list/";
	}
	
	@RequestMapping(value = "copy/{id}")
	public String copyTask(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		Task task=taskManager.getTaskById(id);
		Task copyTask=new Task();
		copyTask.setEventType(task.getEventType());
		copyTask.setInputParas(task.getInputParas());
		copyTask.setTaskPriority(task.getTaskPriority());
		copyTask.setTaskStatus(TaskConstant.TASK_STATUS_NOT_STARTED);
		copyTask.setTriggerTime(task.getTriggerTime());
		copyTask.setUserId(task.getUserId());
		taskManager.saveTask(copyTask);
		redirectAttributes.addFlashAttribute("message", "复制任务成功");
		return "redirect:/task/list/";
	}
}
