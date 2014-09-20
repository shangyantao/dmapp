package com.sap.dm.agent.ws;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sap.data.db.util.PropertyUtil;

public class TimerExecutorJob extends TimerTask {
    
	protected final long delay = 1000;
	protected final long period = 1000;
    protected String companyId;
    protected String agentId;
    protected String serverUrl;
	protected TaskResourceClient rs;
	protected Timer timer;
	protected String hostAddress;
	protected String hostName;
	protected String connStatus = "ununited";
    
    public TimerExecutorJob() {
        this.companyId = PropertyUtil.getCompanyId();
        this.agentId = PropertyUtil.getAgentId();
        this.serverUrl = PropertyUtil.getServerUrl();
        this.rs = new TaskResourceClient(this.serverUrl);
        this.timer = new Timer();
		try {
			InetAddress addres = InetAddress.getLocalHost();
			this.hostAddress = addres.getHostAddress().toString();
	        this.hostName = addres.getHostName().toString();
		} catch (UnknownHostException ex) {
			Logger.getLogger(TimerExecutorJob.class.getName()).log(Level.SEVERE, null, ex);
		}
        
    }

    @Override
    public void run() {
    	try {
    		this.connStatus = "connected".equals(this.connStatus) ? "connected" : "connecting";
    		System.out.println(this.serverUrl + " " + new Date() + " " + this.companyId);
        	List<TaskDTO> list = rs.findTasks(companyId, agentId, this.hostAddress + "OOO", this.hostName);
        	System.out.println("task count:" + list.size());
        	for (TaskDTO taskDTO : list) {
        		System.out.println("TaskId:" + taskDTO.getId());
            	TaskSpringCronJob taskSpringCronJob = new TaskSpringCronJob(taskDTO);
                taskSpringCronJob.start();
        	}
        	this.connStatus = "connected";
        } catch (Exception ex) {
        	this.stopTimer();
        	this.connStatus = "ununited";
        	System.out.println("Error to connecte server:" + ex.getMessage());
        }
    }
    
    public void startTimer() {
    	this.timer.schedule(this, delay, period);
    }
    
    public void stopTimer() {
    	this.timer.cancel();
    	this.connStatus = "ununited";
    }
    
    public String getConnStatus() {
    	return this.connStatus;
    }
    
}
