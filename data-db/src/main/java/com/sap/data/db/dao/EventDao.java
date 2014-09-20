/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.data.db.dao;

import com.sap.data.db.pojo.EventPojo;
import com.sap.data.db.util.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author C5178395
 */
@SuppressWarnings("unchecked")
public class EventDao {

	public List<EventPojo> select() throws NotFoundException {
		List<EventPojo> list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.setCacheMode(CacheMode.GET);
			list = session.createCriteria("EventPojo")
					.add(Restrictions.eq("DELETIONFLAG", "1"))
					.addOrder(Order.desc("EVENT_ID")).list();
		} catch (HibernateException ex) {
			Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
			throw new NotFoundException(ex.getMessage());
		} finally {
			HibernateUtil.close(session);
		}
		return list;
	}

	/**
	 * delete EventPojo by id
	 * 
	 * @author C5208985
	 * @param id
	 * @return
	 * @throws NotFoundException 
	 */
	public EventPojo getEventById(String id) throws NotFoundException {
		Session session = null;
		EventPojo eventPojo = null;
		try {
			session = HibernateUtil.getSession();
			eventPojo = (EventPojo) session.get("EventPojo",
					Integer.valueOf(id));
		} catch (HibernateException ex) {
			Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
			throw new NotFoundException(ex.getMessage());
		} finally {
			HibernateUtil.close(session);
		}
		return eventPojo;
	}

	private EventPojo selectMaxEvent() throws NotFoundException {
		List<EventPojo> list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.setCacheMode(CacheMode.GET);
			list = session.createCriteria("EventPojo")
					.addOrder(Order.desc("EVENT_ID")).list();
		} catch (HibernateException ex) {
			Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
			throw new NotFoundException(ex.getMessage());
		} finally {
			HibernateUtil.close(session);
		}
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public String selectFuncName(String eventNumber) throws NotFoundException {
		List<EventPojo> list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.setCacheMode(CacheMode.GET);
			list = session.createCriteria("EventPojo")
					.add(Restrictions.eq("EVENT_NUMBER", eventNumber))
					.addOrder(Order.asc("EVENT_ID")).list();
		} catch (HibernateException ex) {
			Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
			throw new NotFoundException(ex.getMessage());
		} finally {
			HibernateUtil.close(session);
		}
		return list != null && !list.isEmpty() ? list.get(0).getFUNCNAME() : "";
	}

	public List<EventPojo> selectEvents(String eventNumber) throws NotFoundException {
		List<EventPojo> evnts = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.setCacheMode(CacheMode.GET);
			evnts = session.createCriteria("EventPojo")
					.add(Restrictions.eq("EVENT_NUMBER", eventNumber))
					.addOrder(Order.asc("EVENT_ID")).list();
		} catch (HibernateException ex) {
			Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
			throw new NotFoundException(ex.getMessage());
		} finally {
			HibernateUtil.close(session);
		}
		return evnts;
	}
	
	public EventPojo buildEvent(String userId, String jCoFunction) throws NotFoundException {
		EventPojo event = this.selectMaxEvent();
		if (null != event && null != event.getEVENT_NUMBER()) {
			String eventNumber = "E0000";
			eventNumber = event.getEVENT_NUMBER();
			EventPojo createEvent = new EventPojo();
			Integer createEventNumber = Integer.valueOf(eventNumber.substring(
					1, eventNumber.length())) + 1;
			if (createEventNumber < 10) {
				createEvent.setEVENT_NUMBER("E000" + createEventNumber);
			} else if (createEventNumber < 100) {
				createEvent.setEVENT_NUMBER("E00" + createEventNumber);
			} else if (createEventNumber < 1000) {
				createEvent.setEVENT_NUMBER("E0" + createEventNumber);
			} else {
				createEvent.setEVENT_NUMBER("E" + createEventNumber);
			}
			createEvent.setEVENT_NAME("SAP_" + jCoFunction);
			createEvent.setFUNCNAME(jCoFunction);
			createEvent.setCHANGED_BY(userId);
			createEvent.setDELETIONFLAG("0");
			createEvent.setLAST_CHANGE_TIME(new Date());
			this.save(createEvent);
			return createEvent;
		} else {
			return null;
		}

	}

	public String selectEventNumber(String jCoFunction) throws NotFoundException {
		List<EventPojo> list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.setCacheMode(CacheMode.GET);
			list = session.createCriteria("EventPojo")
					.add(Restrictions.eq("FUNCNAME", jCoFunction)).list();
		} catch (HibernateException ex) {
			Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
			throw new NotFoundException(ex.getMessage());
		} finally {
			HibernateUtil.close(session);
		}
		if (list != null && !list.isEmpty()) {
			return list.get(0).getEVENT_NUMBER();
		} else {
			return null;
		}
	}
	
	public void save(EventPojo event) throws NotFoundException {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			session.saveOrUpdate("EventPojo", event);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			session.getTransaction().rollback();
			Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
			throw new NotFoundException(ex.getMessage());
		} finally {
			HibernateUtil.close(session);
		}
	}

	public boolean delete(EventPojo eventPojo) throws NotFoundException {
		Session session = null;
		// EventPojo eventPojo=null;
		boolean flag = false;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			// eventPojo=getEventById(id);
			if (eventPojo != null) {
				eventPojo.setDELETIONFLAG("0");
				session.saveOrUpdate("EventPojo", eventPojo);
				flag = true;
			}
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			session.getTransaction().rollback();
			Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
			throw new NotFoundException(ex.getMessage());
		} finally {
			HibernateUtil.close(session);
		}
		return flag;
	}

	public void save(List<EventPojo> evnts) throws NotFoundException {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			for (EventPojo evnt : evnts) {
				session.saveOrUpdate("EventPojo", evnt);
				session.flush();
				session.clear();
			}
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			session.getTransaction().rollback();
			Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
			throw new NotFoundException(ex.getMessage());
		} finally {
			HibernateUtil.close(session);
		}
	}

}
