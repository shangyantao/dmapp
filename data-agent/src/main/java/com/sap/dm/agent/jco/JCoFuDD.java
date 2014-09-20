/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.dm.agent.jco;

import com.sap.data.db.util.NotFoundException;
import com.sap.data.db.dao.BapiDD03LDao;
import com.sap.data.db.pojo.BapiDD03LId;
import com.sap.data.db.pojo.BapiDD03LPojo;
import com.sap.data.db.pojo.BapiDD04TId;
import com.sap.data.db.pojo.BapiDD04TPojo;
import com.sap.data.db.pojo.BapiFUPARAREFId;
import com.sap.data.db.pojo.BapiFUPARAREFPojo;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoTable;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author C5178395
 */
public class JCoFuDD extends JCoModel {
    
    protected String queryTable = "QUERY_TABLE";
    protected String delimiter =  "DELIMITER";
    protected String queryOptions = "OPTIONS";
    protected String queryFields = "FIELDS";
    protected String queryData = "DATA";
    protected String queryFieldName = "FIELDNAME";
    protected String queryText = "TEXT";
    protected String queryFuncName = "FUNCNAME";
    protected String queryTabName = "TABNAME";
    protected String queryRollName = "ROLLNAME";
    protected String queryDDLanaguage = "DDLANGUAGE";
    protected String queryField = "WA";
    protected String querySlash = "\\";
    protected String queryUnicode = "UTF-8";
    protected Map<Integer, String> fieldNames = new HashMap<Integer, String>();
    
    public JCoFuDD(String jCoFunctionName, String destinationName) throws JCoException, NotFoundException {
        super(jCoFunctionName, destinationName);
    }
    
    public List<?> getFUPARAREF(String jCoFunctionName) throws JCoException, UnsupportedEncodingException, InstantiationException, IllegalAccessException, NotFoundException {
        Map<String, List<Map<String, String>>> filter = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> fieldFilter = this.prepareFilterField("FUPARAREF");
        filter.put(this.queryFields, fieldFilter);
        List<Map<String, String>> optionsFilter = this.prepareFilterOptions1(jCoFunctionName);
        filter.put(this.queryOptions, optionsFilter);
        return this.getTable(BapiFUPARAREFPojo.class, filter);
    }
    
    public List<?> getDD03L(List<?> list) throws JCoException, UnsupportedEncodingException, InstantiationException, IllegalAccessException, NotFoundException {
        this.flushOptionFields();
        Map<String, List<Map<String, String>>> filter = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> fieldFilter = this.prepareFilterField("DD03L");
        filter.put(this.queryFields, fieldFilter);
        List<Map<String, String>> optionsFilter = this.prepareFilterOptions2(list);
        filter.put(this.queryOptions, optionsFilter);
        return this.getTable(BapiDD03LPojo.class, filter);
    }
    
    public List<?> getDD04T(List<?> list) throws JCoException, UnsupportedEncodingException, InstantiationException, IllegalAccessException, NotFoundException {
        this.flushOptionFields();
        Map<String, List<Map<String, String>>> filter = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> fieldFilter = this.prepareFilterField("DD04T");
        filter.put(this.queryFields, fieldFilter);
        List<Map<String, String>> optionsFilter = this.prepareFilterOptions3(list);
        filter.put(this.queryOptions, optionsFilter);
        return this.getTable(BapiDD04TPojo.class, filter);
    }
    
    private List<?> getTable (Class<?> obj, Map<String, List<Map<String, String>>> filter) throws JCoException {
        List<Object> list = new ArrayList<Object>();
        if(obj != null) {
            String tableName = this.getTableName(obj);
            Map<String, String> input = new HashMap<String, String>();
            input.put(this.queryTable, tableName);
            input.put(this.delimiter, this.queryDelimiter);
            this.setImportParameters(input);
            this.setTableFilterOption(tableName, filter);
            this.run();
            JCoTable jt = this.jCoFunction.getTableParameterList().getTable(this.queryData);
            for(int i = 0; i < jt.getNumRows(); i++) {
                jt.setRow(i);
                Object objnes = this.initialClass(obj, jt.getString(this.queryField));
                if(objnes != null) {
                    list.add(objnes);
                }
            }
        }
        return list;
    }
    
    private String getTableName(Class<?> obj) {
    	if(obj == BapiFUPARAREFPojo.class) {
    		return "FUPARAREF";
    	} 
    	if(obj == BapiDD03LPojo.class) {
    		return "DD03L";
    	}
    	if(obj == BapiDD04TPojo.class) {
    		return "DD04T";
    	}
    	return null;
    }
    
    private void flushOptionFields() {
        JCoTable data = this.jCoFunction.getTableParameterList().getTable(this.queryData);
        data.deleteAllRows();
        JCoTable options = this.jCoFunction.getTableParameterList().getTable(this.queryOptions);
        options.deleteAllRows();
        JCoTable fields = this.jCoFunction.getTableParameterList().getTable(this.queryFields);
        fields.deleteAllRows();
    }
    
    private List<Map<String, String>> prepareFilterField(String tableName) throws NotFoundException {
        List<Map<String, String>> fieldFilter = new ArrayList<Map<String, String>>();
        List<BapiDD03LPojo> bapiFields = new BapiDD03LDao().selectTabFields(tableName);
        int j = 0;
        for(int i = 0; bapiFields != null && i < bapiFields.size(); i++) {
            BapiDD03LPojo bapiField = bapiFields.get(i);
            if(bapiField.getId().getFIELDNAME().startsWith(".")) {
                continue;
            }
            if(!"X".equalsIgnoreCase(null != bapiField.getDd03ldm() ? bapiField.getDd03ldm().getDM_EXCLUDE_FLAG() : "")) {
                Map<String, String> field = new HashMap<String, String>();
                field.put(this.queryFieldName, bapiField.getId().getFIELDNAME().trim());
                this.fieldNames.put(j, bapiField.getId().getFIELDNAME().trim());
                fieldFilter.add(field);
                j++;
            }
        }
        return fieldFilter;
    } 
    
