/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.model;

import com.mensa.sharewebservice.model.TradingShare;
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
public class TradingShareTest extends TestCase {
    
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
            System.out.println("Start TradingShare Test finalize"); 
//            session.getTransaction().rollback();
//            session.close();
        }
    
	@Override
	protected void tearDown() throws Exception {
            System.out.println("Start TradingShare Test tearDown"); 
            entityManagerFactory.close();
	}
        
        public TradingShare createRecord() {
            System.out.println("start createTrading"); 
            TradingShare share = new TradingShare();
            
            share.setTransaction_date(LocalDateTime.now()); 
            share.setShare_id(1); 
            share.setClosing(BigDecimal.valueOf(100)); 
            share.setPrevious_closing(BigDecimal.valueOf(100)); 
            share.setAsk(BigDecimal.valueOf(100)); 
            share.setBid(BigDecimal.valueOf(100)); 
            share.setHigh(BigDecimal.valueOf(100)); 
            share.setLow(BigDecimal.valueOf(100)); 
            share.setShares_traded(BigDecimal.valueOf(100)); 
            share.setTurnover(BigDecimal.valueOf(100)); 
            share.setOpening(BigDecimal.valueOf(100)); 
            share.setCount_traded(100); 
            share.setCount_big_traded(100); 
            share.setIs_ex_dividend_date(100); 
            share.setIs_index_share(100);  
            share.setSequence_number(100);
            
            entityManager.persist(share);
            //session.save(user);
            return share; 
        }
        
        public TradingShare getTestingRecord() {
            System.out.println("start getTestingRecord"); 
            List<TradingShare> answer = entityManager.createQuery( "select record from TradingShare record", TradingShare.class)
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
            TradingShare share = getTestingRecord(); 
            setRollback(); 
            System.out.println("Finished testCreateRecord"); 
            assertTrue(share != null); 
	}
        
        public void testDeleteRecord() {
            System.out.println("Start testDeleteRecord"); 
            setTransaction(); 
            createRecord();
            TradingShare share = getTestingRecord(); 
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
