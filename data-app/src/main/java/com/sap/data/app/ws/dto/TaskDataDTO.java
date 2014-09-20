package com.sap.data.app.ws.dto;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TaskDataDTO<T> {
	
	@XmlElement
	private HashMap<String,T> mapData=new HashMap<String,T>();

	public HashMap<String, T> getMapData() {
		return mapData;
	}

	public void setMapData(HashMap<String, T> mapData) {
		this.mapData = mapData;
	}
	
	
}
