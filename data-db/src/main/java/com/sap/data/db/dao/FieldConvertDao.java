package com.sap.data.db.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sap.data.db.pojo.FieldConvertPojo;
import com.sap.data.db.util.NotFoundException;

public class FieldConvertDao {
	
	@SuppressWarnings("unchecked")
	public List<FieldConvertPojo> selectConvertField() throws NotFoundException {
		List<FieldConvertPojo> list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.setCacheMode(CacheMode.GET);
			list = session.createCriteria("FieldConvertPojo").list();
		} catch (HibernateException ex) {
			Logger.getLogger(FieldConvertDao.class.getName()).log(Level.SEVERE, null, ex);
			throw new NotFoundException(ex.getMessage());
		} finally {
			HibernateUtil.close(session);
		}
		return list;
	}
}
