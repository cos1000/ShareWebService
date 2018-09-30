/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.util;

import java.io.File;
import java.math.BigDecimal;
import java.util.Properties;
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
public class CommonTest {
    
    public CommonTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getProperties method, of class Common.
     */
    @Test
    public void testGetProperties() {
        System.out.println("getProperties");
        Properties expResult = null;
        Properties result = Common.getProperties();
        assertNotEquals(expResult, result);
    }

    /**
     * Test of getProperty method, of class Common.
     */
    @Test
    public void testGetProperty_CommonEnumPropertyKeys() {
        System.out.println("getProperty");
        CommonEnum.PropertyKeys key = CommonEnum.PropertyKeys.DefaultSavePath;
        String expResult = "C:\\Temp\\";
        String result = Common.getProperty(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of getProperty method, of class Common.
     */
    @Test
    public void testGetProperty_String() {
        System.out.println("getProperty");
        String key = "DefaultSavePath";
        String expResult = "C:\\Temp\\";
        String result = Common.getProperty(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of TryParse method, of class Common.
     */
    @Test
    public void testTryParse_String_Integer() {
        System.out.println("TryParse");
        String value = "1234";
        Integer expResult = 1234;
        Integer result = Common.TryParseToInteger(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of TryParse method, of class Common.
     */
    @Test
    public void testTryParse_String_Long() {
        System.out.println("TryParse");
        String value = "1234";
        Long expResult = 1234L;
        Long result = Common.TryParseToLong(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of TryParse method, of class Common.
     */
    @Test
    public void testTryParse_String_Double() {
        System.out.println("TryParse");
        String value = "1234.56";
        Double expResult = 1234.56D;
        Double result = Common.TryParseToDouble(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of TryParse method, of class Common.
     */
    @Test
    public void testTryParse_String_BigDecimal() {
        System.out.println("TryParse");
        String value = "1234.56";
        BigDecimal expResult = BigDecimal.valueOf(1234.56D);
        BigDecimal result = Common.TryParseToDecimal(value);
        assertEquals(expResult, result);
    }
    
}
