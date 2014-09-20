/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.data.db.util;

import com.sap.data.db.util.NotFoundException;
import com.sap.data.db.dao.HibernateUtil;
import com.sap.data.db.dao.StructureDao;
import com.sap.data.db.dao.StructureUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

/**
 *
 * @author C5178395
 */
public class ClassUtil {
    
    protected String className = null;
    protected Map<String, String> errorMessage = new HashMap<String, String>();
    protected static final Logger log = Logger.getLogger(ClassUtil.class.getName());
    
    public ClassUtil() {
        
    }
    
    public ClassUtil(String eventNumber, String paramName) {
    	this.className = (eventNumber + "_" + paramName).toUpperCase();
    }
    
    public ClassUtil(String paramName) throws NotFoundException {
        if(null == ThreadLocalUtil.getEventNumber()) {
            throw new NotFoundException("EventNumber not allow null.");
        } else {
            this.className = (ThreadLocalUtil.getEventNumber() + "_" + paramName).toUpperCase();
        }
    }
    
    public Class<?> load() {
        Class<?> cls = null;
        try {
            if(this.className != null && !this.className.isEmpty()) {
                cls = Class.forName(PropertyUtil.getHbPjPkgn() + "." + this.className);
            }
        } catch (ClassNotFoundException ex) {
            return null;
        }
        return cls;
    } 
    
    public Class<?> load(String tabName, String firstLoad) throws FileNotFoundException, IOException, 
            DocumentException, SAXException, ClassNotFoundException, NotFoundException {
        Class<?> cls = null;
        try {
            if(this.className != null && !this.className.isEmpty()) {
            	if("X".equalsIgnoreCase(firstLoad)) {
            		new StructureDao(this.className).dropTable();
            		cls = this.init(tabName, "x");
            	} else {
	                cls = Class.forName(PropertyUtil.getHbPjPkgn() + "." + this.className);
	                if(!StructureUtil.exist(this.className)) {
	                	new StructureUtil().addStructure(this.className, tabName);
	                }
	            	HibernateUtil.incarnate(this.className, "NO_FIRST_LOAD");
            	}
            }
        } catch (ClassNotFoundException ex) {
            cls = this.init(tabName, firstLoad);
        }
        return cls;
    }
    
    private Class<?> init(String tabName, String firstLoad) throws FileNotFoundException, IOException, 
            DocumentException, SAXException, ClassNotFoundException, NotFoundException {
        ClassUtil.log.log(Level.INFO, "Create class {0}", this.className);
        ConstructorUtil rcId = new ConstructorUtil(this.className + "Id").addKeyField(tabName);
        if(rcId.getKeyFlag() || this.className.endsWith("RETURN")) {
            rcId.generate(true);
            new CompilerUtil().compiler(this.className + "Id");
            ConstructorUtil rc = new ConstructorUtil(this.className).addField(tabName);
            rc.generate(false);
            new CompilerUtil().compiler(this.className);
            new StructureUtil().addStructure(this.className, tabName);
            HibernateUtil.incarnate(this.className, firstLoad);
            Class<?> cls = Class.forName(PropertyUtil.getHbPjPkgn() + "." + this.className);
            return cls;
        } else {
            throw new NotFoundException(this.className + " not key field found in the table dd03l_dm or dd03l.");
        }
    }
    
}
