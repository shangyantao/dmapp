package com.sap.data.app.web.account;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.data.app.entity.account.Group;
import com.sap.data.app.service.account.AccountManager;
import com.sap.data.core.utils.Collections3;

/**
 * 用于转换用户表单中复杂对象Group的checkbox的关联。
 * 
 */
@Component
public class GroupListEditor extends PropertyEditorSupport {

	@Autowired
	private AccountManager accountManager;

	/**
	 * Back From Page
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		String[] ids = StringUtils.split(text, ",");
		List<Group> groups = new ArrayList<Group>();
		for (String id : ids) {
			Group group = accountManager.getGroup(Long.valueOf(id));
			groups.add(group);
		}
		setValue(groups);
	}

	/**
	 * Set to page
	 */
	@Override
	public String getAsText() {
		return Collections3.extractToString((List) getValue(), "id", ",");
	}
}
