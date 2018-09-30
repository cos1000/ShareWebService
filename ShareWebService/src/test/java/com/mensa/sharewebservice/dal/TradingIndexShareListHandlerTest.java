/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dal;

import com.mensa.sharewebservice.entity.TradingIndexShareList;
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
public class TradingIndexShareListHandlerTest {
    public TradingIndexShareList record = new TradingIndexShareList(); 
    
    public TradingIndexShareListHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        record = new TradingIndexShareList(); 
        record.setShare_id(1);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Exist method, of class TradingIndexShareListHandler.
     */
    @Test
    public void testExist() {
        System.out.println("Exist");
        TradingIndexShareListHandler instance = new TradingIndexShareListHandler();
        instance.Create(record);
        boolean expResult = true;
        boolean result = instance.Exist(record);
        instance.Delete(record);
        assertEquals(expResult, result);
    }

    /**
     * Test of Get method, of class TradingIndexShareListHandler.
     */
    @Test
    public void testGet() {
        System.out.println("Get");
        TradingIndexShareListHandler instance = new TradingIndexShareListHandler();
        instance.Create(record);
        TradingIndexShareList expResult = record;
        TradingIndexShareList result = instance.Get(record);
        instance.Delete(record);
        assertEquals(expResult.getShare_id(), result.getShare_id());
    }

    /**
     * Test of Create method, of class TradingIndexShareListHandler.
     */
    @Test
    public void testCreate() {
        System.out.println("Create");
        TradingIndexShareListHandler instance = new TradingIndexShareListHandler();
        TradingIndexShareList expResult = record;
        TradingIndexShareList result = instance.CreateAndReturn(record);
        instance.Delete(record);
        assertEquals(expResult.getShare_id(), result.getShare_id());
    }

    /**
     * Test of Update method, of class TradingIndexShareListHandler.
     */
    @Test
    public void testUpdate() {
        System.out.println("Update");
        TradingIndexShareListHandler instance = new TradingIndexShareListHandler();
        instance.Create(record);
        boolean expResult = true;
        boolean result = instance.Update(record);
        instance.Delete(record);
        assertEquals(expResult, result);
    }

    /**
     * Test of Delete method, of class TradingIndexShareListHandler.
     */
    @Test
    public void testDelete() {
        System.out.println("Delete");
        TradingIndexShareListHandler instance = new TradingIndexShareListHandler();
        instance.Create(record);
        boolean expResult = true;
        boolean result = instance.Delete(record);
        assertEquals(expResult, result);
    }
    
}
