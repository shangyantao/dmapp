/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.data.db.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author C5178395
 */
public class ThreadLocalUtil {
//    protected String JCO_USER;
//    protected String INPUT_PARAMETER;
//    public String TASK_ID;
//    public String RUN_ID;
//    public String MISC_STATUS;
//    public String LAST_CHANGE_TIME;
    
    protected static final ThreadLocal<Vector<FieldUtil>> threadLocal = new ThreadLocal<Vector<FieldUtil>>();
    
    public static String getEventNumber() {
    	Vector<FieldUtil> fields = threadLocal.get();
        if(fields == null || fields.isEmpty()) {
            return null;
        } else {
            return fields.get(0).getEventNumber();
        }
    }
    
    public static Vector<FieldUtil> getFieldType() {
        return threadLocal.get();
    }
    
    public static void setTaskDTO(String eventNumber, String jcoUser, String inputParas, String taskId, String runId) {
        String lastChangeTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Vector<FieldUtil> fields = new Vector<FieldUtil>();
        fields.add(new FieldUtil(eventNumber, "key", "JCO_USER", "20", jcoUser));
        fields.add(new FieldUtil(eventNumber, "key", "INPUT_PARAMETER", "255", inputParas));
        fields.add(new FieldUtil(eventNumber, "field", "TASK_ID", "20", taskId));
        fields.add(new FieldUtil(eventNumber, "field", "RUN_ID", "20", runId));
        fields.add(new FieldUtil(eventNumber, "field", "MISC_STATUS", "20", "4"));
        fields.add(new FieldUtil(eventNumber, "field", "LAST_CHANGE_TIME", "20", lastChangeTime));
        threadLocal.set(fields);
    }
    
}
