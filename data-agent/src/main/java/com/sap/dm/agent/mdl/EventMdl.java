/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.dm.agent.mdl;

import com.sap.data.db.util.ConstructorUtil;
import com.sap.data.db.util.NotFoundException;
import com.sap.data.db.util.ClassUtil;
import com.sap.data.db.util.PropertyUtil;
import com.sap.data.db.util.ThreadLocalUtil;
import com.sap.data.db.dao.BapiDD03LDao;
import com.sap.data.db.dao.BapiDD04TDao;
import com.sap.data.db.dao.BapiFUPARAREFDao;
import com.sap.data.db.pojo.BapiDD03LPojo;
import com.sap.data.db.pojo.BapiDD04TPojo;
import com.sap.data.db.pojo.BapiFUPARAREFPojo;
import com.sap.data.db.pojo.EventPojo;
import com.sap.data.db.dao.EventDao;
import com.sap.data.db.dao.StructureDao;
import com.sap.dm.agent.jco.JCoFuDD;
import com.sap.dm.agent.jco.JCoPool;
import com.sap.dm.agent.jco.JCoRdTB;
import com.sap.dm.agent.jco.JCoUtil;
import com.sap.dm.agent.ws.DataDTO;
import com.sap.dm.agent.ws.FbConstants;
import com.sap.dm.agent.ws.TaskDTO;
import com.sap.dm.agent.ws.TaskResourceClient;
import com.sap.dm.agent.ws.Utils;
import com.sap.conn.jco.JCoException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

/**
 *
 * @author C5178395
 */
public class EventMdl {
    
    protected String eventNumber;
    protected String funcName;
    protected String taskId;
    protected String destinationName = "ABAP_AS_WITH_POOL_C5178395";
    protected String queryDelimiter = "^";
    protected String querySlash = "\\";
    protected int defaultMaxCount = 50000;
    protected TaskDTO taskDto;
    protected DataDTO dto = new DataDTO();
    protected Map<String, String> input;
    protected Map<String, Object> export;
    protected TaskResourceClient client;
    protected EventDao eventDao;
    protected final Object lock = new Object();
    
    public EventMdl() {
        this.client = new TaskResourceClient(PropertyUtil.getServerUrl());
        this.eventDao = new EventDao();
        this.export = new HashMap<String, Object>();
    }
    
    public void triggers(TaskDTO taskDto) {
        this.taskDto = taskDto;
        this.eventNumber = taskDto.getEventType();
        if(null != this.eventNumber && !this.eventNumber.isEmpty()) {
        	try {
                this.taskId = taskDto.getId().toString();
                this.input = Utils.parasToMap(taskDto.getInputParas());
                this.client.sendTaskStartLog(this.taskId, "ID:" + this.taskId + "的任务-开始执行" + this.input.toString());
            	destinationName = this.input.get("SAP_SYSTEM") + "_" + this.taskDto.getUserId();
                ThreadLocalUtil.setTaskDTO(this.eventNumber, String.valueOf(taskDto.getUserId()), taskDto.getInputParas(), String.valueOf(taskDto.getId()), String.valueOf(taskDto.getRunId()));
                if("A0001".equals(this.eventNumber)) {
                	this.switchConnJCo();
                } else if("E0000".equals(this.eventNumber)) {
                	if(this.switchUsi()) {
                		this.switchEvent();
                	}
                } else if("E0015".equals(this.eventNumber)) {
                	if(this.switchUsi()) {
                		this.switchTable();
                	}
                } else {
                	if(this.switchUsi()) {
                		this.switchOther();
                	}
                }
        	} catch (JCoException | NotFoundException | InstantiationException | IllegalAccessException | ClassNotFoundException | IOException | DocumentException | SAXException ex) {
            	Logger.getLogger(EventMdl.class.getName()).log(Level.SEVERE, null, ex);
                String errorMessage = ex.getMessage();
            	this.client.sendTaskErrorLog(this.taskId, "ID:" + this.taskId + "," + (errorMessage.length() > 200 ? errorMessage.substring(0, 200) : errorMessage));
            }
        } else {
        	this.client.sendTaskErrorLog(this.taskId, "Event Number is null.");
        }
    }
    
