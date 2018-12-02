/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dao;

import com.mensa.sharewebservice.dao.ShareInformationHandler;
import com.mensa.sharewebservice.model.ShareInformation;
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
public class ShareInformationHandlerTest {
    public ShareInformation record = new ShareInformation(); 
    
    public ShareInformationHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        record = new ShareInformation(); 
        record.setShare_id(1);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Exist method, of class ShareInformationHandler.
     */
    @Test
    public void testExist() {
        System.out.println("Exist");
        ShareInformationHandler instance = new ShareInformationHandler();
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
        ShareInformationHandler instance = new ShareInformationHandler();
        
        ShareInformation testing01 = new ShareInformation(); 
        testing01.setShare_id(1);
        ShareInformation testing02 = new ShareInformation(); 
        testing02.setShare_id(2);
        
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
     * Test of Get method, of class ShareInformationHandler.
     */
    @Test
    public void testGet() {
        System.out.println("Get");
        ShareInformationHandler instance = new ShareInformationHandler();
        if (instance.Exist(record)) instance.Delete(record);
        instance.Create(record); 
        ShareInformation expResult = record;
        ShareInformation result = instance.Get(record);
        instance.Delete(record);
        assertEquals(expResult.getShare_id(), result.getShare_id());
    }

    /**
     * Test of Create method, of class ShareInformationHandler.
     */
    @Test
    public void testCreate() {
        System.out.println("Create");
        ShareInformationHandler instance = new ShareInformationHandler();
        if (instance.Exist(record)) instance.Delete(record);
        ShareInformation expResult = record;
        ShareInformation result = instance.CreateAndReturn(record);
        instance.Delete(record);
        assertEquals(expResult.getShare_id(), result.getShare_id());
    }

    /**
     * Test of Update method, of class ShareInformationHandler.
     */
    @Test
    public void testUpdate() {
        System.out.println("Update");
        ShareInformationHandler instance = new ShareInformationHandler();
        if (instance.Exist(record)) instance.Delete(record);
        instance.Create(record); 
        boolean expResult = true;
        boolean result = instance.Update(record);
        instance.Delete(record);
        assertEquals(expResult, result);
    }

    /**
     * Test of Delete method, of class ShareInformationHandler.
     */
    @Test
    public void testDelete() {
        System.out.println("Delete");
        ShareInformationHandler instance = new ShareInformationHandler();
        if (instance.Exist(record)) instance.Delete(record);
        instance.Create(record); 
        boolean expResult = true;
        boolean result = instance.Delete(record);
        assertEquals(expResult, result);
    }
    
}
