package com.sap.data.db.pojo;

import java.io.Serializable;
import java.util.Date;

public class FieldConvertPojo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String FIELDNAME;
	private String DM_FIELDNAME;
	private String DM_CHANGED_BY;
	private Date DM_LAST_CHANGE_TIME;
	private String DM_COMMENTS;
	
	public FieldConvertPojo() {
		
	}
	
	public FieldConvertPojo(String FIELDNAME) {
		this.FIELDNAME = FIELDNAME;
	}
	
	public FieldConvertPojo(String FIELDNAME, String DM_FIELDNAME, String DM_CHANGED_BY, Date DM_LAST_CHANGE_TIME, String DM_COMMENTS) {
		this.FIELDNAME = FIELDNAME;
		this.DM_FIELDNAME = DM_FIELDNAME;
		this.DM_CHANGED_BY = DM_CHANGED_BY;
		this.DM_LAST_CHANGE_TIME = DM_LAST_CHANGE_TIME;
		this.DM_COMMENTS = DM_COMMENTS;
	}

	public String getFIELDNAME() {
		return FIELDNAME;
	}

	public void setFIELDNAME(String fIELDNAME) {
		FIELDNAME = fIELDNAME;
	}

	public String getDM_FIELDNAME() {
		return DM_FIELDNAME;
	}

	public void setDM_FIELDNAME(String dM_FIELDNAME) {
		DM_FIELDNAME = dM_FIELDNAME;
	}

	public String getDM_CHANGED_BY() {
		return DM_CHANGED_BY;
	}

	public void setDM_CHANGED_BY(String dM_CHANGED_BY) {
		DM_CHANGED_BY = dM_CHANGED_BY;
	}

	public Date getDM_LAST_CHANGE_TIME() {
		return DM_LAST_CHANGE_TIME;
	}

	public void setDM_LAST_CHANGE_TIME(Date dM_LAST_CHANGE_TIME) {
		DM_LAST_CHANGE_TIME = dM_LAST_CHANGE_TIME;
	}

	public String getDM_COMMENTS() {
		return DM_COMMENTS;
	}

	public void setDM_COMMENTS(String dM_COMMENTS) {
		DM_COMMENTS = dM_COMMENTS;
	}
	
	
}
