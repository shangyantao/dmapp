/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.data.db.dao;

import com.sap.data.db.pojo.BapiDD03LPojo;
import com.sap.data.db.util.NotFoundException;

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
public class BapiDD03LDao {

	public List<BapiDD03LPojo> select() throws NotFoundException {
        List<BapiDD03LPojo> list = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.setCacheMode(CacheMode.GET);
            list = session.createCriteria("BapiDD03LPojo")
                .addOrder(Order.asc("id.TABNAME"))
                .addOrder(Order.asc("id.FIELDNAME"))
                .addOrder(Order.asc("id.AS4LOCAL"))
                .addOrder(Order.asc("id.AS4VERS"))
                .addOrder(Order.asc("id.POSITION"))
                .list();
        } catch (HibernateException ex) {
            Logger.getLogger(BapiDD03LDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
        return list;
    }
    
    public List<BapiDD03LPojo> selectTabFields(String tabName) throws NotFoundException {
        List<BapiDD03LPojo> list = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.setCacheMode(CacheMode.GET);
            list = session.createCriteria("BapiDD03LPojo")
                .add(Restrictions.eq("id.TABNAME", tabName))
                .addOrder(Order.asc("id.POSITION"))
                .list();
        } catch (HibernateException ex) {
            Logger.getLogger(BapiDD03LDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
        return list;
    }
    
    public void save(BapiDD03LPojo pojo) throws NotFoundException {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();
            session.saveOrUpdate("BapiDD03LPojo", pojo);
            session.flush();
            session.clear();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            Logger.getLogger(BapiDD03LDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
    }
    
    public void save(List<BapiDD03LPojo> list) throws NotFoundException {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();
            for(BapiDD03LPojo pojo : list) {
                session.saveOrUpdate("BapiDD03LPojo", pojo);
                session.flush();
                session.clear();
            }
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            Logger.getLogger(BapiDD03LDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
    }
}