    private boolean switchUsi() throws NotFoundException, FileNotFoundException, ClassNotFoundException, IOException, DocumentException, SAXException {
    	this.funcName = this.eventDao.selectFuncName(this.eventNumber);
		if(null != this.funcName && !this.funcName.isEmpty()) {
        	System.out.println("Func Name:" + this.funcName);
            String classpath = ConstructorUtil.class.getResource("/").getPath() + PropertyUtil.getHbPjResource();
            this.client.sendTaskStartLog(this.taskId, "classpath:" + classpath);
            boolean loadClassFlag = this.loadClass(this.eventNumber, this.funcName, this.input);
            if(loadClassFlag) {
                this.export.put("FUNC_NAME", this.funcName);
                this.export.put("EVENT_NUMBER", this.eventNumber);
                this.export.put("USER_ID", this.taskId);
                this.export.put("FIRST_LOAD", this.input.get("FIRST_LOAD"));
                this.export.put(FbConstants.FB_PUSH_KEY, this.input.get(FbConstants.FB_PUSH_KEY));
                this.export.put(FbConstants.FB_LIMIT, this.input.get(FbConstants.FB_LIMIT));
                this.export.put(FbConstants.FB_PATH, this.input.get(FbConstants.FB_PATH));
                taskDto.setCompanyId(PropertyUtil.getCompanyId());
                dto.setTaskDTO(taskDto);
                return true;
            } else {
            	this.client.sendTaskErrorLog(this.taskId, "Class and Table load fail.");
            	return false;
            }
		} else {
			this.client.sendTaskErrorLog(this.taskId, "FUNC_NAME not found. Event Number (" + this.eventNumber +")");
			return false;
        }
    }
    
    private void switchConnJCo() throws IOException, JCoException {
    	String asHost = this.input.get("SERVER_NAME");
    	String sysNr = this.input.get("SAP_INSTANCE_NUMBER");
    	String client = this.input.get("SAP_CLIENT");
    	String user = this.input.get("SAP_USER");
    	String passwd = this.input.get("SAP_PASSWORD");
    	String lang = this.input.get("SAP_LANGUAGE");
    	String codePage = this.input.get("codepage");
    	String poolCapacity = this.input.get("pool_capacity");
    	String peakLimit = this.input.get("peak_limit");
    	this.client.sendTaskLog(this.taskId, "Add Destination start.");
    	JCoPool jCoPool = new JCoPool();
    	jCoPool.addJCoDestination(destinationName, asHost, sysNr, client, user, passwd, lang, codePage, poolCapacity, peakLimit);
    	this.client.sendTaskLog(this.taskId, "Add Destination end.");
    	jCoPool.getJCoDestination(this.destinationName);
    	this.client.sendTaskLog(this.taskId, "Connected SAP success.");
    	this.client.sendTaskEndLog(this.taskId, "Test conecte finish.");
    }
    
