/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.dm.agent.jco;

import com.sap.data.db.util.ClassUtil;
import com.sap.data.db.util.NotFoundException;
import com.sap.data.db.dao.BapiDD03LDao;
import com.sap.data.db.pojo.BapiDD03LPojo;
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
public class JCoRdTB extends JCoModel {
    
    protected String tableName = "";
    protected String queryTable = "QUERY_TABLE";
    protected String delimiter =  "DELIMITER";
    protected String notData = "NO_DATA";
    protected String rowSkips = "ROWSKIPS";
    protected String rowCount = "ROWCOUNT";
    protected String rowMax = "infinity";
    protected String queryOptions = "OPTIONS";
    protected String queryFields = "FIELDS";
    protected String queryFieldName = "FIELDNAME";
    protected String queryText = "TEXT";
    protected String queryData = "DATA";
    protected String queryField = "WA";
    protected Integer queryRowSkips = 0;
    protected Integer queryRowCount = 100000;
    protected Map<Integer, String> fieldNames = new HashMap<Integer, String>();
    
    public JCoRdTB(String jCoFunction, String destinationName) throws JCoException, NotFoundException {
        super(jCoFunction, destinationName);
    }
        
    public List<Object> getTable(String tableName, String[] options) throws JCoException, NotFoundException {
        return this.getTable(tableName, this.queryRowSkips, this.queryRowCount, options);
    }
    
    public List<Object> getTable(String tableName, Integer rowSkips, Integer rowCount, String[] options) throws JCoException, NotFoundException {
        this.tableName = tableName;
        Map<String, String> input = new HashMap<String, String>();
        input.put(this.queryTable, tableName);
        input.put(this.delimiter, this.queryDelimiter);
        input.put(this.rowSkips, rowSkips.toString());
        input.put(this.rowCount, rowCount.toString());
        this.setImportParameters(input);
        this.setTableFilterOption(tableName, this.prepareFilter(options));
        this.run();
        return this.getTableParameter(tableName);
    }
    
    protected List<Object> getTableParameter(String tableName) throws NotFoundException {
        List<Object> tableData = new ArrayList<Object>();
        JCoTable jt = this.jCoFunction.getTableParameterList().getTable(this.queryData);
        Class<?> cls = new ClassUtil(tableName).load();
        if(cls != null) {
            for(int i = 0; i < jt.getNumRows(); i++) {
                jt.setRow(i);
                Object obj = this.initialClass(cls, jt.getString(this.queryField));
                if(obj != null) {
                    tableData.add(obj);
                }
            }
        }
        return tableData;
    }
    
    protected Map<String, List<Map<String, String>>> prepareFilter(String[] options) throws NotFoundException {
    	Map<String, List<Map<String, String>>> filter = new HashMap<String, List<Map<String, String>>>();
    	List<Map<String, String>> fieldsFilter = this.prepareFilterFields();
    	if(!fieldsFilter.isEmpty()) {
            filter.put(this.queryFields, fieldsFilter);
        }
    	if(null != options && options.length > 0) {
    		filter.put(this.queryOptions, this.prepareFilterOptions(options));
    	}
    	return filter;
    }
    
    protected List<Map<String, String>> prepareFilterFields() throws NotFoundException {
        List<Map<String, String>> fieldsFilter = new ArrayList<Map<String, String>>();
        List<BapiDD03LPojo> bapiFields = new BapiDD03LDao().selectTabFields(this.tableName);
        int j = 0;
        for(int i = 0; bapiFields != null && i < bapiFields.size(); i++) {
            BapiDD03LPojo bapiField = bapiFields.get(i);
            if(bapiField.getId().getFIELDNAME().startsWith(".")) {
                continue;
            }
            if(!"X".equalsIgnoreCase(null != bapiField.getDd03ldm() ? bapiField.getDd03ldm().getDM_EXCLUDE_FLAG() : "")) {
                Map<String, String> fieldFilter = new HashMap<String, String>();
                fieldFilter.put(this.queryFieldName, bapiField.getId().getFIELDNAME().trim());
                this.fieldNames.put(j, bapiField.getId().getFIELDNAME().trim());
                fieldsFilter.add(fieldFilter);
                j ++;
            }
        }
        return fieldsFilter;
    }
    
    protected List<Map<String, String>> prepareFilterOptions(String[] options) {
    	List<Map<String, String>> optionsFilter = new ArrayList<Map<String, String>>();
    	for(String option : options) {
    		if(null != option && !option.isEmpty()) {
    			Map<String, String> optionFilter = new HashMap<String, String>();
    			optionFilter.put(this.queryText, option);
    			optionsFilter.add(optionFilter);
    		}
    	}
    	return optionsFilter;
    }
    
    private Object initialClass(Class<?> cls, String line) {
        try {
            Map<String, String> fieldvs = this.convertToMap(line);
            if(fieldvs != null && !fieldvs.isEmpty()) {
                Object obj = cls.newInstance();
                Field[] fields = cls.getDeclaredFields();
                for(Field field : fields) {
                    field.setAccessible(true);
                    if("DId".equals(field.getName())) {
                    	field.set(obj, this.initialIdClass(field.getType(), fieldvs));
                    } else if(field.getType() == String.class){
                    	field.set(obj, this.convertToString(fieldvs.get(field.getName())));
                    }
                }
                return obj;
            } else {
                return null;
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(JCoRdTB.class.getName()).log(Level.SEVERE, null, ex);
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
            Map<String, String> fields = new HashMap<String, String>();
            String wa = new String(line.getBytes(), "UTF-8");
            String[] value = wa.split("\\" + this.queryDelimiter);
            for(int j = 0; j < value.length; j++) {
                fields.put(this.fieldNames.get(j), value[j].trim());
            }
            return fields;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JCoRdTB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
