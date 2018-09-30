/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dal;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matt_
 */
public class RecordHandler<T> {
    private Logger log = LoggerFactory.getLogger(RecordHandler.class);
    private EntityManagerFactory entityManagerFactory; 
    private EntityManager entityManager; 

    public RecordHandler() {
        entityManagerFactory = Persistence.createEntityManagerFactory( "org.hibernate.share.jpa" );
        entityManager = entityManagerFactory.createEntityManager();
    }
    
    @Override
    public void finalize() {
        entityManager.close();
        entityManagerFactory.close();
    }
    
    private void setEntityManager() {
        if (!entityManager.isOpen()) {
            entityManager.close(); 
            entityManager = entityManagerFactory.createEntityManager();
        }        
    }
    
    public EntityManager getEntityManager() {
        return this.entityManager; 
    }
    
    public EntityManagerFactory getEntityManagerFactory() {
        return this.entityManagerFactory; 
    }
    
    public boolean CreateOrUpdate(T record) {
        boolean answer = true; 
        setTransaction(); 
        try {
            entityManager.persist(record);
            setCommit();
        } catch (Exception e) {
            if (e != null) log.error(e.getMessage());
            setRollback();
            answer = false; 
        }
        return answer; 
    }

    public boolean Delete(T record) {
        boolean answer = true; 
        setTransaction(); 
        try {
            entityManager.remove(record);
            setCommit();
        } catch (Exception e) {
            if (e != null) log.error(e.getMessage());
            setRollback();
            answer = false; 
        }
        return answer; 
    }

    public List<T> GetList(String sql) {
        return GetQuery(sql).getResultList(); 
    }
    
    public Query GetQuery(String sql) {
        setEntityManager(); 
        return entityManager.createQuery(sql); 
    }
        
    public void setTransaction() {
        if (entityManager.getTransaction().isActive())  {
            setRollback(); 
        }
        setEntityManager(); 
        entityManager.getTransaction().begin();
    }

    public void setRollback() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }
    
    public void setCommit() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
    }
}
