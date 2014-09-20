/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.data.db.util;

/**
 *
 * @author C5178395
 */
public class FieldUtil {

    private String eventNumber;
    private String fieldType;
    private String fieldName;
    private String fieldLeng;
    private String fieldValue;
        
    public FieldUtil() {
        
    }
    
    public FieldUtil(String eventNumber) {
    	this.eventNumber = eventNumber;
    }
    
    public FieldUtil(String fieldType, String fieldName) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
    }
    
    public FieldUtil(String fieldType, String fieldName, String fieldLeng, String fieldValue) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.fieldLeng = fieldLeng;
        this.fieldValue = fieldValue;
    }
    public FieldUtil(String eventNumber, String fieldType, String fieldName, String fieldLeng, String fieldValue) {
    	this.eventNumber = eventNumber;
    	this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.fieldLeng = fieldLeng;
        this.fieldValue = fieldValue;
    }

	public String getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(String eventNumber) {
		this.eventNumber = eventNumber;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldLeng() {
		return fieldLeng;
	}

	public void setFieldLeng(String fieldLeng) {
		this.fieldLeng = fieldLeng;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

}
