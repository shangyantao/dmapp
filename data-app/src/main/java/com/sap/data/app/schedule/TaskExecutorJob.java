package com.sap.data.app.schedule;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.DelegatingErrorHandlingRunnable;
import org.springframework.scheduling.support.TaskUtils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sap.data.core.utils.Threads;

/**
 * 被ScheduledThreadPoolExecutor定时执行的任务类, 并强化了退出控制.
 */
public class TaskExecutorJob implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(TaskExecutorJob.class);

	private int initialDelay = 1;

	private int period = 60;

	private int shutdownTimeout = Integer.MAX_VALUE;

	private ScheduledExecutorService scheduledExecutorService;


	private Map<String,String> mapParas;
	
	private String eventType;
	
	private String triggerTime;
	
	public TaskExecutorJob(String eventType,String triggerTime,Map<String,String> mapParas) {
		
		this.eventType=eventType;
		this.triggerTime=triggerTime;
		this.mapParas=mapParas;
	}
	@PostConstruct
	public void start() throws Exception {
		Validate.isTrue(period > 0);

		//任何异常不会中断schedule执行
		Runnable task = new DelegatingErrorHandlingRunnable(this, TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);

		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat(
				"JdkExecutorJob-%1$d").build());

		//scheduleAtFixedRatefixRate() 固定任务两次启动之间的时间间隔.
		//scheduleAtFixedDelay()      固定任务结束后到下一次启动间的时间间隔.
		scheduledExecutorService.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
	}

	@PreDestroy
	public void stop() {
		Threads.normalShutdown(scheduledExecutorService, shutdownTimeout, TimeUnit.SECONDS);
	}

	/**
	 * 定时打印当前用户数到日志.
	 */
	@Override
	public void run() {
		
		System.out.println(this.eventType+"|"+this.triggerTime);
	
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

	/**
	 * 设置gracefulShutdown的等待时间,单位秒.
	 */
	public void setShutdownTimeout(int shutdownTimeout) {
		this.shutdownTimeout = shutdownTimeout;
	}

	
	
}
