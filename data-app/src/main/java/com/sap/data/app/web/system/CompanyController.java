package com.sap.data.app.web.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sap.data.app.entity.system.Company;
import com.sap.data.app.service.system.SystemManager;

@Controller
@RequestMapping(value = "/system/company")
public class CompanyController {

	@Autowired
	private SystemManager systemManager;
	/*@Autowired
	private EventListEditor eventListEditor;*/
 
	@InitBinder
	public void initBinder(WebDataBinder b) {
		//b.registerCustomEditor(List.class, "eventList", eventListEditor);
	}
	
	@RequestMapping(value = { "list", "" })
	public String list(Model model) {
		List<Company> companys = systemManager.getAllCompany();
		model.addAttribute("companys", companys);
		return "system/companyList";
	}
	
	@RequestMapping(value = "create")
	public String createForm(Model model) {
		model.addAttribute("company", new Company());
		//model.addAttribute("allEvents", systemManager.getAllEvent());
		return "system/companytForm";
	}

	@RequestMapping(value = "save")
	public String save(Company company, RedirectAttributes redirectAttributes) {
		systemManager.saveCompany(company);
		redirectAttributes.addFlashAttribute("message", "创建公司" + company.getCmyName() + "成功");
		return "redirect:/system/company/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		systemManager.deleteCompany(id);
		redirectAttributes.addFlashAttribute("message", "删除公司成功");
		return "redirect:/system/company/";
	}
	
}
