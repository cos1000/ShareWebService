/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dal;

import com.mensa.sharewebservice.entity.TradingIndex;
import java.time.LocalDateTime;
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
public class TradingIndexHandlerTest {
    public TradingIndex record = new TradingIndex(); 
    
    public TradingIndexHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        record = new TradingIndex(); 
        record.setTransaction_date(LocalDateTime.now());
        record.setIndex(1);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Exist method, of class TradingIndexHandler.
     */
    @Test
    public void testExist() {
        System.out.println("Exist");
        TradingIndexHandler instance = new TradingIndexHandler();
        instance.Create(record);
        boolean expResult = true;
        boolean result = instance.Exist(record);
        instance.Delete(record);
        assertEquals(expResult, result);
    }

    /**
     * Test of Get method, of class TradingIndexHandler.
     */
    @Test
    public void testGet() {
        System.out.println("Get");
        TradingIndexHandler instance = new TradingIndexHandler();
        instance.Create(record);
        TradingIndex expResult = record;
        TradingIndex result = instance.Get(record);
        instance.Delete(record);
        assertEquals(expResult.getTransaction_date(), result.getTransaction_date());
    }

    /**
     * Test of Create method, of class TradingIndexHandler.
     */
    @Test
    public void testCreate() {
        System.out.println("Create");
        TradingIndexHandler instance = new TradingIndexHandler();
        TradingIndex expResult = record;
        TradingIndex result = instance.CreateAndReturn(record);
        instance.Delete(record);
        assertEquals(expResult.getTransaction_date(), result.getTransaction_date());
    }

    /**
     * Test of Update method, of class TradingIndexHandler.
     */
    @Test
    public void testUpdate() {
        System.out.println("Update");
        TradingIndexHandler instance = new TradingIndexHandler();
        instance.Create(record);
        boolean expResult = true;
        boolean result = instance.Update(record);
        instance.Delete(record);
        assertEquals(expResult, result);
    }

    /**
     * Test of Delete method, of class TradingIndexHandler.
     */
    @Test
    public void testDelete() {
        System.out.println("Delete");
        TradingIndexHandler instance = new TradingIndexHandler();
        instance.Create(record);
        boolean expResult = true;
        boolean result = instance.Delete(record);
        assertEquals(expResult, result);
    }

    /**
     * Test of Get method, of class TradingIndexHandler.
     */
    @Test
    public void testGet_LocalDateTime() {
        System.out.println("Get");
        LocalDateTime transaction_date = record.getTransaction_date();
        TradingIndexHandler instance = new TradingIndexHandler();
        instance.Create(record); 
        TradingIndex expResult = record;
        List<TradingIndex> result = instance.Get(transaction_date);
        instance.Delete(record); 
        assertEquals(expResult.getTransaction_date(), result.get(0).getTransaction_date());
    }
    
}
