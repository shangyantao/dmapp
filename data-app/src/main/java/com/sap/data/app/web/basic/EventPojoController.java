package com.sap.data.app.web.basic;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sap.data.app.entity.account.User;
import com.sap.data.db.dao.BapiDD03LDao;
import com.sap.data.db.dao.BapiFUPARAREFDao;
import com.sap.data.db.dao.EventDao;
import com.sap.data.db.pojo.BapiDD03LPojo;
import com.sap.data.db.pojo.BapiFUPARAREFPojo;
import com.sap.data.db.pojo.EventPojo;
import com.sap.data.db.util.NotFoundException;

@Controller
@RequestMapping(value = "/basic")
public class EventPojoController {

	@RequestMapping(value = { "list", "" })
	public String list(Model model) throws NotFoundException {
		EventDao eventDao=new EventDao();
		List<EventPojo> list=eventDao.select();
		model.addAttribute("events", list);
		return "basic/eventList";
	}
	
	@RequestMapping(value = "create")
	public String createForm(Model model) {
		model.addAttribute("event", new EventPojo());
		return "basic/eventForm";
	}
	
	@RequestMapping(value = "save")
	public String save(EventPojo event, RedirectAttributes redirectAttributes) throws NotFoundException {
		EventDao eventDao=new EventDao();
		Session session=SecurityUtils.getSubject().getSession();
		User user=(User)session.getAttribute("CURRENT_USER_KEY");
		event.setCHANGED_BY(user.getLoginName());
		event.setDELETIONFLAG("1");
		event.setLAST_CHANGE_TIME(new Date());
		eventDao.save(event);
		redirectAttributes.addFlashAttribute("message", "创建事件" + event.getEVENT_ID() + "成功");
		return "redirect:/basic/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) throws NotFoundException {
		EventDao eventDao=new EventDao();
		EventPojo eventPojo=eventDao.getEventById(String.valueOf(id));
		boolean flag=eventDao.delete(eventPojo);
		if(flag){
		redirectAttributes.addFlashAttribute("message", "删除EVENT成功");
		}
		return "redirect:/basic/";
	}
	
	@RequestMapping(value = "view/{funName}")
	public String viewData(@PathVariable("funName") String funName,Model model) throws NotFoundException {
		BapiFUPARAREFDao dao=new BapiFUPARAREFDao();
		List<BapiFUPARAREFPojo> list=dao.selectParameters(funName);
		model.addAttribute("fupararefs", list);
		return "basic/viewData";
	}
	
	//SELECT * FROM schema_dell.e0000_dd03l l inner join schema_dell.e0000_dd04t t  on l.ROLLNAME=t.ROLLNAME and t.DDLANGUAGE='E'  left join e0000_dd03l_dm dm on l.TABNAME=dm.TABNAME and l.FIELDNAME=dm.FIELDNAME and l.AS4LOCAL=dm.AS4LOCAL and l.AS4VERS=dm.AS4VERS 
	
	@RequestMapping(value = "viewdd3l/{structure}")
	public String viewdd3l(@PathVariable("structure") String structure,Model model) throws NotFoundException {
		BapiDD03LDao dao=new BapiDD03LDao();
		List<BapiDD03LPojo> list=dao.selectTabFields(structure);
		model.addAttribute("dd03ls", list);
		return "basic/viewdd3l";
	}
	
}
