package com.sap.data.app.web.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sap.data.app.entity.account.User;
import com.sap.data.app.entity.system.AccountSysUser;
import com.sap.data.app.entity.system.Company;
import com.sap.data.app.entity.system.System;
import com.sap.data.app.service.system.SystemManager;

@Controller
@RequestMapping(value = "/system/accountSysUser")
public class AccountSysUserController {
	
	@Autowired
	private SystemManager systemManager;
	
	@RequestMapping(value = { "list", "" })
	public String list(Model model) {
		List<AccountSysUser> accountSysUsers = systemManager.getAllAccountSysUser();
		model.addAttribute("accountSysUsers", accountSysUsers);
		return "system/accountSysUserList";
	}
	
	@RequestMapping(value = "create")
	public String createForm(Model model) {
		model.addAttribute("accountSysUser", new AccountSysUser());
		model.addAttribute("allCompanys", systemManager.getAllCompany());
		model.addAttribute("allUsers", systemManager.getAllUser());
		model.addAttribute("allSystems", systemManager.getAllSystem());
		return "system/accountSysUserForm";
	}
	
	@RequestMapping(value = "save")
	public String save(AccountSysUser accountSysUser, RedirectAttributes redirectAttributes) {
		Company company = systemManager.getCompany(accountSysUser.getCompany().getCompanyGUID());
		accountSysUser.setCompany(company);
		User account = systemManager.getUser(accountSysUser.getAccount().getId());
		accountSysUser.setAccount(account);
		System system = systemManager.getSystem(accountSysUser.getSystem().getSystemGUID());
		accountSysUser.setSystem(system);
		systemManager.saveAccountSysUser(accountSysUser);
		redirectAttributes.addFlashAttribute("message", "创建SAP User" + accountSysUser.getSapUserId() + "成功");
		return "redirect:/system/accountSysUser/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		systemManager.deleteAccountSysUser(id);
		redirectAttributes.addFlashAttribute("message", "删除SAP User成功");
		return "redirect:/system/accountSysUser/";
	}
}
