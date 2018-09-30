/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.util;

import java.time.LocalDateTime;
import java.time.Month;
import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;

/**
 *
 * @author matt_
 */
public class ConverterTest {
    
    public ConverterTest() {
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
     * Test of ConvertHtmlToString method, of class Converter.
     */
    @Test
    public void testConvertHtmlToString() throws Exception {
        System.out.println("ConvertHtmlToString");
        String expResult = "Testing\nTesting\n";
        //String xmlString = "<Html><body><div>Testing</div><br /><div>Testing</div></body></Html>";
        String xmlString = "<Html><body><font>Testing</font><br /><font>Testing</font></body></Html>";
        String result = Converter.ConvertHtmlToString(Converter.ConvertXmlFromString(xmlString));
        assertEquals(expResult, result);
    }

    /**
     * Test of ConvertHtmlToString method, of class Converter.
     */
    @Test
    public void testConvertHtmlToString2() throws Exception {
        System.out.println("ConvertHtmlToString");
        String filename = "C:\\Temp\\ShareSample.txt";
        String expResult = "";
        String xmlString = FileHandler.ReadFromFile(filename);
        String result = Converter.ConvertHtmlToString(Converter.ConvertXmlFromString(xmlString));
        assertNotEquals(expResult, result);
    }

    /**
     * Test of ConvertXmlFromString method, of class Converter.
     */
    @Test
    public void testConvertXmlFromString() throws Exception {
        System.out.println("ConvertXmlFromString");
        String xmlString = "<Html><Head>Testing</Head></Html>";
        Element result = Converter.ConvertXmlFromString(xmlString);
        assertNotNull(result);
    }

    /**
     * Test of ConvertXmlFromString method, of class Converter.
     */
    @Test
    public void testConvertXmlFromString2() throws Exception {
        System.out.println("ConvertXmlFromString");
        String filename = "C:\\Temp\\ShareSampleFormatted.txt";
        String xmlString = FileHandler.ReadFromFile(filename);
        Element result = Converter.ConvertXmlFromString(xmlString);
        assertNotNull(result);
    }

    /**
     * Test of ConvertDateFromFilename method, of class Converter.
     */
    @Test
    public void testConvertDateFromFilename() {
        System.out.println("ConvertDateFromFilename");
        String filename = "d180831e.txt";
        LocalDateTime expResult = LocalDateTime.of(2018, Month.AUGUST, 31, 0, 0);
        LocalDateTime result = Converter.ConvertDateFromFilename(filename);
        assertEquals(expResult, result);
    }

}
