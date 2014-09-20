package com.sap.dm.agent.ws;

import com.sap.dm.agent.mdl.EventMdl;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.sap.dm.agent.ws.TaskDTO;
import com.sap.dm.agent.ws.Threads;

import org.apache.commons.lang3.StringUtils;

/**
 * 使用Spring的ThreadPoolTaskScheduler执行Cron式任务的类, 并强化了退出控制.
 */
public class TaskSpringCronJob implements Runnable {

    protected static Logger logger = LoggerFactory.getLogger(TaskSpringCronJob.class);

    protected Map<String, String> mapParas;

    protected String eventType;

    protected String triggerTime;

    protected String userId;

    private String cronExpression;

    private int shutdownTimeout = Integer.MAX_VALUE;

    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private TaskDTO taskDTO;
    
    private Integer status = 0;
    
    public static int line = 0;

    public TaskSpringCronJob(String cronExpression, String eventType, String triggerTime,
            Map<String, String> mapParas, String userId) {

        this.cronExpression = cronExpression;
        this.eventType = eventType;
        this.triggerTime = triggerTime;
        this.mapParas = mapParas;
        this.userId = userId;
    }

    public TaskSpringCronJob(TaskDTO taskDTO) {
        this.cronExpression = taskDTO.getTriggerTime().trim();
        this.taskDTO = taskDTO;
    }

    @PostConstruct
    public void start() {
        Validate.notBlank(cronExpression);

        threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setThreadNamePrefix("SpringCronJob");
        //threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.initialize();
        if (true == StringUtils.equalsIgnoreCase(cronExpression, "immediate")) {
            threadPoolTaskScheduler.execute(this);
        } else {
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
//        TaskResourceClient client = new TaskResourceClient();
//        client.setBaseUrl("http://10.58.79.30:8088/data-app/rs");//115.28.205.21 10.59.154.191
        EventMdl ret = new EventMdl();
        ret.triggers(taskDTO);
        
//        try {
//            File file = new File("C:\\log\\log.txt");
//            if(!file.exists()) {
//                file.createNewFile();
//            }
//            FileWriter write = new FileWriter(file, true);
//            
//            write.write(line + String.valueOf(taskDTO.getId()) + "\n");
//            write.close();
//            line ++;
//        } catch (IOException ex) {
//            System.out.println("IOException++++++++++++++");
//        }
//        
//        
//        String taskId = taskDTO.getId().toString();
//        String eventType = taskDTO.getEventType();
//        String triggerTime = taskDTO.getTriggerTime().trim();
//        String inputParas = taskDTO.getInputParas();
//        String userId = String.valueOf(taskDTO.getUserId());
//        Map<String, String> mapParas = Utils.parasToMap(inputParas);
//
//        mapParas.put("USER_ID", userId);
//        mapParas.put("TASK_ID", taskId);
//        RayEvent re = new RayEvent(eventType);
//        Map<String, Object> result = re.trigger(mapParas);
//        boolean flag = false;
//        for(Map.Entry<String, Object> entry : result.entrySet()) {
//            if(entry.getValue() instanceof List && !((List)entry.getValue()).isEmpty() && flag == false) {
//                flag = true;
//            }
//        }
//        client.sendTaskLog(taskId, "ID:" + taskId + "的任务-发送数据到服务器-开始");
//        if(flag) {
//            DataDTO dto = new DataDTO();
//            dto.setData(result);
//            dto.setTaskDTO(taskDTO);
//            client.postData(dto);
//            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-发送数据到服务器-完成");
//            System.out.println("postData.");
//        } else {
//            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-完成-没有数据更新");
//            taskDTO.setTaskStatus(TaskConstant.TASK_STATUS_COMPLETED);
//            client.updateTaskStatus(taskDTO);
//            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-完成-没有数据更新");
//        }
        this.status = 1;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
