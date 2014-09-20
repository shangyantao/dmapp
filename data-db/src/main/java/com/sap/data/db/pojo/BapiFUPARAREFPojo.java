/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.data.db.pojo;

import java.io.Serializable;

/**
 *
 * @author C5178395
 */
public class BapiFUPARAREFPojo implements Serializable {
    
	private static final long serialVersionUID = 1L;
    private BapiFUPARAREFId id;
    private String STRUCTURE;
    private String DEFAULTVAL;
    private String REFERENCE;
    private Integer PPOSITION;
    private String OPTIONAL;
    private String TYPE;
    private String CLASS;
    private String REF_CLASS;
    private String LINE_OF;
    private String TABLE_OF;
    private String RESFLAG1;
    private String RESFLAG2;
    private String RESFLAG3;
    private String RESFLAG4;
    private String RESFLAG5;
    
    public BapiFUPARAREFPojo() {
        
    }
    
    public BapiFUPARAREFPojo(BapiFUPARAREFId id) {
        this.id = id;
    }
    
    public BapiFUPARAREFPojo(BapiFUPARAREFId id, String STRUCTURE, String DEFAULTVAL, 
            String REFERENCE, Integer PPOSITION, String OPTIONAL, String TYPE, String CLASS, String REF_CLASS, String LINE_OF, 
            String TABLE_OF, String RESFLAG1, String RESFLAG2, String RESFLAG3, String RESFLAG4, String RESFLAG5) {
        this.id = id;
        this.STRUCTURE = STRUCTURE;
        this.DEFAULTVAL = DEFAULTVAL;
        this.REFERENCE = REFERENCE;
        this.PPOSITION = PPOSITION;
        this.OPTIONAL = OPTIONAL;
        this.TYPE = TYPE;
        this.CLASS = CLASS;
        this.REF_CLASS = REF_CLASS;
        this.LINE_OF = LINE_OF;
        this.TABLE_OF = TABLE_OF;
        this.RESFLAG1 = RESFLAG1;
        this.RESFLAG2 = RESFLAG2;
        this.RESFLAG3 = RESFLAG3;
        this.RESFLAG4 = RESFLAG4;
        this.RESFLAG5 = RESFLAG5;
    }

    public BapiFUPARAREFId getId() {
        return id;
    }

    public void setId(BapiFUPARAREFId id) {
        this.id = id;
    }
    
    public String getSTRUCTURE() {
        return STRUCTURE;
    }

    public void setSTRUCTURE(String STRUCTURE) {
        this.STRUCTURE = STRUCTURE;
    }

    public String getDEFAULTVAL() {
        return DEFAULTVAL;
    }

    public void setDEFAULTVAL(String DEFAULTVAL) {
        this.DEFAULTVAL = DEFAULTVAL;
    }

    public String getREFERENCE() {
        return REFERENCE;
    }

    public void setREFERENCE(String REFERENCE) {
        this.REFERENCE = REFERENCE;
    }

    public Integer getPPOSITION() {
        return PPOSITION;
    }

    public void setPPOSITION(Integer PPOSITION) {
        this.PPOSITION = PPOSITION;
    }

    public String getOPTIONAL() {
        return OPTIONAL;
    }

    public void setOPTIONAL(String OPTIONAL) {
        this.OPTIONAL = OPTIONAL;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getCLASS() {
        return CLASS;
    }

    public void setCLASS(String CLASS) {
        this.CLASS = CLASS;
    }

    public String getREF_CLASS() {
        return REF_CLASS;
    }

    public void setREF_CLASS(String REF_CLASS) {
        this.REF_CLASS = REF_CLASS;
    }

    public String getLINE_OF() {
        return LINE_OF;
    }

    public void setLINE_OF(String LINE_OF) {
        this.LINE_OF = LINE_OF;
    }

    public String getTABLE_OF() {
        return TABLE_OF;
    }

    public void setTABLE_OF(String TABLE_OF) {
        this.TABLE_OF = TABLE_OF;
    }

    public String getRESFLAG1() {
        return RESFLAG1;
    }

    public void setRESFLAG1(String RESFLAG1) {
        this.RESFLAG1 = RESFLAG1;
    }

    public String getRESFLAG2() {
        return RESFLAG2;
    }

    public void setRESFLAG2(String RESFLAG2) {
        this.RESFLAG2 = RESFLAG2;
    }

    public String getRESFLAG3() {
        return RESFLAG3;
    }

    public void setRESFLAG3(String RESFLAG3) {
        this.RESFLAG3 = RESFLAG3;
    }

    public String getRESFLAG4() {
        return RESFLAG4;
    }

    public void setRESFLAG4(String RESFLAG4) {
        this.RESFLAG4 = RESFLAG4;
    }

    public String getRESFLAG5() {
        return RESFLAG5;
    }

    public void setRESFLAG5(String RESFLAG5) {
        this.RESFLAG5 = RESFLAG5;
    }
    
}

