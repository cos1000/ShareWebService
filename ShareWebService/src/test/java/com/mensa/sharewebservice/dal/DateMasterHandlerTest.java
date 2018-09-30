/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dal;

import com.mensa.sharewebservice.entity.DateMaster;
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
public class DateMasterHandlerTest {
    public DateMaster record = new DateMaster(); 

    public DateMasterHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        record = new DateMaster(); 
        record.setDate_code(LocalDateTime.now());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Exist method, of class DateMasterHandler.
     */
    @Test
    public void testExist() {
        System.out.println("Exist");
        DateMasterHandler instance = new DateMasterHandler();
        instance.Create(record); 
        boolean expResult = true;
        boolean result = instance.Exist(record);
        instance.Delete(record);
        assertEquals(expResult, result);
    }

    /**
     * Test of Get method, of class DateMasterHandler.
     */
    @Test
    public void testGet() {
        System.out.println("Get");
        DateMasterHandler instance = new DateMasterHandler();
        instance.Create(record); 
        DateMaster expResult = record;
        DateMaster result = instance.Get(record);
        instance.Delete(record);
        assertEquals(expResult.getDate_code(), result.getDate_code());
    }

    /**
     * Test of Create method, of class DateMasterHandler.
     */
    @Test
    public void testCreate() {
        System.out.println("Create");
        DateMasterHandler instance = new DateMasterHandler();
        DateMaster expResult = record;
        DateMaster result = instance.CreateAndReturn(record);
        instance.Delete(record);
        assertEquals(expResult.getDate_code(), result.getDate_code());
    }

    /**
     * Test of Update method, of class DateMasterHandler.
     */
    @Test
    public void testUpdate() {
        System.out.println("Update");
        DateMasterHandler instance = new DateMasterHandler();
        instance.Create(record); 
        boolean expResult = true;
        boolean result = instance.Update(record);
        instance.Delete(record);
        assertEquals(expResult, result);
    }

    /**
     * Test of Delete method, of class DateMasterHandler.
     */
    @Test
    public void testDelete() {
        System.out.println("Delete");
        DateMasterHandler instance = new DateMasterHandler();
        instance.Create(record); 
        boolean expResult = true;
        boolean result = instance.Delete(record);
        assertEquals(expResult, result);
    }
    
}
