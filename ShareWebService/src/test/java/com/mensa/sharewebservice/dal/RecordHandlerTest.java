/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dal;

import java.util.List;
import javax.persistence.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.mensa.sharewebservice.entity.DateMaster; 
import java.time.LocalDateTime;

/**
 *
 * @author matt_
 */
public class RecordHandlerTest {
    private DateMaster record; 
    
    public RecordHandlerTest() {
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
        record.setCreated_at(LocalDateTime.now());
        record.setUpdated_at(LocalDateTime.now());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of CreateOrUpdate method, of class RecordHandler.
     */
    @Test
    public void testCreateOrUpdate() {
        System.out.println("CreateOrUpdate");
        RecordHandler instance = new RecordHandler();
        boolean expResult = true;
        boolean result = instance.CreateOrUpdate(record);
        instance.Delete(record); 
        assertEquals(expResult, result);
    }

    /**
     * Test of Delete method, of class RecordHandler.
     */
    @Test
    public void testDelete() {
        System.out.println("Delete");
        RecordHandler instance = new RecordHandler();
        instance.CreateOrUpdate(record);
        boolean expResult = true;
        boolean result = instance.Delete(record);
        assertEquals(expResult, result);
    }

    /**
     * Test of GetList method, of class RecordHandler.
     */
    @Test
    public void testGetList() {
        System.out.println("GetList");
        String sql = "select record from DateMaster record";
        RecordHandler instance = new RecordHandler();
        instance.CreateOrUpdate(record);
        int expResult = 1;
        List result = instance.GetList(sql);
        instance.Delete(record); 
        assertTrue(expResult <= result.size());
    }

    /**
     * Test of GetQuery method, of class RecordHandler.
     */
    @Test
    public void testGetQuery() {
        System.out.println("GetQuery");
        String sql = "select record from DateMaster record";
        RecordHandler instance = new RecordHandler();
        Query result = instance.GetQuery(sql);
        assertNotNull(result);
    }
    
}
