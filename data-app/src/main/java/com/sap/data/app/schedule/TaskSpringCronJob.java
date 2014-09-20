package com.sap.data.app.schedule;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.sap.data.app.service.account.AccountManager;
import com.sap.data.app.ws.Utils;
import com.sap.data.app.ws.dto.TaskDTO;
import com.sap.data.core.utils.Threads;

/**
 * 使用Spring的ThreadPoolTaskScheduler执行Cron式任务的类, 并强化了退出控制.
 */
public class TaskSpringCronJob implements Runnable {

	private static Logger logger = LoggerFactory
			.getLogger(TaskSpringCronJob.class);

	private String cronExpression;

	private int shutdownTimeout = Integer.MAX_VALUE;

	private ThreadPoolTaskScheduler threadPoolTaskScheduler;

	private AccountManager accountManager;

	private Map<String, String> mapParas;

	private String eventType;

	private String triggerTime;
	
	private String  userId;
	
	private TaskDTO taskDTO;

	public TaskSpringCronJob(String cronExpression,String eventType, String triggerTime,
			Map<String, String> mapParas,String userId) {

		this.cronExpression=cronExpression;
		this.eventType = eventType;
		this.triggerTime = triggerTime;
		this.mapParas = mapParas;
		this.userId=userId;
	}

	public TaskSpringCronJob(TaskDTO taskDTO){
		this.cronExpression=taskDTO.getTriggerTime().trim();
		this.taskDTO=taskDTO;
	}
	
	@PostConstruct
	public void start() {
		Validate.notBlank(cronExpression);

		threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setThreadNamePrefix("SpringCronJob");
		threadPoolTaskScheduler.initialize();
		if(true==StringUtils.equalsIgnoreCase(cronExpression, "immediate")){
			threadPoolTaskScheduler.execute(this);
		}else{
			threadPoolTaskScheduler.schedule(this, new CronTrigger(cronExpression));
			

		}
		
	}

	@PreDestroy
	public void stop() {
		ScheduledExecutorService scheduledExecutorService = threadPoolTaskScheduler
				.getScheduledExecutor();
		Threads.normalShutdown(scheduledExecutorService, shutdownTimeout,
				TimeUnit.SECONDS);

	}

	/**
	 * 定时打印当前用户数到日志.
	 */
	@Override
	public void run() {

		Long taskId=taskDTO.getId();
		String eventType = taskDTO.getEventType();
		String triggerTime = taskDTO.getTriggerTime().trim();
		String inputParas = taskDTO.getInputParas();
		String userId = String.valueOf(taskDTO.getUserId());
		Map<String, String> mapParas = Utils.parasToMap(inputParas);
		System.out.println("taskId="+taskId+"|"+eventType+"|"+triggerTime);
		
		
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 * 设置gracefulShutdown的等待时间,单位秒.
	 */
	public void setShutdownTimeout(int shutdownTimeout) {
		this.shutdownTimeout = shutdownTimeout;
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
}
