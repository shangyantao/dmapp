package com.sap.data.app.web.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sap.data.app.service.system.SystemManager;
import com.sap.data.app.entity.system.Agent;
import com.sap.data.app.entity.system.Company;
import com.sap.data.app.entity.system.System;

@Controller
@RequestMapping(value = "/system/system")
public class SystemController {

	@Autowired
	private SystemManager systemManager;
	
	@RequestMapping(value = { "list", "" })
	public String list(Model model) {
		List<System> systems = systemManager.getAllSystem();
		model.addAttribute("systems", systems);
		return "system/systemList";
	}
	
	@RequestMapping(value = "create")
	public String createForm(Model model) {
		model.addAttribute("system", new System());
		model.addAttribute("allCompanys", systemManager.getAllCompany());
		model.addAttribute("allAgents", systemManager.getAllAgent());
		return "system/systemForm";
	}
	
	@RequestMapping(value = "save")
	public String save(System system, RedirectAttributes redirectAttributes) {
		Company company = systemManager.getCompany(system.getCompany().getCompanyGUID());
		system.setCompany(company);
		systemManager.saveSystem(system);
		redirectAttributes.addFlashAttribute("message", "创建系统" + system.getSystemDes() + "成功");
		return "redirect:/system/system/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		systemManager.deleteSystem(id);
		redirectAttributes.addFlashAttribute("message", "删除系统成功");
		return "redirect:/system/system/";
	}
}
