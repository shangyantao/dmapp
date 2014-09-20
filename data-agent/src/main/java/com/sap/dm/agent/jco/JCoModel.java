/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.dm.agent.jco;

import com.sap.data.db.util.ClassUtil;
import com.sap.data.db.util.FieldUtil;
import com.sap.data.db.util.NotFoundException;
import com.sap.data.db.util.ThreadLocalUtil;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterField;
import com.sap.conn.jco.JCoParameterFieldIterator;
import com.sap.conn.jco.JCoRecord;
import com.sap.conn.jco.JCoRecordFieldIterator;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author C5178395
 */
public class JCoModel {
    
    protected JCoPool jCoPool = new JCoPool();
    protected JCoFunction jCoFunction = null;
    protected JCoDestination jCoDestination = null;
    protected Map<String, String> inputParameters = new HashMap<String, String>();
    protected String queryDelimiter = "^";
    private final Object lock = new Object();
    protected static final Logger log = Logger.getLogger(JCoModel.class.getName());
    
    public JCoModel(String jCoFunctionName, String destinationName) throws JCoException, NotFoundException {
        this.jCoDestination = this.jCoPool.getJCoDestination(destinationName);
        this.jCoFunction = this.jCoPool.getJCoFunction(this.jCoDestination, jCoFunctionName);
    }
    
    public void setImportParameters(Map<String, String> inputParameters) throws JCoException {
        synchronized (lock) {
            if(this.jCoFunction != null && inputParameters != null && !inputParameters.isEmpty()) {
                System.out.println("---Input Parameter-----------------");
                final JCoParameterFieldIterator jpfi = this.jCoFunction.getImportParameterList().getParameterFieldIterator();
                while(jpfi.hasNextField()) {
                    final JCoParameterField jpf = jpfi.nextParameterField();
                    final String paramName = jpf.getName();
                    String paramValue = inputParameters.get(paramName);
                    if(null != paramValue) {
                         paramValue = paramValue.replaceAll("'", "");
                        if(8 == jpf.getType()) {
                            this.setImportParameter(paramName, Integer.valueOf(paramValue));
                        } else {
                            this.setImportParameter(paramName, paramValue);
                        }
                    }
                }
                System.out.println("-----------------------------------");
            }
        }
    }
    
    protected void setImportParameter(String name, Object value) throws JCoException {
        if(this.jCoFunction != null) {
            this.jCoFunction.getImportParameterList().setValue(name, value);
            this.inputParameters.put(name, value.toString());
            System.out.println("| " + name + ":" + value);
        }
    }
    
    public void setTableFilterOption(String tableName, Map<String, List<Map<String, String>>> filter) {
        synchronized (lock) {
            if(this.jCoFunction != null && filter != null && !filter.isEmpty()) {
                System.out.println("---Filter table(" + tableName + ") option--------");
                final JCoParameterFieldIterator jpfi = this.jCoFunction.getTableParameterList().getParameterFieldIterator();
                while(jpfi.hasNextField()) {
                    final JCoParameterField jpf = jpfi.nextParameterField();
                    List<Map<String, String>> list = filter.get(jpf.getName());
                    if(list != null && !list.isEmpty()) {
                        System.out.print("| ");
                        for(Map<String, String> fields : list) {
                            if(fields != null && !fields.isEmpty()) {
                                this.setFilterOption(jpf.getTable(), fields);
                            }
                        }
                        System.out.println();
                    }
                }
                System.out.println("-----------------------------------");
            }
        }
    }
    
    protected void setFilterOption(JCoTable jCoTable, Map<String, String> fields) {
        jCoTable.appendRow();
        final JCoRecordFieldIterator jCoRecordFieldIterator = jCoTable.getRecordFieldIterator();
        while(jCoRecordFieldIterator.hasNextField()) {
            final JCoField jCoField = jCoRecordFieldIterator.nextField();
            final String fieldValue = fields.get(jCoField.getName());
            if(fieldValue != null) {
                jCoField.setValue(fieldValue.trim());
                System.out.print(fieldValue + " ");
            }
        }
    }
    