    @SuppressWarnings("unchecked")
	private void switchEvent() throws JCoException, NotFoundException, UnsupportedEncodingException, InstantiationException, IllegalAccessException { 
        if(input.get("FUNC_NAME") != null) {
            String eventNumberFlag = eventDao.selectEventNumber(input.get("FUNC_NAME"));
            if(null == eventNumberFlag) {
            	EventPojo createEvent = eventDao.buildEvent(String.valueOf(taskDto.getUserId()), input.get("FUNC_NAME"));
            	if(null != createEvent && null != createEvent.getEVENT_NUMBER()) {
            		eventNumberFlag = createEvent.getEVENT_NUMBER();
            		client.sendTaskLog(taskId, "ID:" + taskId + "的任务-创建一个新的Event Number(" + eventNumberFlag + ")");
            		List<EventPojo> createEvents = new ArrayList<EventPojo>();
            		createEvents.add(createEvent);
            		export.put("A0001", createEvents);
            	} else {
            		client.sendTaskLog(taskId, "ID:" + taskId + "的任务-创建一个Event Number失败.");
            	}
            } else {
                client.sendTaskLog(taskId, "ID:" + taskId + "的任务-Bapi[" + input.get("FUNC_NAME") + "] 对应的Event Number(" + eventNumberFlag + ")已经存在");
            }
            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-抽取数据-开始");
            JCoFuDD job = new JCoFuDD(funcName, destinationName);
            List<?> list = job.getFUPARAREF(input.get("FUNC_NAME"));
            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-抽取数据-（FUPARAREF:" + list.size() + "）");
            List<?> list2 = job.getDD03L(list);
            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-抽取数据-（DD03L:" + list2.size() + "）");
            List<?> list3 = job.getDD04T(list2);
            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-抽取数据-（DD04T:" + list3.size() + "）");
            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-抽取数据-完成");
            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-保存数据到Agent DB-开始");
            new BapiFUPARAREFDao().save((List<BapiFUPARAREFPojo>)list);
            new BapiDD03LDao().save((List<BapiDD03LPojo>)list2);
            new BapiDD04TDao().save((List<BapiDD04TPojo>)list3);
            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-保存数据到Agent DB-完成,FUPARAREF.size(" + list.size() + "),DD03L.size(" + list2.size() + "),DD04T.size(" + list3.size() + ")");
            export.put("FUPARAREF", list);
            export.put("DD03L", list2);
            export.put("DD04T", list3);
            export.put("FUNC_NAME", funcName);
            export.put("EVENT_NUMBER", eventNumber);
            dto.setData(export);
            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-发送数据到服务器-开始");
            client.postData(dto);
            client.sendTaskEndLog(taskId, "ID:" + taskId + "的任务-发送数据到服务器-完成");
        } else if(input.get("TABNAME") != null) {
            String[] tabNames = input.get("TABNAME").split(querySlash + queryDelimiter);
            List<Object> list = new ArrayList<Object>();
            for (String tabName : tabNames) {
                BapiFUPARAREFPojo pojo = new BapiFUPARAREFPojo();
                pojo.setSTRUCTURE(tabName);
                list.add(pojo);
            }
            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-抽取数据-开始");
            JCoFuDD job = new JCoFuDD(funcName, destinationName);
            List<?> list2 = job.getDD03L(list);
            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-抽取数据-（DD03L:" + list2.size() + "）");
            List<?> list3 = job.getDD04T(list2);
            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-抽取数据-（DD04T:" + list3.size() + "）");
            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-抽取数据-完成");
            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-保存数据到Agent DB-开始");
            new BapiDD03LDao().save((List<BapiDD03LPojo>)list2);
            new BapiDD04TDao().save((List<BapiDD04TPojo>)list3);
            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-保存数据到Agent DB-完成,FUPARAREF.size(" + list.size() + "),DD03L.size(" + list2.size() + "),DD04T.size(" + list3.size() + ")");
            export.put("DD03L", list2);
            export.put("DD04T", list3);
            export.put("FUNC_NAME", funcName);
            export.put("EVENT_NUMBER", eventNumber);
            dto.setData(export);
            client.sendTaskLog(taskId, "ID:" + taskId + "的任务-发送数据到服务器-开始");
            client.postData(dto);
            client.sendTaskEndLog(taskId, "ID:" + taskId + "的任务-发送数据到服务器-完成");
        } 
    }
    
    private void switchTable() throws JCoException, NotFoundException {
        
        int size;
        int loopCount;
        int count = 0;
        int rowSkips = 0;
        int rowCount = input.get("ROWCOUNT") != null ? getInt(input.get("ROWCOUNT").trim()) : defaultMaxCount;
        int rowMax = input.get("ROWMAX") != null ? getInt(input.get("ROWMAX").trim()) : defaultMaxCount;
        String[] options = input.get("OPTIONS") != null ? input.get("OPTIONS").split(querySlash + queryDelimiter) : null;
        JCoRdTB jr = new JCoRdTB(funcName, destinationName);
        client.sendTaskLog(taskId, "ID:" + taskId + "的任务-抽取数据-开始");
        if(rowMax % rowCount > 0) {
            loopCount = rowMax / rowCount + 1;
        } else {
            loopCount = rowMax / rowCount;
        }
        String queryTable = input.get("QUERY_TABLE");
        List<?> list = jr.getTable(queryTable, rowSkips, rowCount, options);
        size = list.size();
        StructureDao dud = new StructureDao(eventNumber + "_" + queryTable);
        client.sendTaskLog(taskId, "ID:" + taskId + "的任务-保存数据到Agent DB-开始Loop(1/" + loopCount + ")");
        dud.saveEntityToList(list);
        client.sendTaskLog(taskId, "ID:" + taskId + "的任务-保存数据到Agent DB-完成Loop(1/" + loopCount + ")" + size);
        export.put(queryTable, list);
        dto.setData(export);
        client.sendTaskLog(taskId, "ID:" + taskId + "的任务-发送数据到服务器-开始Loop(1/" + loopCount + ")");
        client.postData(dto);
        client.sendTaskLog(taskId, "ID:" + taskId + "的任务-发送数据到服务器-完成Loop(1/" + loopCount + ")");
        count += size;
        for(int i = 2; size == rowCount && count < rowMax; i++) {
        	export.remove("FIRST_LOAD");
            rowSkips = rowCount * (i - 1);
            JCoRdTB jr2 = new JCoRdTB(funcName, destinationName);
            List<?> list2 = jr2.getTable(queryTable, rowSkips, rowCount, options);
            size = list2.size();
            count += size;
            if(count > rowMax) {
                List<Object> list3 = new ArrayList<Object>();
                for(int j = 0; j < (list2.size() - (count - rowMax)); j++) {
                    list3.add(list2.get(j));
                }
                client.sendTaskLog(taskId, "ID:" + taskId + "的任务-保存数据到Agent DB-开始Loop(" + i + "/" + loopCount + ")");
                dud.saveEntityToList(list3);
                client.sendTaskLog(taskId, "ID:" + taskId + "的任务-保存数据到Agent DB-完成Loop(" + i + "/" + loopCount + ")" + size);
                export.put(queryTable, list3);
                dto.setData(export);
                client.sendTaskLog(taskId, "ID:" + taskId + "的任务-发送数据到服务器-开始Loop(" + i + "/" + loopCount + ")");
                client.postData(dto);
                client.sendTaskLog(taskId, "ID:" + taskId + "的任务-发送数据到服务器-完成Loop(" + i + "/" + loopCount + ")");
            }else {
                client.sendTaskLog(taskId, "ID:" + taskId + "的任务-保存数据到Agent DB-开始Loop(" + i + "/" + loopCount + ")");
                dud.saveEntityToList(list2);
                client.sendTaskLog(taskId, "ID:" + taskId + "的任务-保存数据到Agent DB-完成Loop(" + i + "/" + loopCount + ")" + size);
                export.put(queryTable, list2);
                dto.setData(export);
                client.sendTaskLog(taskId, "ID:" + taskId + "的任务-发送数据到服务器-开始Loop(" + i + "/" + loopCount + ")");
                client.postData(dto);
                client.sendTaskLog(taskId, "ID:" + taskId + "的任务-发送数据到服务器-完成Loop(" + i + "/" + loopCount + ")");
            }
        }
        client.sendTaskEndLog(taskId, "ID:" + taskId + "的任务-发送数据到服务器-完成");
    }
    
