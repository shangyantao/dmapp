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
public class BapiDD03LPojo implements Serializable {
    
	private static final long serialVersionUID = 1L;
    private BapiDD03LId id;
    private BapiDD03LDMPojo dd03ldm;
    private String KEYFLAG;
    private String MANDATORY;
    private String ROLLNAME;
    private String CHECKTABLE;
    private String ADMINFIELD;
    private String INTTYPE;
    private Integer INTLEN;
    private String REFTABLE;
    private String PRECFIELD;
    private String REFFIELD;
    private String CONROUT;
    private String NOTNULL;
    private String DATATYPE;
    private Integer LENG;
    private Integer DECIMALS;
    private String DOMNAME;
    private String SHLPORIGIN;
    private String TABLETYPE;
    private Integer DEPTH;
    private String COMPTYPE;
    private String REFTYPE;
    private String LANGUFLAG;
    private Integer DBPOSITION;
    private String ANONYMOUS;
    private Integer OUTPUTSTYLE;
    
    public BapiDD03LPojo() {
    }
    
    public BapiDD03LPojo(BapiDD03LId id) {
       this.id = id;
    }
    
    public BapiDD03LPojo(BapiDD03LId id, BapiDD03LDMPojo dd03ldm, String KEYFLAG, String MANDATORY, String ROLLNAME, String CHECKTABLE, 
        String ADMINFIELD, String INTTYPE, Integer INTLEN, String REFTABLE, String PRECFIELD, String REFFIELD, String CONROUT, String NOTNULL, String DATATYPE, Integer LENG, Integer DECIMALS, 
        String DOMNAME, String SHLPORIGIN, String TABLETYPE, Integer DEPTH, String COMPTYPE, String REFTYPE, String LANGUFLAG, Integer DBPOSITION, String ANONYMOUS, Integer OUTPUTSTYLE){
        this.id = id;
        this.dd03ldm = dd03ldm;
        this.KEYFLAG = KEYFLAG;
        this.MANDATORY = MANDATORY;
        this.ROLLNAME = ROLLNAME;
        this.CHECKTABLE = CHECKTABLE;
        this.ADMINFIELD = ADMINFIELD;
        this.INTTYPE = INTTYPE;
        this.INTLEN = INTLEN;
        this.REFTABLE = REFTABLE;
        this.PRECFIELD = PRECFIELD;
        this.REFFIELD = REFFIELD;
        this.CONROUT = CONROUT;
        this.NOTNULL = NOTNULL;
        this.DATATYPE = DATATYPE;
        this.LENG = LENG;
        this.DECIMALS = DECIMALS;
        this.DOMNAME = DOMNAME;
        this.SHLPORIGIN = SHLPORIGIN;
        this.TABLETYPE = TABLETYPE;
        this.DEPTH = DEPTH;
        this.COMPTYPE = COMPTYPE;
        this.REFTYPE = REFTYPE;
        this.LANGUFLAG = LANGUFLAG;
        this.DBPOSITION = DBPOSITION;
        this.ANONYMOUS = ANONYMOUS;
        this.OUTPUTSTYLE = OUTPUTSTYLE;
    }

    public BapiDD03LId getId() {
        return id;
    }

    public void setId(BapiDD03LId id) {
        this.id = id;
    }

    public BapiDD03LDMPojo getDd03ldm() {
        return dd03ldm;
    }

    public void setDd03ldm(BapiDD03LDMPojo dd03ldm) {
        this.dd03ldm = dd03ldm;
    }

    public String getKEYFLAG() {
        return KEYFLAG;
    }

    public void setKEYFLAG(String KEYFLAG) {
        this.KEYFLAG = KEYFLAG;
    }

    public String getMANDATORY() {
        return MANDATORY;
    }

    public void setMANDATORY(String MANDATORY) {
        this.MANDATORY = MANDATORY;
    }

    public String getROLLNAME() {
        return ROLLNAME;
    }

    public void setROLLNAME(String ROLLNAME) {
        this.ROLLNAME = ROLLNAME;
    }

