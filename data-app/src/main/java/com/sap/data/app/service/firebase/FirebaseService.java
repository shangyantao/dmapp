package com.sap.data.app.service.firebase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.sap.data.app.entity.account.User;
import com.sap.data.app.entity.task.Task;
import com.sap.data.app.entity.task.TaskLog;
import com.sap.data.app.service.task.TaskManager;
import com.sap.data.app.ws.TaskConstant;
import com.sap.data.core.mapper.JsonMapper;

@Component
public class FirebaseService {

	private static String FIREBASE_ROOT="https://dmapp-push.firebaseio.com/";
	
	private Task task;
	private static boolean completeFlag=false;
	private Firebase dataRef=null;
	@Autowired
	private TaskManager taskManager;
	
	
	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}
	public FirebaseService() {
		dataRef = new Firebase(FIREBASE_ROOT);
	}
	public boolean pushData(String child,Object obj,Task taskp){
		this.task=taskp;
		Firebase pushRef=dataRef.child(child);
		pushRef.push().setValue(obj, new Firebase.CompletionListener() {
			
			@Override
			public void onComplete(FirebaseError e, Firebase fb) {
				TaskLog taskLog = new TaskLog();
				taskLog.setTask(task);
				taskLog.setLogType(TaskConstant.TASK_LOG_SYN_FB);
				taskLog.setLogTime(new Date());
				taskLog.setRunId(task.getRunId());
				if(e==null){
					completeFlag=true;
					taskLog.setLogDesc("it's successful to push data to firebase   ");

				}else{
					taskLog.setLogDesc("it's failing to push data to firebase. Cause by: "+e.getMessage());

				}
				ArrayList<TaskLog> tasklogs=new ArrayList<TaskLog>();
				tasklogs.add(taskLog);
				task.setListTaskLogs(tasklogs);
				taskManager.saveTask(task);
			}
		});
		return completeFlag;
	}
	

	public void synDataToFb(String fbPush,String fbLimit,int step,String fbPath,Object rayDTO,Task task){
		System.out.println("fbPush:"+fbPush+"fbLimit:"+fbLimit+"step:"+step);
		//假如fbPush不等于空 并且等于X或x，开启数据同步到Firebase
		if(StringUtils.isNotBlank(fbPush) && fbPush.equalsIgnoreCase(FbConstants.FB_PUSH_VALUE)){
			//假如fbLimit不为空，将同步fbLimit条数据到Firebase
			if(StringUtils.isNotBlank(fbLimit)){
				if(Integer.parseInt(fbLimit)-1!=step){
					boolean flag=pushData(fbPath, rayDTO,task);
					System.out.println("when limit isn't null ,complate state:"+flag);
				}
				//否则fbLimit为空，将默认同步FB_LIMIT_DEFAULT_VALUE条数据到firebase
			}else{
				
				if(Integer.parseInt(fbLimit)!=FbConstants.FB_LIMIT_DEFAULT_VALUE){
					boolean flag=pushData(fbPath, rayDTO,task);
					System.out.println("when limit is null ,complate state:"+flag);
				}
			} 
			
		}
	}
	/**
	 * 
	 * @return
	 */
	public boolean writeData(){
		
		Firebase dataRef = new Firebase(FIREBASE_ROOT);
		User tony=new User();
		tony.setLoginName("tony");
		tony.setEmail("tony@126.com");
		
		User joey=new User();
		joey.setLoginName("joey");
		joey.setEmail("joey@126.com");
		
		Map<String, User> users = new HashMap<String, User>();
		users.put("tony", tony);
		users.put("joey", joey);
		

		JsonMapper jsonMapper = new JsonMapper();
		ArrayList list=new ArrayList();
		list.add(tony);
		list.add(joey);
		String jsonString=jsonMapper.toJson(list);
		//System.out.println(jsonString);
		dataRef.child("userlist").push().setValue(list,new Firebase.CompletionListener() {
			
			@Override
			public void onComplete(FirebaseError e, Firebase arg1) {
				if(e!=null){
					System.out.println(e.getMessage());
				}
				System.out.println("successful!");
				
			}
		});
		return true;
	}
	
	public static void main(String[] args) {
		FirebaseService fs=new FirebaseService();
		while(true)
		fs.writeData();
		
	}
	
}
