package com.sap.data.app.web.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sap.data.app.entity.system.Company;
import com.sap.data.app.entity.system.Event;
import com.sap.data.app.service.system.SystemManager;

@Controller
@RequestMapping(value = "/system/event")
public class EventController {

	@Autowired
	private SystemManager systemManager;
	
	@RequestMapping(value = { "list", "" })
	public String list(Model model) {
		List<Event> events = systemManager.getAllEvent();
		model.addAttribute("events", events);
		return "system/eventList";
	}
	
	@RequestMapping(value = "createCompanyEvent")
	public String createCompanyEventForm(Model model) {
		List<Event> events = systemManager.getAllEvent();
		List<Company> companys=systemManager.getAllCompany();
		model.addAttribute("events", events);
		model.addAttribute("companys", companys);
		return "system/companyEventForm";
	}
	
	@RequestMapping(value = "saveCompanyEvent")
	public String save(Company company,Event event, RedirectAttributes redirectAttributes) {
		systemManager.saveEvent(event);
		redirectAttributes.addFlashAttribute("message", "创建事件" + event.getEventId() + "成功");
		return "redirect:/system/event/";
	}
	
	@RequestMapping(value = "create")
	public String createForm(Model model) {
		model.addAttribute("event", new Event());
		return "system/eventForm";
	}

	@RequestMapping(value = "save")
	public String save(Event event, RedirectAttributes redirectAttributes) {
		systemManager.saveEvent(event);
		redirectAttributes.addFlashAttribute("message", "创建事件" + event.getEventId() + "成功");
		return "redirect:/system/event/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		systemManager.deleteEvent(id);
		redirectAttributes.addFlashAttribute("message", "删除事件成功");
		return "redirect:/system/event/";
	}
 
}
