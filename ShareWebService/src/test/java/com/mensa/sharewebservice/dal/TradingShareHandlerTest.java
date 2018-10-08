/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dal;

import com.mensa.sharewebservice.entity.TradingShare;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author matt_
 */
public class TradingShareHandlerTest {
    public TradingShare record = new TradingShare(); 
    
    public TradingShareHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        record = new TradingShare(); 
        record.setTransaction_date(LocalDateTime.now());
        record.setShare_id(1);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Exist method, of class TradingShareHandler.
     */
    @Test
    public void testExist() {
        System.out.println("Exist");
        TradingShareHandler instance = new TradingShareHandler();
        instance.Create(record);
        boolean expResult = true;
        boolean result = instance.Exist(record);
        instance.Delete(record);
        assertEquals(expResult, result);
    }

    /**
     * Test of GetAll method, of class DateMasterHandler.
     */
    @Test
    public void testGetAll() {
        System.out.println("GetAll");
        TradingShareHandler instance = new TradingShareHandler();
        
        TradingShare testing01 = new TradingShare(); 
        testing01.setTransaction_date(LocalDateTime.of(2000, 1, 1, 0, 0));
        testing01.setShare_id(1);
        TradingShare testing02 = new TradingShare(); 
        testing02.setTransaction_date(LocalDateTime.of(2000, 1, 2, 0, 0));
        testing02.setShare_id(1);
        TradingShare testing03 = new TradingShare(); 
        testing03.setTransaction_date(LocalDateTime.of(2000, 1, 2, 0, 0));
        testing03.setShare_id(2);
        
        int expResult = 3;
        instance.Create(testing01);
        instance.Create(testing02); 
        instance.Create(testing03); 
        int result = instance.GetAll().size(); 
        instance.Delete(testing01);
        instance.Delete(testing02); 
        instance.Delete(testing03); 
        assertTrue(expResult <= result);
    }

    /**
     * Test of Get method, of class TradingShareHandler.
     */
    @Test
    public void testGet() {
        System.out.println("Get");
        TradingShareHandler instance = new TradingShareHandler();
        instance.Create(record);
        TradingShare expResult = record;
        TradingShare result = instance.Get(record);
        instance.Delete(record);
        assertEquals(expResult.getShare_id(), result.getShare_id());
    }

    /**
     * Test of Create method, of class TradingShareHandler.
     */
    @Test
    public void testCreate() {
        System.out.println("Create");
        TradingShareHandler instance = new TradingShareHandler();
        TradingShare expResult = record;
        TradingShare result = instance.CreateAndReturn(record);
        instance.Delete(record);
        assertEquals(expResult.getShare_id(), result.getShare_id());
    }

    /**
     * Test of Update method, of class TradingShareHandler.
     */
    @Test
    public void testUpdate() {
        System.out.println("Update");
        TradingShareHandler instance = new TradingShareHandler();
        instance.Create(record);
        boolean expResult = true;
        boolean result = instance.Update(record);
        instance.Delete(record);
        assertEquals(expResult, result);
    }

    /**
     * Test of Delete method, of class TradingShareHandler.
     */
    @Test
    public void testDelete() {
        System.out.println("Delete");
        TradingShareHandler instance = new TradingShareHandler();
        instance.Create(record);
        boolean expResult = true;
        boolean result = instance.Delete(record);
        assertEquals(expResult, result);
    }

    /**
     * Test of Get method, of class TradingShareHandler.
     */
    @Test
    public void testGet_LocalDateTime() {
        System.out.println("Get");
        LocalDateTime transaction_date = record.getTransaction_date();
        TradingShareHandler instance = new TradingShareHandler();
        instance.Create(record); 
        TradingShare expResult = record;
        List<TradingShare> result = instance.Get(transaction_date);
        instance.Delete(record); 
        assertEquals(expResult.getTransaction_date(), result.get(0).getTransaction_date());
    }

    /**
     * Test of BulkInsert method, of class TradingIndexHandler.
     */
    @Test
    public void testBulkInsert() {
        System.out.println("BulkInsert");
        String filename = "C:\\Temp\\trading_shares001.csv";
        TradingShareHandler instance = new TradingShareHandler();
        try {
            instance.BulkInsert(filename); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        int expResult = 100000;
        List<TradingShare> records = instance.GetAll(); 
        int result = records.size();
        assertEquals(expResult, result);
    }
        
}
