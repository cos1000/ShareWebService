/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dao;

import com.mensa.sharewebservice.dao.TradingHandler;
import com.mensa.sharewebservice.model.Trading;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        if (instance.Exist(record)) instance.Delete(record);
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
        TradingHandler instance = new TradingHandler();
        
        Trading testing01 = new Trading(); 
        testing01.setTransaction_date(LocalDateTime.of(2000, 1, 1, 0, 0));
        Trading testing02 = new Trading(); 
        testing02.setTransaction_date(LocalDateTime.of(2000, 1, 2, 0, 0));
        
        if (instance.Exist(testing01)) instance.Delete(testing01);
        if (instance.Exist(testing02)) instance.Delete(testing02);
        int expResult = 2;
        instance.Create(testing01);
        instance.Create(testing02); 
        int result = instance.GetAll().size(); 
        instance.Delete(testing01);
        instance.Delete(testing02); 
        assertEquals(expResult, result);
    }

    /**
     * Test of Get method, of class TradingHandler.
     */
    @Test
    public void testGet() {
        System.out.println("Get");
        TradingHandler instance = new TradingHandler();
        if (instance.Exist(record)) instance.Delete(record);
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
        if (instance.Exist(record)) instance.Delete(record);
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
        if (instance.Exist(record)) instance.Delete(record);
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
        if (instance.Exist(record)) instance.Delete(record);
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
        if (instance.Exist(record)) instance.Delete(record);
        instance.Create(record); 
        Trading expResult = record;
        Trading result = instance.GetMax();
        instance.Delete(record);
        assertEquals(expResult.getTransaction_date(), result.getTransaction_date());
    }
    
}