    public void run() throws JCoException {
        JCoContext.begin(this.jCoDestination);
        this.jCoFunction.execute(this.jCoDestination);
        JCoContext.end(this.jCoDestination);
    }
    
    protected Map<String, String> getExceptionList() throws JCoException{
        Map<String, String> parameters = new HashMap<String, String>();
        final AbapException[] list = this.jCoFunction.getExceptionList();
        if(list != null) {
            for (final AbapException abapException : list) {
                parameters.put(abapException.getKey(), abapException.getMessage());
            }
        }
        return parameters;
    }
    
    public List<Object> getExportParameter(String parameterName, JCoStructure js) throws NotFoundException {
        List<Object> structures = new ArrayList<Object>();
        Class<?> cls = new ClassUtil(parameterName).load();
        if(cls != null) {
            Object obj = this.initialClass(cls, js, this.getRecordFeild(js));
            if(obj != null) {
                structures.add(obj);
            }
        }
        return structures;
    }
    
    public List<Object> getTableParameter(String tableName, JCoTable jt) throws NotFoundException {
        List<Object> tableData = new ArrayList<Object>();
        Class<?> cls = new ClassUtil(tableName).load();
        if(cls != null) {
            String fieldNames = this.getRecordFeild(jt);
            for(int i = 0; i < jt.getNumRows(); i++) {
                jt.setRow(i);
                Object obj = this.initialClass(cls, jt, fieldNames);
                if(obj != null) {
                    tableData.add(obj);
                }
            }
        }
        return tableData;
    }
    
    protected String getRecordFeild(JCoRecord jr) {
        String fieldNames = this.queryDelimiter;
        JCoFieldIterator jCoFields = jr.getFieldIterator();
        while(jCoFields.hasNextField()) {
            String fieldName = jCoFields.nextField().getName();
            fieldNames += fieldName + this.queryDelimiter;
        }
        return fieldNames;
    }
    
    protected Object initialClass(Class<?> cls, JCoRecord jr, String fieldNames) {
        try {
        	Vector<FieldUtil> fieldUtils = ThreadLocalUtil.getFieldType();
            Object obj = cls.newInstance();
            Field[] fields = cls.getDeclaredFields();
            for(Field field : fields) {
                field.setAccessible(true);
                if(fieldNames.indexOf(field.getName() + this.queryDelimiter) > 0) {
                    field.set(obj, this.convertToString(jr.getString(field.getName())));
                } else if("DId".equals(field.getName())) {
                	field.set(obj, this.initialIdClass(field.getType(), jr, fieldNames, fieldUtils));
                } else {
                	String fieldValue = this.getFieldValue(field.getName(), fieldUtils);
                	if(null != fieldValue) {
                		field.set(obj, fieldValue);
                	}
            	}
            }
            return obj;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(JCoModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private Object initialIdClass(Class<?> cls, JCoRecord jr, String fieldNames, Vector<FieldUtil> fieldUtils) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
        Object obj = cls.newInstance();
        Field[] fields = cls.getDeclaredFields();
        for(Field field : fields) {
            field.setAccessible(true);
            String strng = null;
            if(fieldNames.indexOf(field.getName() + this.queryDelimiter) > 0) {            	
                strng = jr.getString(field.getName());
        	} else {
        		strng = this.getFieldValue(field.getName(), fieldUtils);
        	}
            if(null != strng) {
            	field.set(obj, this.convertToString(strng));
            }
        }
        return obj;
    }
    
    private String getFieldValue(String fieldName, Vector<FieldUtil> fieldUtils) {
    	for(FieldUtil fieldUtil : fieldUtils) {
    		if(fieldName.equals(fieldUtil.getFieldName())) {
    			return fieldUtil.getFieldValue();
    		}
    	}
    	return null;
    }
    
    protected boolean isNumeric(String str){
        if(str == null || str.isEmpty()) {
            return false;
        }
        for(int i = str.length(); --i >= 0; ){
           int chr = str.charAt(i);
           if(chr < 48 || chr > 57){
              return false;
           }
        }
        return true;
    }
    
    protected String convertToString(String strng) {
        if(null != strng) {
            return strng.replaceAll(" ", "_");
        } else {
            return "";
        }
    }
}