    private List<Map<String, String>> prepareFilterOptions1(String jCoFunctionName) {
        List<Map<String, String>> optionsFilter = new ArrayList<Map<String, String>>();
        Map<String, String> option = new HashMap<String, String>();
        option.put(this.queryText, this.queryFuncName + " IN ('" + jCoFunctionName + "')");
        optionsFilter.add(option);
        return optionsFilter;
    }
    
    private List<Map<String, String>> prepareFilterOptions2(List<?> list) {
        List<Map<String, String>> optionsFilter = new ArrayList<Map<String, String>>();
        if(null != list && !list.isEmpty()) {
            for(int i = 0; i < list.size(); i++) {
            	Object obj = list.get(i);
            	if(obj instanceof BapiFUPARAREFPojo) {
            		BapiFUPARAREFPojo pojo = (BapiFUPARAREFPojo) obj;
	                Map<String, String> option = new HashMap<String, String>();
	                String structure = pojo.getSTRUCTURE();
	                if(i == 0) {
	                	if(list.size() == 1) {
	                		option.put(this.queryText, this.queryTabName + " IN ('" + structure + "')");
	                	} else {
	                		option.put(this.queryText, this.queryTabName + " IN ('" + structure + "'");
	                	}
	                } else if(i == (list.size() - 1)) {
	                    option.put(this.queryText, ",'" + structure + "')");
	                } else {
	                    option.put(this.queryText, ",'" + structure + "'");
	                }
	                optionsFilter.add(option);
            	}
            }
        } else {
            Map<String, String> option = new HashMap<String, String>();
            option.put(this.queryText, this.queryTabName + " IN ('')");
            optionsFilter.add(option);
        }
        return optionsFilter;
    }
    
    private List<Map<String, String>> prepareFilterOptions3(List<?> list) {
        List<Map<String, String>> optionsFilter = new ArrayList<Map<String, String>>();
        if(null != list && !list.isEmpty()) {
            for(int i = 0; i < list.size(); i++) {
            	Object obj = list.get(i);
            	if(obj instanceof BapiDD03LPojo) {
            		BapiDD03LPojo pojo = (BapiDD03LPojo) obj;
	                Map<String, String> option = new HashMap<String, String>();
	                String rollName = pojo.getROLLNAME();
	                if(i == 0) {
	                	if(list.size() == 1) {
	                		option.put(this.queryText, this.queryRollName + " IN ('" + rollName + "')");
	                	} else {
	                		option.put(this.queryText, this.queryRollName + " IN ('" + rollName + "'");
	                	}
	                } else if(i == (list.size() - 1)) {
	                    option.put(this.queryText, ",'" + rollName + "')");
	                } else {
	                    option.put(this.queryText, ",'" + rollName + "'");
	                }
	                optionsFilter.add(option);
            	}
            }

            Map<String, String> option = new HashMap<String, String>();
            option.put(this.queryText, " and " + this.queryDDLanaguage + " IN ('E')");
            optionsFilter.add(option);
        } else {
            Map<String, String> option = new HashMap<String, String>();
            option.put(this.queryText, this.queryRollName + " IN ('')");
            optionsFilter.add(option);
        }
        return optionsFilter;
    }
    
    private Object initialClass(Class<?> obj, String line) {
        try {
            Object objnes = obj.newInstance();
            Field[] fields = obj.getDeclaredFields();
            Map<String, String> fieldvs = this.convertToMap(line);
            for(Field field : fields) {
                field.setAccessible(true);
                String fieldv = this.convertToString(fieldvs.get(field.getName()));
                if(field.getType() == Integer.class) {
                    if(this.isNumeric(fieldv)) {
                        field.set(objnes, Integer.valueOf(fieldv));
                    }
                } else if(field.getType() == String.class){
                    field.set(objnes, fieldv);
                } else if(field.getType() == BapiFUPARAREFId.class){
                    field.set(objnes, this.initialIdClass(BapiFUPARAREFId.class, fieldvs));
                } else if(field.getType() == BapiDD03LId.class){
                    field.set(objnes, this.initialIdClass(BapiDD03LId.class, fieldvs));
                } else if(field.getType() == BapiDD04TId.class){
                    field.set(objnes, this.initialIdClass(BapiDD04TId.class, fieldvs));
                }
            }
            return objnes;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(JCoFuDD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private Object initialIdClass(Class<?> obj, Map<String, String> fieldvs) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
        Object idnes = obj.newInstance();
        Field[] fields = obj.getDeclaredFields();
        for(Field field : fields) {
            field.setAccessible(true);
            String fieldv = this.convertToString(fieldvs.get(field.getName()));
            if(field.getType() == Integer.class) {
                if(this.isNumeric(fieldv)) {
                    field.set(idnes, Integer.valueOf(fieldv));
                }
            } else if(field.getType() == String.class) {
                field.set(idnes, fieldv);
            }
        }
        return idnes;
    }
    
    private Map<String, String> convertToMap(String line) {
        try {
            Map<String, String> fieldvs = new HashMap<String, String>();
            String wa = new String(line.getBytes(), "UTF-8");
            String[] value = wa.split("\\" + this.queryDelimiter);
            for(int j = 0; j < value.length; j++) {
                fieldvs.put(this.fieldNames.get(j), value[j].trim());
            }
            return fieldvs;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JCoRdTB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
