/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.data.db.dao;

import com.sap.data.db.pojo.BapiDD03LDMPojo;
import com.sap.data.db.util.NotFoundException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author C5178395
 */
@SuppressWarnings("unchecked")
public class BapiDD03LDMDao {

    public List<BapiDD03LDMPojo> selectKeyFields(String tabName) throws NotFoundException {
        List<BapiDD03LDMPojo> list = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.setCacheMode(CacheMode.GET);
            list = session.createCriteria("BapiDD03LDMPojo")
                .add(Restrictions.eq("id.TABNAME", tabName))
                .add(Restrictions.eq("DM_KEYFLAG", "X"))
                .list();
        } catch (HibernateException ex) {
            Logger.getLogger(BapiDD03LDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
            HibernateUtil.close(session);
        }
        return list;
    }
}
