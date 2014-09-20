/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.data.db.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author C5178395
 */
public class EventPojo implements Serializable {
    
	private static final long serialVersionUID = 1L;
    private Integer EVENT_ID;
    private String EVENT_NUMBER;
    private String EVENT_NAME;
    private String FUNCNAME;
    private String EVENT_DES;
    private String DELETIONFLAG;
    private String CHANGED_BY;
    private Date LAST_CHANGE_TIME;
    private String COMMENTS;
    
    public EventPojo() {
    }
    
    public EventPojo(Integer EVENT_ID) {
        this.EVENT_ID = EVENT_ID;
    }
    

    public EventPojo(Integer eVENT_ID, String eVENT_NUMBER, String eVENT_NAME,
			String fUNCNAME, String eVENT_DES, String dELETIONFLAG,
			String cHANGED_BY, Date lAST_CHANGE_TIME, String cOMMENTS) {
		super();
		EVENT_ID = eVENT_ID;
		EVENT_NUMBER = eVENT_NUMBER;
		EVENT_NAME = eVENT_NAME;
		FUNCNAME = fUNCNAME;
		EVENT_DES = eVENT_DES;
		DELETIONFLAG = dELETIONFLAG;
		CHANGED_BY = cHANGED_BY;
		LAST_CHANGE_TIME = lAST_CHANGE_TIME;
		COMMENTS = cOMMENTS;
	}

	public String getEVENT_DES() {
		return EVENT_DES;
	}

	public void setEVENT_DES(String eVENT_DES) {
		EVENT_DES = eVENT_DES;
	}

	public String getDELETIONFLAG() {
		return DELETIONFLAG;
	}

	public void setDELETIONFLAG(String dELETIONFLAG) {
		DELETIONFLAG = dELETIONFLAG;
	}

	public String getCHANGED_BY() {
		return CHANGED_BY;
	}

	public void setCHANGED_BY(String cHANGED_BY) {
		CHANGED_BY = cHANGED_BY;
	}

	public Date getLAST_CHANGE_TIME() {
		return LAST_CHANGE_TIME;
	}

	public void setLAST_CHANGE_TIME(Date lAST_CHANGE_TIME) {
		LAST_CHANGE_TIME = lAST_CHANGE_TIME;
	}

	public String getCOMMENTS() {
		return COMMENTS;
	}

	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
	}

	public Integer getEVENT_ID() {
        return EVENT_ID;
    }

    public void setEVENT_ID(Integer EVENT_ID) {
        this.EVENT_ID = EVENT_ID;
    }

    public String getEVENT_NUMBER() {
        return EVENT_NUMBER;
    }

    public void setEVENT_NUMBER(String EVENT_NUMBER) {
        this.EVENT_NUMBER = EVENT_NUMBER;
    }

    public String getEVENT_NAME() {
        return EVENT_NAME;
    }

    public void setEVENT_NAME(String EVENT_NAME) {
        this.EVENT_NAME = EVENT_NAME;
    }

    public String getFUNCNAME() {
        return FUNCNAME;
    }

    public void setFUNCNAME(String FUNCNAME) {
        this.FUNCNAME = FUNCNAME;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.getEVENT_ID();
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EventPojo)) {
            return false;
        }
        EventPojo other = (EventPojo) obj;
        
        return (this.getEVENT_ID() == other.getEVENT_ID());
    }
    
}

