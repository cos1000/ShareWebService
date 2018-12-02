/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dao;

import com.mensa.sharewebservice.dao.DateMasterHandler;
import com.mensa.sharewebservice.model.DateMaster;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        DateMasterHandler instance = new DateMasterHandler();
        
        DateMaster testing01 = new DateMaster(); 
        testing01.setDate_code(LocalDateTime.of(2000, 1, 1, 0, 0));
        DateMaster testing02 = new DateMaster(); 
        testing02.setDate_code(LocalDateTime.of(2000, 1, 2, 0, 0));
        
        if (instance.Exist(testing01)) instance.Delete(testing01);
        if (instance.Exist(testing02)) instance.Delete(testing02);
        int expResult = 2;
        instance.Create(testing01);
        instance.Create(testing02); 
        int result = instance.GetAll().size(); 
        instance.Delete(testing01);
        instance.Delete(testing02); 
        assertTrue(expResult <= result);
    }

    /**
     * Test of Get method, of class DateMasterHandler.
     */
    @Test
    public void testGet() {
        System.out.println("Get");
        DateMasterHandler instance = new DateMasterHandler();
        if (instance.Exist(record)) instance.Delete(record);
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
        if (instance.Exist(record)) instance.Delete(record);
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
        if (instance.Exist(record)) instance.Delete(record);
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
        if (instance.Exist(record)) instance.Delete(record);
        instance.Create(record); 
        boolean expResult = true;
        boolean result = instance.Delete(record);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of BulkInsert method, of class TradingIndexHandler.
     */
    @Test
    public void testBulkInsert() {
        System.out.println("BulkInsert");
        String filename = "C:\\Temp\\date_master.csv";
        DateMasterHandler instance = new DateMasterHandler();

        List<DateMaster> records = instance.GetAll(); 
        
        for (DateMaster item : records) {
            instance.Delete(item); 
        }

        try {
            instance.BulkInsert(filename); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        int expResult = 3653;
        records = instance.GetAll(); 
        int result = records.size();
        
        for (DateMaster item : records) {
            instance.Delete(item); 
        }

        assertEquals(expResult, result);
    }
    
}
