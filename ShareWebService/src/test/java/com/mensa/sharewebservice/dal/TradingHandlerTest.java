/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dal;

import com.mensa.sharewebservice.entity.Trading;
import java.time.LocalDateTime;
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
public class TradingHandlerTest {
    public Trading record = new Trading(); 
    
    public TradingHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        record = new Trading(); 
        record.setTransaction_date(LocalDateTime.now());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Exist method, of class TradingHandler.
     */
    @Test
    public void testExist() {
        System.out.println("Exist");
        TradingHandler instance = new TradingHandler();
        instance.Create(record); 
        boolean expResult = true;
        boolean result = instance.Exist(record);
        instance.Delete(record);
        assertEquals(expResult, result);
    }

    /**
     * Test of Get method, of class TradingHandler.
     */
    @Test
    public void testGet() {
        System.out.println("Get");
        TradingHandler instance = new TradingHandler();
        instance.Create(record); 
        Trading expResult = record;
        Trading result = instance.Get(record);
        instance.Delete(record);
        assertEquals(expResult.getTransaction_date(), result.getTransaction_date());
    }

    /**
     * Test of Create method, of class TradingHandler.
     */
    @Test
    public void testCreate() {
        System.out.println("Create");
        TradingHandler instance = new TradingHandler();
        Trading expResult = record;
        Trading result = instance.CreateAndReturn(record);
        instance.Delete(record);
        assertEquals(expResult.getTransaction_date(), result.getTransaction_date());
    }

    /**
     * Test of Update method, of class TradingHandler.
     */
    @Test
    public void testUpdate() {
        System.out.println("Update");
        TradingHandler instance = new TradingHandler();
        instance.Create(record); 
        boolean expResult = true;
        boolean result = instance.Update(record);
        instance.Delete(record);
        assertEquals(expResult, result);
    }

    /**
     * Test of Delete method, of class TradingHandler.
     */
    @Test
    public void testDelete() {
        System.out.println("Delete");
        TradingHandler instance = new TradingHandler();
        instance.Create(record); 
        boolean expResult = true;
        boolean result = instance.Delete(record);
        assertEquals(expResult, result);
    }

    /**
     * Test of GetMax method, of class TradingHandler.
     */
    @Test
    public void testGetMax() {
        System.out.println("GetMax");
        TradingHandler instance = new TradingHandler();
        instance.Create(record); 
        Trading expResult = record;
        Trading result = instance.GetMax();
        instance.Delete(record);
        assertEquals(expResult.getTransaction_date(), result.getTransaction_date());
    }
    
}
