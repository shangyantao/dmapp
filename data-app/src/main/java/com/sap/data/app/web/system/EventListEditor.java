package com.sap.data.app.web.system;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.data.app.entity.system.Event;
import com.sap.data.app.service.system.SystemManager;
import com.sap.data.core.utils.Collections3;

/**
 * 用于转换用户表单中复杂对象Group的checkbox的关联。
 * 
 */
@Component
public class EventListEditor extends PropertyEditorSupport {

	@Autowired
	private SystemManager systemManager;

	/**
	 * Back From Page
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		String[] ids = StringUtils.split(text, ",");
		List<Event> events = new ArrayList<Event>();
		for (String id : ids) {
			Event e=systemManager.getEvent(id);
			events.add(e);
		}
		setValue(events);
	}

	/**
	 * Set to page
	 */
	@Override
	public String getAsText() {
		return Collections3.extractToString((List) getValue(), "id", ",");
	}
}
