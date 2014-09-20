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
public class BapiDD04TId implements Serializable{
    
	private static final long serialVersionUID = 1L;
    private String ROLLNAME;
    private String DDLANGUAGE;
    private String AS4LOCAL;
    private String AS4VERS;
    
    public BapiDD04TId() {
        
    }
    
    public BapiDD04TId(String ROLLNAME, String DDLANGUAGE, String AS4LOCAL, String AS4VERS) {
        this.ROLLNAME = ROLLNAME;
        this.DDLANGUAGE = DDLANGUAGE;
        this.AS4LOCAL = AS4LOCAL;
        this.AS4VERS = AS4VERS;
    }
    
    public String getROLLNAME() {
        return ROLLNAME;
    }

    public void setROLLNAME(String ROLLNAME) {
        this.ROLLNAME = ROLLNAME;
    }

    public String getDDLANGUAGE() {
        return DDLANGUAGE;
    }

    public void setDDLANGUAGE(String DDLANGUAGE) {
        this.DDLANGUAGE = DDLANGUAGE;
    }

    public String getAS4LOCAL() {
        return AS4LOCAL;
    }

    public void setAS4LOCAL(String AS4LOCAL) {
        this.AS4LOCAL = AS4LOCAL;
    }

    public String getAS4VERS() {
        return AS4VERS;
    }

    public void setAS4VERS(String AS4VERS) {
        this.AS4VERS = AS4VERS;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.getROLLNAME() == null ? 0 : this.getROLLNAME().hashCode());
        hash = 37 * hash + (this.getDDLANGUAGE() == null ? 0 : this.getDDLANGUAGE().hashCode());
        hash = 37 * hash + (this.getAS4LOCAL() == null ? 0 : this.getAS4LOCAL().hashCode());
        hash = 37 * hash + (this.getAS4VERS() == null ? 0 : this.getAS4VERS().hashCode());
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
        if (!(obj instanceof BapiDD04TId)) {
            return false;
        }
        BapiDD04TId other = (BapiDD04TId) obj;
        
        return (this.getROLLNAME() != null && other.getROLLNAME() != null && this.getROLLNAME().equals(other.getROLLNAME())) 
            && (this.getDDLANGUAGE() != null && other.getDDLANGUAGE() != null && this.getDDLANGUAGE().equals(other.getDDLANGUAGE())) 
            && (this.getAS4LOCAL() != null && other.getAS4LOCAL() != null && this.getAS4LOCAL().equals(other.getAS4LOCAL())) 
           && (this.getAS4VERS() != null && other.getAS4VERS() != null && this.getAS4VERS().equals(other.getAS4VERS())) ;
    }
}
