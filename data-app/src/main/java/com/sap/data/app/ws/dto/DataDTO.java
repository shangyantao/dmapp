package com.sap.data.app.ws.dto;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DataDTO {
	@XmlElement
	private Map<String,Object> data=new HashMap<String,Object>();
	@XmlElement
	private TaskDTO taskDTO;
	
	
	public TaskDTO getTaskDTO() {
		return taskDTO;
	}

	public void setTaskDTO(TaskDTO taskDTO) {
		this.taskDTO = taskDTO;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	
}