    private void switchOther() throws JCoException, NotFoundException {
        client.sendTaskLog(taskId, "ID:" + taskId + "的任务-抽取数据-开始");
        JCoUtil ju = new JCoUtil(funcName, destinationName);
        Map<String, Object> jcoExport = ju.getExport(input);
        client.sendTaskLog(taskId, "ID:" + taskId + "的任务-抽取数据-完成");
        RayCompare rc = new RayCompare(eventNumber, jcoExport);
        client.sendTaskLog(taskId, "ID:" + taskId + "的任务-Table数据比较-开始");
        Map<String, Object> compareExport = rc.compare(String.valueOf(taskDto.getUserId()), taskId, client);
        client.sendTaskLog(taskId, "ID:" + taskId + "的任务-Table数据比较-完成");
        if(rc.isUpdateData()) {
        	export.putAll(compareExport);
        	dto.setData(export);
        	client.sendTaskLog(taskId, "ID:" + taskId + "的任务-发送数据到服务器-开始");
        	System.out.println("Sending data....");
        	client.postData(dto);
        	System.out.println("Send data finish.");
        	client.sendTaskEndLog(taskId, "ID:" + taskId + "的任务-发送数据到服务器-完成");
        } else {
        	client.sendTaskLog(taskId, "ID:" + taskId + "的任务-SAP数据没有更新");
        	client.sendTaskEndLog(taskId, "ID:" + taskId + "的任务-完成");
        }
        
    }
    
    private boolean loadClass(String eventNumber, String funcName, Map<String, String> input) throws FileNotFoundException, ClassNotFoundException, IOException, DocumentException, SAXException, NotFoundException {
        synchronized(lock) {
	    	String firstLoad = input.get("FIRST_LOAD");
	        if("E0015".equals(eventNumber)) {
	            String queryTable = input.get("QUERY_TABLE");
	            if(queryTable != null && !queryTable.isEmpty()) {
	                new ClassUtil(queryTable).load(queryTable, firstLoad);
	            }
	        } else if(!"E0000".equals(eventNumber)){
	            List<BapiFUPARAREFPojo> list = new BapiFUPARAREFDao().selectETParameters(funcName);
	            for(BapiFUPARAREFPojo pojo : list) {
	                new ClassUtil(pojo.getId().getPARAMETER()).load(pojo.getSTRUCTURE(), firstLoad);
	            }
	        }
	        this.client.sendTaskStartLog(this.taskId, "ID:" + "Agent Class and Table load success.");
	        return true;
        }
    }
    
    private int getInt(String strng) {
        return Integer.valueOf(strng).intValue();
    }
}
