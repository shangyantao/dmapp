package com.sap.data.app.schedule;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.DelegatingErrorHandlingRunnable;
import org.springframework.scheduling.support.TaskUtils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sap.data.app.ws.TaskConstant;
import com.sap.data.app.ws.TaskResourceClient;
import com.sap.data.app.ws.Utils;
import com.sap.data.app.ws.dto.TaskDTO;
import com.sap.data.core.utils.Threads;

/**
 * 被ScheduledThreadPoolExecutor定时执行的任务类, 并强化了退出控制.
 */
public class JdkExecutorJob implements Runnable {

	private static Logger logger = LoggerFactory
			.getLogger(JdkExecutorJob.class);

	private int initialDelay = 0;

	private int period = 1;

	private int shutdownTimeout = Integer.MAX_VALUE;

	private ScheduledExecutorService scheduledExecutorService;

	private TaskResourceClient rs;

	@PostConstruct
	public void start() throws Exception {

		rs.setBaseUrl("http://localhost:8080/data-app/rs");
		System.getProperties().setProperty("http.proxySet", "true");
		System.getProperties().setProperty("http.proxyHost",
				"proxy.pal.sap.corp");
		System.getProperties().setProperty("http.proxyPort", "8080");
		Validate.isTrue(period > 0);

		// 任何异常不会中断schedule执行
		Runnable task = new DelegatingErrorHandlingRunnable(this,
				TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);

		scheduledExecutorService = Executors
				.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder()
						.setNameFormat("JdkExecutorJob-%1$d").build());

		// scheduleAtFixedRatefixRate() 固定任务两次启动之间的时间间隔.
		// scheduleAtFixedDelay() 固定任务结束后到下一次启动间的时间间隔.
		scheduledExecutorService.scheduleAtFixedRate(task, initialDelay,
				period, TimeUnit.SECONDS);
	}

	@PreDestroy
	public void stop() {
		Threads.normalShutdown(scheduledExecutorService, shutdownTimeout,
				TimeUnit.SECONDS);
	}

	/**
	 * 定时打印当前用户数到日志.
	 */
	@Override
	public void run() {

		TaskMethod();
		/*List<TaskDTO> list = rs.findTasks();
		System.out.println(list.size() + "11111111111");
		for (TaskDTO taskDTO : list) {
			System.out.println("----------------");
			String eventType = taskDTO.getEventType();
			String triggerTime = taskDTO.getTriggerTime();
			String inputParas = taskDTO.getInputParas();
			String userId = String.valueOf(taskDTO.getUserId());
			Map<String, String> mapParas = Utils.parasToMap(inputParas);
			
			 * Set<Map.Entry<String,String>> setParas=mapParas.entrySet(); for
			 * (Map.Entry<String, String> entry : setParas) {
			 * System.out.println(entry.getKey()+"="+entry.getValue()+" "); }
			 * System
			 * .out.println("EventType:"+eventType+"triggerTime："+triggerTime);
			 
			TaskSpringCronJob taskSpringCronJob = new TaskSpringCronJob(
					triggerTime, eventType, triggerTime, mapParas, userId);
			// TaskExecutorJob taskJob=new TaskExecutorJob(eventType,
			// triggerTime, mapParas);
			try {
				taskSpringCronJob.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			taskDTO.setTaskStatus(TaskConstant.TASK_STATUS_HAVE_STARTED);
			rs.updateTaskStatus(taskDTO);
		}*/
		/*
		 * rs.setBaseUrl("http://115.28.205.21:8080/data-app/rs");
		 * ClientResponse clientResponse =rs.findTasks();
		 * System.out.println(clientResponse);
		 */
	}

	public synchronized void TaskMethod() {

		List<TaskDTO> list = rs.findTasks("40288b8147cd16ce0147cd236df20000", "40288b8146fb28e60146fb2ce1890002");;
		
		System.out.println(list.size()+"==============");
		for (TaskDTO taskDTO : list) {
			synchronized (rs) {

				System.out.println("----------------");
				String eventType = taskDTO.getEventType();
				String triggerTime = taskDTO.getTriggerTime();
				String inputParas = taskDTO.getInputParas();
				String userId = String.valueOf(taskDTO.getUserId());
				Map<String, String> mapParas = Utils.parasToMap(inputParas);
				/*
				 * Set<Map.Entry<String,String>> setParas=mapParas.entrySet();
				 * for (Map.Entry<String, String> entry : setParas) {
				 * System.out.println(entry.getKey()+"="+entry.getValue()+" ");
				 * } System.out.println("EventType:"+eventType+"triggerTime："+
				 * triggerTime);
				 */
				TaskSpringCronJob taskSpringCronJob = new TaskSpringCronJob(taskDTO);
				// TaskExecutorJob taskJob=new TaskExecutorJob(eventType,
				// triggerTime, mapParas);
				try {
					taskSpringCronJob.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
				taskDTO.setTaskStatus(TaskConstant.TASK_STATUS_HAVE_STARTED);
				rs.updateTaskStatus(taskDTO);
			}
		}
	}

	/**
	 * 设置任务初始启动延时时间.
	 */
	public void setInitialDelay(int initialDelay) {
		this.initialDelay = initialDelay;
	}

	/**
	 * 设置任务间隔时间,单位秒.
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	@Autowired
	public void setRs(TaskResourceClient rs) {
		this.rs = rs;
	}

	/**
	 * 设置gracefulShutdown的等待时间,单位秒.
	 */
	public void setShutdownTimeout(int shutdownTimeout) {
		this.shutdownTimeout = shutdownTimeout;
	}

}
