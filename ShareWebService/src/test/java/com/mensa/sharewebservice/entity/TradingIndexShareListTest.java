/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertTrue;

/**
 *
 * @author matt_
 */
public class TradingIndexShareListTest extends TestCase {
    
        EntityManagerFactory entityManagerFactory; 
        EntityManager entityManager; 
    
        @Override
	protected void setUp() throws Exception {
            entityManagerFactory = Persistence.createEntityManagerFactory( "org.hibernate.share.jpa" );
	}

        // </editor-fold>
        
        @Override
        public void finalize()
        {
            System.out.println("Start TradingIndexShareList Test finalize"); 
//            session.getTransaction().rollback();
//            session.close();
        }
    
	@Override
	protected void tearDown() throws Exception {
            System.out.println("Start TradingIndexShareList Test tearDown"); 
            entityManagerFactory.close();
	}
        
        public TradingIndexShareList createRecord() {
            System.out.println("start createTrading"); 
            TradingIndexShareList share = new TradingIndexShareList();
            
            share.setShare_id(1); 
            
            entityManager.persist(share);
            //session.save(user);
            return share; 
        }
        
        public TradingIndexShareList getTestingRecord() {
            System.out.println("start getTestingRecord"); 
            List<TradingIndexShareList> answer = entityManager.createQuery( "select record from TradingIndexShareList record", TradingIndexShareList.class)
                    .setMaxResults(1)
                    .getResultList(); 
            System.out.println("finished getTestingRecord"); 
            if (answer.size() > 0) {
                return answer.get(0); 
            } else {
                return null; 
            }
        }
        
        public void setTransaction() {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
        }
        
        public void setRollback() {
            entityManager.getTransaction().rollback();
            entityManager.close(); 
        }
        
	public void testCreateRecord() {
            System.out.println("Start testCreateRecord"); 
            setTransaction(); 
            createRecord(); 
            //throw new java.lang.Error("*****************Testing Error*********************"); 
            TradingIndexShareList share = getTestingRecord(); 
            setRollback(); 
            System.out.println("Finished testCreateRecord"); 
            assertTrue(share != null); 
	}
        
        public void testDeleteRecord() {
            System.out.println("Start testDeleteRecord"); 
            setTransaction(); 
            createRecord();
            TradingIndexShareList share = getTestingRecord(); 
            boolean existedRecord = (share != null); 
            if (existedRecord) {
                System.out.println("testDeleteRecord existed record"); 
                entityManager.remove(share);
                share = getTestingRecord(); 
                System.out.println("Finished testDeleteRecord"); 
                assertTrue(share == null); 
            } else {
                assertTrue(existedRecord); 
            }
            setRollback(); 
        }
}
