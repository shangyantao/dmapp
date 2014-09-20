/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.dm.agent.mdl;

import com.sap.data.db.dao.StructureDao;
import com.sap.data.db.util.NotFoundException;
import com.sap.dm.agent.ws.TaskResourceClient;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author C5178395
 */
public class RayCompare {
    
    protected String eventNumber;
    protected Map<String, Object> funcParam;
    protected boolean updateData = false;
    
    public RayCompare(String eventNumber, Map<String, Object> funcParam) {
        this.eventNumber = eventNumber;
        this.funcParam = funcParam;
    }
    
    /**
     * 1.exist 2.add 3.delete 4.newest 5.update
     * @param taskDto
     * @return 
     * @throws NotFoundException 
     */
    public Map<String, Object> compare(String userId, String taskId, TaskResourceClient client) throws NotFoundException {
        Map<String, Object> result = new HashMap<String, Object>();
        synchronized(userId) {
            if(this.funcParam != null && !this.funcParam.isEmpty()) {
                for(Map.Entry<String, Object> m : this.funcParam.entrySet()) {
                    if(m.getValue() instanceof List) {
                        List<?> list = (List<?>) m.getValue();
                        client.sendTaskLog(taskId, "ID:" + taskId + "的任务-" + m.getKey() + " 从SAP获得" + (list != null ? list.size() : 0) + " 数据.");
                        StructureDao dud = new StructureDao(this.eventNumber + "_" + m.getKey());
                        for(Object obc : list) {
                        	if(dud.select(this.setMISC_STATUS(obc, "4"))) {
                                dud.updateToExist(this.setMISC_STATUS(obc, "1"));
                            } else {
                                dud.saveEntityToSingle(this.setMISC_STATUS(obc, "2"));
                            }
                        }
                        dud.updateToDelete(userId);
                        List<?> lt = dud.selectToAddAndDelete(userId);
                        int size = lt != null ? lt.size() : 0;
                        if(size > 0) {
                        	this.updateData = true;
                        }
                        client.sendTaskLog(taskId, "ID:" + taskId + "的任务-" + m.getKey() + " Compare获得" + size + " 数据.");
                        result.put(m.getKey(), lt);
                        dud.deleteToDelete(userId);
                        dud.updateToNewest(userId);
                    } else {
                        result.put(m.getKey(), m.getValue());
                    }
                }
            } else {
                result = this.funcParam;
            }
        }
        return result;
    }
    
    private Object setMISC_STATUS(Object obj, String miscStatus) {
    	try {    
    		Field[] fields = obj.getClass().getDeclaredFields();
	        for(Field field : fields) {
	            field.setAccessible(true);
	            if("MISC_STATUS".equals(field.getName())) {
					field.set(obj, miscStatus);
	            }
	        }
        } catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return obj;
    }
    
    public boolean isUpdateData() {
    	return this.updateData;
    }
}
