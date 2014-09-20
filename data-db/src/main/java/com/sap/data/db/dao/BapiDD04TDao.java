/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.data.db.dao;

import com.sap.data.db.pojo.BapiDD04TPojo;
import com.sap.data.db.util.NotFoundException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author C5178395
 */
public class BapiDD04TDao {
    
    public void save(BapiDD04TPojo pojo) throws NotFoundException {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();
            session.saveOrUpdate("BapiDD04TPojo", pojo);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            Logger.getLogger(BapiDD03LDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
    }
    
    public void save(List<BapiDD04TPojo> list) throws NotFoundException {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();
            for(BapiDD04TPojo pojo : list) {
                session.saveOrUpdate("BapiDD04TPojo", pojo);
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
