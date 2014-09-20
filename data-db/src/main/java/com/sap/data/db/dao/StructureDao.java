/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.data.db.dao;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.sap.data.db.util.NotFoundException;

/**
 *
 * @author C5178395
 */
public final class StructureDao {
    
    private String structure = null;
    private static final Object lock = new Object();
    
    public StructureDao(String entityName) {
        this.structure = entityName;
    }
    
    public List<?> selectE0015() throws NotFoundException {
        List<?> list = null;
        Session session = null;
        try {
        	session = HibernateUtil.getSession();
            session.setCacheMode(CacheMode.GET);
            list = session.createCriteria(this.structure).list();
        } catch (HibernateException ex) {
            Logger.getLogger(StructureDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
        	HibernateUtil.close(session);
        }
        return list;
    }
    public List<?> selectE0015(int firstResult, int maxResults) throws NotFoundException {
        List<?> list = null;
        Session session = null;
        try {
        	session = HibernateUtil.getSession();
            session.setCacheMode(CacheMode.GET);
            list = session.createCriteria(this.structure).setFirstResult(firstResult).setMaxResults(maxResults).list();
        } catch (HibernateException ex) {
            Logger.getLogger(StructureDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
        	HibernateUtil.close(session);
        }
        return list;
    }
    
    public List<?> selectByJCO_USER(String jCo_USer, int firstResult, int maxResults) throws NotFoundException {
        List<?> list = null;
        Session session = null;
        try {
        	session = HibernateUtil.getSession();
            session.setCacheMode(CacheMode.GET);
            list = session.createCriteria(this.structure)
                   .add(Restrictions.eq("DId.JCO_USER", jCo_USer))
                   .setFirstResult(firstResult)
                   .setMaxResults(maxResults)
                   .list();
        } catch (HibernateException ex) {
            Logger.getLogger(StructureDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
        	HibernateUtil.close(session);
        }
        return list;
    }
    public List<?> selectByJCO_USER(String jCo_USer) throws NotFoundException {
        List<?> list = null;
        Session session = null;
        try {
        	session = HibernateUtil.getSession();
            session.setCacheMode(CacheMode.GET);
            list = session.createCriteria(this.structure)
                   .add(Restrictions.eq("DId.JCO_USER", jCo_USer))
                   .list();
        } catch (HibernateException ex) {
            Logger.getLogger(StructureDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
        	HibernateUtil.close(session);
        }
        return list;
    }
    
    public boolean select(Object entity) throws NotFoundException {
        List<?> list = null;
        Session session = null;
        try {
        	session = HibernateUtil.getSession();
            Criteria criteria = session.createCriteria(this.structure);
            if(entity != null) {
                Field[] fields = entity.getClass().getDeclaredFields();
                for(Field field : fields) {
                    field.setAccessible(true);
                    if(!"RUN_ID".equals(field.getName()) && !"LAST_CHANGE_TIME".equals(field.getName())) {//!"TASK_ID".equals(field.getName()) && 
                        Object value = field.get(entity);
                        criteria.add(Restrictions.eq(field.getName(), (value != null ? value : "")));
                    }
                }
            }
            list = criteria.list();
        } catch (HibernateException ex) {
            Logger.getLogger(StructureDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(StructureDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
        	HibernateUtil.close(session);
        }
        return (list != null && list.size() == 1);
    }
    
    public List<?> selectToAddAndDelete(String userId) throws NotFoundException {
        List<?> list = null;
        Session session = null;
        try {
        	session = HibernateUtil.getSession();
            Query query = session.createQuery("from " + this.structure + " where JCO_USER =:JCO_USER and MISC_STATUS in ('2', '3')");
            query.setString("JCO_USER", userId);
            list = query.list();
        } catch (HibernateException ex) {
            Logger.getLogger(StructureDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
        	HibernateUtil.close(session);
        }
        return list;
    }
    
    public void saveEntityToSingle(Object dto) throws NotFoundException {
        Session session = null;
        try {
        	session = HibernateUtil.getSession();
            if(dto != null) {
                session.beginTransaction();
                session.saveOrUpdate(this.structure, dto);
                session.getTransaction().commit();
            }
        } catch (HibernateException ex) {
            if(session != null) {
                session.getTransaction().rollback();
            }
            throw new NotFoundException(ex.getMessage());
        } finally {
        	HibernateUtil.close(session);
        }
    }
    
    public void saveEntityToList(List<?> entities) throws NotFoundException {
        synchronized (lock){
            Session session = null;
            try {
                if(entities != null) {
                	session = HibernateUtil.getSession();
                    session.beginTransaction();
                    int i = 0;
                    for(Object entity : entities) {
                        session.saveOrUpdate(this.structure, entity);
                        if(i++ % 20 == 0){
                            session.flush();
                            session.clear();
                        }
                    }
                    session.getTransaction().commit();
                }
            } catch (HibernateException ex) {
                if(session != null) {
                    session.getTransaction().rollback();
                }
                throw new NotFoundException(ex.getMessage());
            } finally {
            	HibernateUtil.close(session);
            }
        }
    }
    
    public void updateToExist(Object entity) throws NotFoundException {
        Session session = null;
        try {
            if(entity != null) {
            	session = HibernateUtil.getSession();
                session.beginTransaction();
                session.saveOrUpdate(this.structure, entity);
                session.getTransaction().commit();
            }
        } catch (HibernateException ex) {
            if(session != null) {
                session.getTransaction().rollback();
            }
            Logger.getLogger(StructureDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
        	HibernateUtil.close(session);
        }
    }
    
    public void updateToDelete(String userId) throws NotFoundException {
        Session session = null;
        try {
        	session = HibernateUtil.getSession();
            session.beginTransaction();
            Query query = session.createSQLQuery("update " + this.structure + " set MISC_STATUS = '3' where JCO_USER = '" + userId +"' and MISC_STATUS = '4'");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if(session != null) {
                session.getTransaction().rollback();
                session.close();
            }
            Logger.getLogger(StructureDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
        	HibernateUtil.close(session);
        }
    }
    
    public void updateToNewest(String userId) throws NotFoundException {
        Session session = null;
        try {
        	session = HibernateUtil.getSession();
            session.beginTransaction();
            Query query = session.createSQLQuery("update " + this.structure + " set MISC_STATUS = '4' where JCO_USER = '" + userId +"'");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if(session != null) {
                session.getTransaction().rollback();
            }
            Logger.getLogger(StructureDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
        	HibernateUtil.close(session);
        }
    }
    
    public void dropTable() throws NotFoundException {
    	Session session = null;
        try {
        	session = HibernateUtil.getSession();
            session.beginTransaction();
            Query query = session.createSQLQuery("drop table " + this.structure); 
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if(session != null) {
                session.getTransaction().rollback();
            }
            Logger.getLogger(StructureDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
        	HibernateUtil.close(session);
        }
    }
    
    public void deleteTable() throws NotFoundException {
        Session session = null;
        try {
        	session = HibernateUtil.getSession();
            session.beginTransaction();
            Query query = session.createSQLQuery("delete from " + this.structure);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if(session != null) {
                session.getTransaction().rollback();
            }
            Logger.getLogger(StructureDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
        	HibernateUtil.close(session);
        }
    }
    
    public void deleteToDelete(String userId) throws NotFoundException {
        Session session = null;
        try {
        	session = HibernateUtil.getSession();
            session.beginTransaction();
            Query query = session.createSQLQuery("delete from " + this.structure + " where JCO_USER = '" + userId +"' and MISC_STATUS = '3'");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if(session != null) {
                session.getTransaction().rollback();
            }
            Logger.getLogger(StructureDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
        	HibernateUtil.close(session);
        }
    }
    
    public void deleteEntity(Object entity) throws NotFoundException {
        Session session = null;
        try {
            if(entity != null) {
            	session = HibernateUtil.getSession();
            	session.beginTransaction();
                session.delete(this.structure, entity);
                session.getTransaction().commit();
            }
        } catch (HibernateException ex) {
            if(session != null) {
                session.getTransaction().rollback();
            }
            Logger.getLogger(StructureDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
        	HibernateUtil.close(session);
        }
    }
    
    public int countSize() throws NotFoundException {
    	int querySize=0;
    	Session session = null;
        try {
        	session = HibernateUtil.getSession();
            session.setCacheMode(CacheMode.GET);
            Query query=session.createQuery("select count(o) from "+ this.structure +" o");
            querySize=((Long)query.iterate().next()).intValue();
        } catch (HibernateException | IllegalArgumentException ex) {
            Logger.getLogger(StructureDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException(ex.getMessage());
        } finally {
        	HibernateUtil.close(session);
        }
        return querySize;
    }
    
}
