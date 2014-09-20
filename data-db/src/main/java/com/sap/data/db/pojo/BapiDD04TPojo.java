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
public class BapiDD04TPojo implements Serializable {
    
	private static final long serialVersionUID = 1L;
    private BapiDD04TId id;
    private String DDTEXT;
    private String REPTEXT;
    private String SCRTEXT_S;
    private String SCRTEXT_M;
    private String SCRTEXT_L;
    
    public BapiDD04TPojo() {
        
    }
    
    public BapiDD04TPojo(BapiDD04TId id) {
        this.id = id;
    }
    
    public BapiDD04TPojo(BapiDD04TId id, String DDTEXT, String REPTEXT, String SCRTEXT_S, String SCRTEXT_M, String SCRTEXT_L) {
        this.id = id;
        this.DDTEXT = DDTEXT;
        this.REPTEXT = REPTEXT;
        this.SCRTEXT_S = SCRTEXT_S;
        this.SCRTEXT_M = SCRTEXT_M;
        this.SCRTEXT_L = SCRTEXT_L;
    }

    public BapiDD04TId getId() {
        return id;
    }

    public void setId(BapiDD04TId id) {
        this.id = id;
    }

    public String getDDTEXT() {
        return DDTEXT;
    }

    public void setDDTEXT(String DDTEXT) {
        this.DDTEXT = DDTEXT;
    }

    public String getREPTEXT() {
        return REPTEXT;
    }

    public void setREPTEXT(String REPTEXT) {
        this.REPTEXT = REPTEXT;
    }

    public String getSCRTEXT_S() {
        return SCRTEXT_S;
    }

    public void setSCRTEXT_S(String SCRTEXT_S) {
        this.SCRTEXT_S = SCRTEXT_S;
    }

    public String getSCRTEXT_M() {
        return SCRTEXT_M;
    }

    public void setSCRTEXT_M(String SCRTEXT_M) {
        this.SCRTEXT_M = SCRTEXT_M;
    }

    public String getSCRTEXT_L() {
        return SCRTEXT_L;
    }

    public void setSCRTEXT_L(String SCRTEXT_L) {
        this.SCRTEXT_L = SCRTEXT_L;
    }
    
}
