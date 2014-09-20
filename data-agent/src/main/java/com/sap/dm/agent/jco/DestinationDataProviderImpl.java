package com.sap.dm.agent.jco;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class DestinationDataProviderImpl implements DestinationDataProvider{
			
	@Override
	public Properties getDestinationProperties(String destinationName) {
		// TODO Auto-generated method stub
		String classpath = DestinationDataProviderImpl.class.getResource("/").getPath() + destinationName +".jcoDestination";
		System.out.println("Destination:" + classpath);
    	Properties pro = new Properties();
    	try {
			pro.load(new FileInputStream(new File(classpath)));
		} catch (IOException ex) {
			Logger.getLogger(DestinationDataProviderImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
		return pro;
	}

	@Override
	public void setDestinationDataEventListener(
			DestinationDataEventListener arg0) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public boolean supportsEvents() {
		// TODO Auto-generated method stub
		return false;
	}

}
