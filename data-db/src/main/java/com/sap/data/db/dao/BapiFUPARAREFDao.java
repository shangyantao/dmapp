/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sap.data.db.dao;

import com.sap.data.db.pojo.BapiFUPARAREFPojo;
import com.sap.data.db.util.NotFoundException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author C5178395
 */
@SuppressWarnings("unchecked")
public class BapiFUPARAREFDao {

    public List<BapiFUPARAREFPojo> select() throws NotFoundException {
        List<BapiFUPARAREFPojo> list = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.setCacheMode(CacheMode.GET);
            list = session.createCriteria("BapiFUPARAREFPojo")
                    .addOrder(Order.asc("id.FUNCNAME"))
                    .addOrder(Order.asc("id.R3STATE"))
                    .addOrder(Order.asc("id.PARAMETER"))
                    .addOrder(Order.asc("id.PARAMTYPE"))
                    .addOrder(Order.asc("PPOSITION"))
                    .list();
        } catch (HibernateException ex) {
            Logger.getLogger(BapiFUPARAREFDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
        return list;
    }

    public List<BapiFUPARAREFPojo> selectParameterStructuresByFuncName(String eventNumber) throws NotFoundException {
        List<BapiFUPARAREFPojo> list = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            Query query = session.createQuery("select rbp from BapiFUPARAREFPojo rbp, EventPojo re where rbp.id.FUNCNAME =re.FUNCNAME and re.EVENT_NUMBER=:EVENT_NUMBER and rbp.id.PARAMTYPE =:PARAMTYPE");
            query.setString("EVENT_NUMBER", eventNumber);
            query.setString("PARAMTYPE", "T");
            list = query.list();
        } catch (HibernateException ex) {
            Logger.getLogger(BapiFUPARAREFDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
        return list;
    }

    public String selectParameterStructure(String funcName, String parameter) throws NotFoundException {
        String structure = null;
        Session session = null;
        try {
            String sql = "select distinct(rbp.STRUCTURE) from BapiFUPARAREFPojo rbp where rbp.id.FUNCNAME =:FUNCNAME and rbp.id.PARAMETER =:PARAMETER";
            session = HibernateUtil.getSession();
            session.setCacheMode(CacheMode.GET);
            Query query = session.createQuery(sql);
            query.setString("FUNCNAME", funcName);
            query.setString("PARAMETER", parameter);
            structure = (String) query.uniqueResult();
        } catch (HibernateException ex) {
            Logger.getLogger(BapiDD03LDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
        return structure;
    }

    public List<BapiFUPARAREFPojo> selectInputParameters(String funcName) throws NotFoundException {
        List<BapiFUPARAREFPojo> list = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.setCacheMode(CacheMode.GET);
            list = session.createCriteria("BapiFUPARAREFPojo")
                    .add(Restrictions.eq("id.FUNCNAME", funcName))
                    .add(Restrictions.eq("id.PARAMTYPE", "I"))
                    .addOrder(Order.asc("id.FUNCNAME"))
                    .addOrder(Order.asc("id.R3STATE"))
                    .addOrder(Order.asc("id.PARAMETER"))
                    .addOrder(Order.asc("id.PARAMTYPE"))
                    .addOrder(Order.asc("PPOSITION"))
                    .list();
        } catch (HibernateException ex) {
            Logger.getLogger(BapiFUPARAREFDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
        return list;
    }

    public List<BapiFUPARAREFPojo> selectTabParameters(String funcName) throws NotFoundException {
        List<BapiFUPARAREFPojo> list = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.setCacheMode(CacheMode.GET);
            list = session.createCriteria("BapiFUPARAREFPojo")
                    .add(Restrictions.eq("id.FUNCNAME", funcName))
                    .add(Restrictions.eq("id.PARAMTYPE", "T"))
                    .addOrder(Order.asc("id.FUNCNAME"))
                    .addOrder(Order.asc("id.R3STATE"))
                    .addOrder(Order.asc("id.PARAMETER"))
                    .addOrder(Order.asc("id.PARAMTYPE"))
                    .addOrder(Order.asc("PPOSITION"))
                    .list();
        } catch (HibernateException ex) {
            Logger.getLogger(BapiFUPARAREFDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
        return list;
    }

    public List<BapiFUPARAREFPojo> selectParameters(String funcName) throws NotFoundException {
        List<BapiFUPARAREFPojo> list = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.setCacheMode(CacheMode.GET);
            list = session.createCriteria("BapiFUPARAREFPojo")
                    .add(Restrictions.eq("id.FUNCNAME", funcName))
                    .addOrder(Order.asc("id.FUNCNAME"))
                    .addOrder(Order.asc("id.R3STATE"))
                    .addOrder(Order.asc("id.PARAMETER"))
                    .addOrder(Order.asc("id.PARAMTYPE"))
                    .addOrder(Order.asc("PPOSITION"))
                    .list();
        } catch (HibernateException ex) {
            Logger.getLogger(BapiFUPARAREFDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
        return list;
    }
    
    public List<BapiFUPARAREFPojo> selectETParameters(String funcName) throws NotFoundException {
        List<BapiFUPARAREFPojo> list = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.setCacheMode(CacheMode.GET);
            list = session.createCriteria("BapiFUPARAREFPojo")
                    .add(Restrictions.eq("id.FUNCNAME", funcName))
                    .add(Restrictions.in("id.PARAMTYPE", new Object[]{"E", "T"}))
                    .add(Restrictions.sqlRestriction("STRUCTURE not like('%-%')"))
                    .addOrder(Order.asc("id.FUNCNAME"))
                    .addOrder(Order.asc("id.R3STATE"))
                    .addOrder(Order.asc("id.PARAMETER"))
                    .addOrder(Order.asc("id.PARAMTYPE"))
                    .addOrder(Order.asc("PPOSITION"))
                    .list();
        } catch (HibernateException ex) {
            Logger.getLogger(BapiFUPARAREFDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
        return list;
    }

    public BapiFUPARAREFPojo selectParameter(String funcName, String parameter) throws NotFoundException {
        BapiFUPARAREFPojo pojo = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.setCacheMode(CacheMode.GET);
            pojo = (BapiFUPARAREFPojo) session.createCriteria("BapiFUPARAREFPojo")
                    .add(Restrictions.eq("id.FUNCNAME", funcName))
                    .add(Restrictions.eq("id.PARAMETER", parameter))
                    .addOrder(Order.asc("id.FUNCNAME"))
                    .addOrder(Order.asc("id.R3STATE"))
                    .addOrder(Order.asc("id.PARAMETER"))
                    .addOrder(Order.asc("id.PARAMTYPE"))
                    .addOrder(Order.asc("PPOSITION"))
                    .setMaxResults(1)
                    .uniqueResult();
        } catch (HibernateException ex) {
            Logger.getLogger(BapiFUPARAREFDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
        return pojo;
    }

    public List<BapiFUPARAREFPojo> selectParameters(String funcName, String paramType) throws NotFoundException {
        List<BapiFUPARAREFPojo> list = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.setCacheMode(CacheMode.GET);
            list = session.createCriteria("BapiFUPARAREFPojo")
                    .add(Restrictions.eq("id.FUNCNAME", funcName))
                    .add(Restrictions.eq("id.PARAMTYPE", paramType))
                    .addOrder(Order.asc("id.FUNCNAME"))
                    .addOrder(Order.asc("id.R3STATE"))
                    .addOrder(Order.asc("id.PARAMETER"))
                    .addOrder(Order.asc("id.PARAMTYPE"))
                    .addOrder(Order.asc("PPOSITION"))
                    .list();
        } catch (HibernateException ex) {
            Logger.getLogger(BapiFUPARAREFDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
        return list;
    }

    public void save(BapiFUPARAREFPojo pojo) throws NotFoundException {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();
            session.saveOrUpdate("BapiFUPARAREFPojo", pojo);
            session.flush();
            session.clear();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            Logger.getLogger(BapiFUPARAREFDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
    }

    public void save(List<BapiFUPARAREFPojo> list) throws NotFoundException {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();
            for (BapiFUPARAREFPojo pojo : list) {
                session.saveOrUpdate("BapiFUPARAREFPojo", pojo);
                session.flush();
                session.clear();
            }
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            Logger.getLogger(BapiFUPARAREFDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
    }

}
