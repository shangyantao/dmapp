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
public class BapiDD03LId implements Serializable {
    
	private static final long serialVersionUID = 1L;
    private String TABNAME;
    private String FIELDNAME;
    private String AS4LOCAL;
    private String AS4VERS;
    private Integer POSITION;
    
    public BapiDD03LId() {
        
    }
    
    public BapiDD03LId(String TABNAME, String FIELDNAME, String AS4LOCAL, String AS4VERS, Integer POSITION) {
        this.TABNAME = TABNAME;
        this.FIELDNAME = FIELDNAME;
        this.AS4LOCAL = AS4LOCAL;
        this.AS4VERS = AS4VERS;
        this.POSITION = POSITION;
    }
        
    public String getTABNAME() {
        return TABNAME;
    }

    public void setTABNAME(String TABNAME) {
        this.TABNAME = TABNAME;
    }

    public String getFIELDNAME() {
        return FIELDNAME;
    }

    public void setFIELDNAME(String FIELDNAME) {
        this.FIELDNAME = FIELDNAME;
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

    public Integer getPOSITION() {
        return POSITION;
    }

    public void setPOSITION(Integer POSITION) {
        this.POSITION = POSITION;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.getTABNAME() == null ? 0 : this.getTABNAME().hashCode());
        hash = 37 * hash + (this.getFIELDNAME() == null ? 0 : this.getFIELDNAME().hashCode());
        hash = 37 * hash + (this.getAS4LOCAL() == null ? 0 : this.getAS4LOCAL().hashCode());
        hash = 37 * hash + (this.getAS4VERS() == null ? 0 : this.getAS4VERS().hashCode());
        hash = 37 * hash + (this.getPOSITION() == null ? 0 : this.getPOSITION().hashCode());
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
        if (!(obj instanceof BapiDD03LId)) {
            return false;
        }
        BapiDD03LId other = (BapiDD03LId) obj;
        
        return (this.getTABNAME() != null && other.getTABNAME() != null && this.getTABNAME().equals(other.getTABNAME())) 
            && (this.getFIELDNAME() != null && other.getFIELDNAME() != null && this.getFIELDNAME().equals(other.getFIELDNAME())) 
            && (this.getAS4LOCAL() != null && other.getAS4LOCAL() != null && this.getAS4LOCAL().equals(other.getAS4LOCAL())) 
            && (this.getAS4VERS() != null && other.getAS4VERS() != null && this.getAS4VERS().equals(other.getAS4VERS())) 
            && (this.getPOSITION() != null && other.getPOSITION() != null && this.getPOSITION().equals(other.getPOSITION()));
    }
}
