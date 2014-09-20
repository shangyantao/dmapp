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
public class BapiDD03LDMPojo implements Serializable {
        
	private static final long serialVersionUID = 1L;
	private BapiDD03LId id;
    private String DM_KEYFLAG;
    private String DM_USED_FLAG;
    private String DM_EXCLUDE_FLAG;
    private String DM_FB_FLAG;
    private String DM_SEQ_NO;
    private String DM_CHANGED_BY;
    private Date DM_LAST_CHANGE_TIME;
    private String DM_COMMENTS;
    
    public BapiDD03LDMPojo() {
        
    }
    
    public BapiDD03LDMPojo(BapiDD03LId id) {
        this.id = id;
    }
    
    public BapiDD03LDMPojo(BapiDD03LId id, String DM_KEYFLAG, String DM_USED_FLAG, String DM_EXCLUDE_FLAG, 
		String DM_FB_FLAG, String DM_SEQ_NO, String DM_CHANGED_BY, Date DM_LAST_CHANGE_TIME, String DM_COMMENTS) {
        this.id = id;
        this.DM_KEYFLAG = DM_KEYFLAG;
        this.DM_USED_FLAG = DM_USED_FLAG;
        this.DM_EXCLUDE_FLAG = DM_EXCLUDE_FLAG;
        this.DM_FB_FLAG = DM_FB_FLAG;
        this.DM_SEQ_NO = DM_SEQ_NO;
        this.DM_CHANGED_BY = DM_CHANGED_BY;
        this.DM_LAST_CHANGE_TIME = DM_LAST_CHANGE_TIME;
        this.DM_COMMENTS = DM_COMMENTS;
    }

    public BapiDD03LId getId() {
        return id;
    }

    public void setId(BapiDD03LId id) {
        this.id = id;
    }

    public String getDM_KEYFLAG() {
        return DM_KEYFLAG;
    }

    public void setDM_KEYFLAG(String DM_KEYFLAG) {
        this.DM_KEYFLAG = DM_KEYFLAG;
    }

    public String getDM_USED_FLAG() {
        return DM_USED_FLAG;
    }

    public void setDM_USED_FLAG(String DM_USED_FLAG) {
        this.DM_USED_FLAG = DM_USED_FLAG;
    }

    public String getDM_EXCLUDE_FLAG() {
        return DM_EXCLUDE_FLAG;
    }

    public void setDM_EXCLUDE_FLAG(String DM_EXCLUDE_FLAG) {
        this.DM_EXCLUDE_FLAG = DM_EXCLUDE_FLAG;
    }

    public String getDM_FB_FLAG() {
		return DM_FB_FLAG;
	}

	public void setDM_FB_FLAG(String dM_FB_FLAG) {
		DM_FB_FLAG = dM_FB_FLAG;
	}

	public String getDM_SEQ_NO() {
        return DM_SEQ_NO;
    }

    public void setDM_SEQ_NO(String DM_SEQ_NO) {
        this.DM_SEQ_NO = DM_SEQ_NO;
    }

    public String getDM_CHANGED_BY() {
        return DM_CHANGED_BY;
    }

    public void setDM_CHANGED_BY(String DM_CHANGED_BY) {
        this.DM_CHANGED_BY = DM_CHANGED_BY;
    }

    public Date getDM_LAST_CHANGE_TIME() {
        return DM_LAST_CHANGE_TIME;
    }

    public void setDM_LAST_CHANGE_TIME(Date DM_LAST_CHANGE_TIME) {
        this.DM_LAST_CHANGE_TIME = DM_LAST_CHANGE_TIME;
    }

    public String getDM_COMMENTS() {
        return DM_COMMENTS;
    }

    public void setDM_COMMENTS(String DM_COMMENTS) {
        this.DM_COMMENTS = DM_COMMENTS;
    }

    
}
