/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.dm.agent.jco;

import com.sap.data.db.util.NotFoundException;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoParameterField;
import com.sap.conn.jco.JCoParameterFieldIterator;
import com.sap.conn.jco.JCoParameterList;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author C5178395
 */
public class JCoUtil extends JCoModel{

    public JCoUtil(String jCoFunction, String destinationName) throws JCoException, NotFoundException {
        super(jCoFunction, destinationName);
    }
    
    public Map<String, Object> getExport(Map<String, String> input) throws JCoException, NotFoundException {
        this.setImportParameters(input);
        this.run();
        Map<String, Object> export = new HashMap<String, Object>();
        export.putAll(this.getExportParameterList());
        export.putAll(this.getTableParameterList());
        export.putAll(this.getExceptionList());
        return export;
    }
    
    protected Map<String, Object> getExportParameterList() throws NotFoundException {
        Map<String, Object> parameters = new HashMap<String, Object>();
        final JCoParameterList parameterList = this.jCoFunction.getExportParameterList();
        if(parameterList != null) {
            final JCoParameterFieldIterator jpfi = parameterList.getParameterFieldIterator();
            while(jpfi.hasNextField()) {
                final JCoParameterField jpf = jpfi.nextParameterField();
                final String parameterName = jpf.getName();
                if(jpf.isStructure()) {
                    parameters.put(parameterName, this.getExportParameter(parameterName, jpf.getStructure()));
                } else if(jpf.isTable()) {
                    parameters.put(parameterName, this.getTableParameter(parameterName, jpf.getTable()));
                } else {
                    parameters.put(parameterName, jpf.getString());
                }
            }
        }
        return parameters;
    }
    
    protected Map<String, Object> getTableParameterList() throws NotFoundException {
        Map<String, Object> parameters = new HashMap<String, Object>();
        final JCoParameterList parameterList = this.jCoFunction.getTableParameterList();
        if(parameterList != null) {
            final JCoParameterFieldIterator jpfi = parameterList.getParameterFieldIterator();
            while(jpfi.hasNextField()) {
                final JCoParameterField jpf = jpfi.nextParameterField();
                final String parameterName = jpf.getName();
                if(jpf.isTable()) {
                    parameters.put(parameterName, this.getTableParameter(parameterName, jpf.getTable()));
                } else if(jpf.isStructure()) {
                    parameters.put(parameterName, this.getExportParameter(parameterName, jpf.getStructure()));
                } else {
                    parameters.put(parameterName, jpf.getString());
                }
            }
        }
        return parameters;
    }
    
}
