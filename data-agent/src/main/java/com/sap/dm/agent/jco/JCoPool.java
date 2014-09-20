/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.dm.agent.jco;

import com.sap.data.db.util.NotFoundException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author C5178395
 */
public class JCoPool {
    
    private final String abapAs = "pool/";
//    private final String appProp = "agent/jco-application.properties";
    
    static {
    	System.out.println("Register Destination Data Provider.");
		Environment.registerDestinationDataProvider(new DestinationDataProviderImpl());
    }
    
    public JCoDestination getJCoDestination(String destinationName) throws JCoException {
        JCoDestination jCoDestination = JCoDestinationManager.getDestination(this.abapAs + destinationName);
        jCoDestination.ping();
        return jCoDestination;
    }
    
    public JCoFunction getJCoFunction(JCoDestination jCoDestination, String name) throws JCoException, NotFoundException {
        JCoFunction jCoFunction = jCoDestination.getRepository().getFunction(name);
        if(jCoFunction == null) {
            throw new NotFoundException(name + " not found in SAP.");
        }
        return jCoFunction;
    }
    
    public void addJCoDestination(String destinationName, String asHost, String sysNr, String client, String user, String passwd, String lang, String codePage, String poolCapacity, String peakLimit) throws IOException {
    	Properties jCoDes = new Properties();
    	jCoDes.setProperty(DestinationDataProvider.JCO_ASHOST, asHost);
        jCoDes.setProperty(DestinationDataProvider.JCO_SYSNR, sysNr);
        jCoDes.setProperty(DestinationDataProvider.JCO_CLIENT, client);
        jCoDes.setProperty(DestinationDataProvider.JCO_USER, user);
        jCoDes.setProperty(DestinationDataProvider.JCO_PASSWD, passwd);
        jCoDes.setProperty(DestinationDataProvider.JCO_LANG, lang);
        jCoDes.setProperty(DestinationDataProvider.JCO_CODEPAGE, codePage);
        jCoDes.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, poolCapacity);
        jCoDes.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, peakLimit);
        String classpath = JCoPool.class.getResource("/").getPath() + this.abapAs;
        
        File file = new File(classpath + destinationName + ".jcoDestination");
        try (FileOutputStream fos = new FileOutputStream(file, false)) {
            jCoDes.store(fos, "");
            fos.close();
        }
    }
    
}