    public String getCHECKTABLE() {
        return CHECKTABLE;
    }

    public void setCHECKTABLE(String CHECKTABLE) {
        this.CHECKTABLE = CHECKTABLE;
    }

    public String getADMINFIELD() {
        return ADMINFIELD;
    }

    public void setADMINFIELD(String ADMINFIELD) {
        this.ADMINFIELD = ADMINFIELD;
    }

    public String getINTTYPE() {
        return INTTYPE;
    }

    public void setINTTYPE(String INTTYPE) {
        this.INTTYPE = INTTYPE;
    }

    public Integer getINTLEN() {
        return INTLEN;
    }

    public void setINTLEN(Integer INTLEN) {
        this.INTLEN = INTLEN;
    }

    public String getREFTABLE() {
        return REFTABLE;
    }

    public void setREFTABLE(String REFTABLE) {
        this.REFTABLE = REFTABLE;
    }

    public String getPRECFIELD() {
        return PRECFIELD;
    }

    public void setPRECFIELD(String PRECFIELD) {
        this.PRECFIELD = PRECFIELD;
    }

    public String getREFFIELD() {
        return REFFIELD;
    }

    public void setREFFIELD(String REFFIELD) {
        this.REFFIELD = REFFIELD;
    }

    public String getCONROUT() {
        return CONROUT;
    }

    public void setCONROUT(String CONROUT) {
        this.CONROUT = CONROUT;
    }

    public String getNOTNULL() {
        return NOTNULL;
    }

    public void setNOTNULL(String NOTNULL) {
        this.NOTNULL = NOTNULL;
    }

    public String getDATATYPE() {
        return DATATYPE;
    }

    public void setDATATYPE(String DATATYPE) {
        this.DATATYPE = DATATYPE;
    }

    public Integer getLENG() {
        return LENG;
    }

    public void setLENG(Integer LENG) {
        this.LENG = LENG;
    }

    public Integer getDECIMALS() {
        return DECIMALS;
    }

    public void setDECIMALS(Integer DECIMALS) {
        this.DECIMALS = DECIMALS;
    }

    public String getDOMNAME() {
        return DOMNAME;
    }

    public void setDOMNAME(String DOMNAME) {
        this.DOMNAME = DOMNAME;
    }

    public String getSHLPORIGIN() {
        return SHLPORIGIN;
    }

    public void setSHLPORIGIN(String SHLPORIGIN) {
        this.SHLPORIGIN = SHLPORIGIN;
    }

    public String getTABLETYPE() {
        return TABLETYPE;
    }

    public void setTABLETYPE(String TABLETYPE) {
        this.TABLETYPE = TABLETYPE;
    }

    public Integer getDEPTH() {
        return DEPTH;
    }

    public void setDEPTH(Integer DEPTH) {
        this.DEPTH = DEPTH;
    }

    public String getCOMPTYPE() {
        return COMPTYPE;
    }

    public void setCOMPTYPE(String COMPTYPE) {
        this.COMPTYPE = COMPTYPE;
    }

    public String getREFTYPE() {
        return REFTYPE;
    }

    public void setREFTYPE(String REFTYPE) {
        this.REFTYPE = REFTYPE;
    }

    public String getLANGUFLAG() {
        return LANGUFLAG;
    }

    public void setLANGUFLAG(String LANGUFLAG) {
        this.LANGUFLAG = LANGUFLAG;
    }

    public Integer getDBPOSITION() {
        return DBPOSITION;
    }

    public void setDBPOSITION(Integer DBPOSITION) {
        this.DBPOSITION = DBPOSITION;
    }

    public String getANONYMOUS() {
        return ANONYMOUS;
    }

    public void setANONYMOUS(String ANONYMOUS) {
        this.ANONYMOUS = ANONYMOUS;
    }

    public Integer getOUTPUTSTYLE() {
        return OUTPUTSTYLE;
    }

    public void setOUTPUTSTYLE(Integer OUTPUTSTYLE) {
        this.OUTPUTSTYLE = OUTPUTSTYLE;
    }

}
