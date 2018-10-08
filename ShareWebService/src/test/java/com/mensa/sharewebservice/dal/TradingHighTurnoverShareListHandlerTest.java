/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dal;

import com.mensa.sharewebservice.entity.TradingHighTurnoverShareList;
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
public class TradingHighTurnoverShareListHandlerTest {
    public TradingHighTurnoverShareList record = new TradingHighTurnoverShareList(); 
    
    public TradingHighTurnoverShareListHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        record = new TradingHighTurnoverShareList(); 
        record.setShare_id(1);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Exist method, of class TradingHighTurnoverShareListHandler.
     */
    @Test
    public void testExist() {
        System.out.println("Exist");
        TradingHighTurnoverShareListHandler instance = new TradingHighTurnoverShareListHandler();
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
        TradingHighTurnoverShareListHandler instance = new TradingHighTurnoverShareListHandler();
        
        TradingHighTurnoverShareList testing01 = new TradingHighTurnoverShareList(); 
        testing01.setShare_id(3);
        TradingHighTurnoverShareList testing02 = new TradingHighTurnoverShareList(); 
        testing02.setShare_id(4);
        
        int expResult = 2;
        instance.Create(testing01);
        instance.Create(testing02); 
        int result = instance.GetAll().size(); 
        instance.Delete(testing01);
        instance.Delete(testing02); 
        assertEquals(expResult, result);
    }

    /**
     * Test of Get method, of class TradingHighTurnoverShareListHandler.
     */
    @Test
    public void testGet() {
        System.out.println("Get");
        TradingHighTurnoverShareListHandler instance = new TradingHighTurnoverShareListHandler();
        instance.Create(record); 
        TradingHighTurnoverShareList expResult = record;
        TradingHighTurnoverShareList result = instance.Get(record);
        instance.Delete(record);
        assertEquals(expResult.getShare_id(), result.getShare_id());
    }

    /**
     * Test of Create method, of class TradingHighTurnoverShareListHandler.
     */
    @Test
    public void testCreate() {
        System.out.println("Create");
        TradingHighTurnoverShareListHandler instance = new TradingHighTurnoverShareListHandler();
        TradingHighTurnoverShareList expResult = record;
        TradingHighTurnoverShareList result = instance.CreateAndReturn(record);
        instance.Delete(record);
        assertEquals(expResult.getShare_id(), result.getShare_id());
    }

    /**
     * Test of Update method, of class TradingHighTurnoverShareListHandler.
     */
    @Test
    public void testUpdate() {
        System.out.println("Update");
        TradingHighTurnoverShareListHandler instance = new TradingHighTurnoverShareListHandler();
        instance.Create(record); 
        boolean expResult = true;
        boolean result = instance.Update(record);
        instance.Delete(record);
        assertEquals(expResult, result);
    }

    /**
     * Test of Delete method, of class TradingHighTurnoverShareListHandler.
     */
    @Test
    public void testDelete() {
        System.out.println("Delete");
        TradingHighTurnoverShareListHandler instance = new TradingHighTurnoverShareListHandler();
        instance.Create(record);
        boolean expResult = true;
        boolean result = instance.Delete(record);
        assertEquals(expResult, result);
    }
    
}
