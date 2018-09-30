/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dal;

import com.mensa.sharewebservice.entity.CandleStickType;
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
public class CandleStickTypeHandlerTest {
    public CandleStickType record = new CandleStickType(); 
    
    public CandleStickTypeHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        record = new CandleStickType(); 
        record.setName("Testing");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Exist method, of class CandleStickTypeHandler.
     */
    @Test
    public void testExist() {
        System.out.println("Exist");
        CandleStickTypeHandler instance = new CandleStickTypeHandler();
        instance.CreateAndReturn(record); 
        boolean expResult = true;
        boolean result = instance.Exist(record);
        instance.Delete(instance.Get(record)); 
        assertEquals(expResult, result);
    }

    /**
     * Test of Get method, of class CandleStickTypeHandler.
     */
    @Test
    public void testGet() {
        System.out.println("Get");
        CandleStickTypeHandler instance = new CandleStickTypeHandler();
        instance.Create(record); 
        CandleStickType expResult = record;
        CandleStickType result = instance.Get(record);
        instance.Delete(instance.Get(record)); 
        assertEquals(expResult.getName(), result.getName());
    }

    /**
     * Test of Create method, of class CandleStickTypeHandler.
     */
    @Test
    public void testCreate() {
        System.out.println("Create");
        CandleStickTypeHandler instance = new CandleStickTypeHandler();
        CandleStickType expResult = record;
        CandleStickType result = instance.CreateAndReturn(record);
        instance.Delete(instance.Get(record)); 
        assertEquals(expResult.getName(), result.getName());
    }

    /**
     * Test of Update method, of class CandleStickTypeHandler.
     */
    @Test
    public void testUpdate() {
        System.out.println("Update");
        CandleStickTypeHandler instance = new CandleStickTypeHandler();
        instance.Create(record);
        record = instance.Get(record); 
        boolean expResult = true;
        boolean result = instance.Update(record);
        instance.Delete(instance.Get(record)); 
        assertEquals(expResult, result);
    }

    /**
     * Test of Delete method, of class CandleStickTypeHandler.
     */
    @Test
    public void testDelete() {
        System.out.println("Delete");
        CandleStickTypeHandler instance = new CandleStickTypeHandler();
        instance.Create(record);
        boolean expResult = true;
        boolean result = instance.Delete(record);
        assertEquals(expResult, result);
    }
    
}
