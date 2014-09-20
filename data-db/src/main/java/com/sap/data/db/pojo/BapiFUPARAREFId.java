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
public class BapiFUPARAREFId implements Serializable {
    
	private static final long serialVersionUID = 1L;
    private String FUNCNAME;
    private String R3STATE;
    private String PARAMETER;
    private String PARAMTYPE;
    
    public BapiFUPARAREFId() {
        
    }
    
    public BapiFUPARAREFId(String FUNCNAME, String R3STATE, String PARAMETER, String PARAMTYPE) {
        this.FUNCNAME = FUNCNAME;
        this.R3STATE = R3STATE;
        this.PARAMETER = PARAMETER;
        this.PARAMTYPE = PARAMTYPE;
    }
        
    public String getFUNCNAME() {
        return FUNCNAME;
    }

    public void setFUNCNAME(String FUNCNAME) {
        this.FUNCNAME = FUNCNAME;
    }

    public String getR3STATE() {
        return R3STATE;
    }

    public void setR3STATE(String R3STATE) {
        this.R3STATE = R3STATE;
    }

    public String getPARAMETER() {
        return PARAMETER;
    }

    public void setPARAMETER(String PARAMETER) {
        this.PARAMETER = PARAMETER;
    }

    public String getPARAMTYPE() {
        return PARAMTYPE;
    }

    public void setPARAMTYPE(String PARAMTYPE) {
        this.PARAMTYPE = PARAMTYPE;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.getFUNCNAME() == null ? 0 : this.getFUNCNAME().hashCode());
        hash = 37 * hash + (this.getR3STATE() == null ? 0 : this.getR3STATE().hashCode());
        hash = 37 * hash + (this.getPARAMETER() == null ? 0 : this.getPARAMETER().hashCode());
        hash = 37 * hash + (this.getPARAMTYPE() == null ? 0 : this.getPARAMTYPE().hashCode());
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
        if (!(obj instanceof BapiFUPARAREFId)) {
            return false;
        }
        BapiFUPARAREFId other = (BapiFUPARAREFId) obj;
        
        return (this.getFUNCNAME() != null && other.getFUNCNAME() != null && this.getFUNCNAME().equals(other.getFUNCNAME())) 
            && (this.getR3STATE() != null && other.getR3STATE() != null && this.getR3STATE().equals(other.getR3STATE())) 
            && (this.getPARAMETER() != null && other.getPARAMETER() != null && this.getPARAMETER().equals(other.getPARAMETER())) 
            && (this.getPARAMTYPE() != null && other.getPARAMTYPE() != null && this.getPARAMTYPE().equals(other.getPARAMTYPE()));
    }
    
}
