/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author matt_
 */
public class DownloaderTest {
    
    public DownloaderTest() {
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
     * Test of GetResponseFromUrl method, of class Downloader.
     */
    @Test
    public void testGetResponseFromUrl() throws Exception {
        System.out.println("GetResponseFromUrl");
        Downloader instance = new Downloader();
        String expResult = "";
        String result = instance.GetResponseFromUrl();
        //System.out.println(String.format("testGetResponseFromUrl Result: %s", result));
        assertNotEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testConvertXmlFromString() throws Exception {
        System.out.println("ConvertXmlFromString");
        Downloader instance = new Downloader();
        String xmlString = "<Html><Head>Testing</Head></Html>";
        Document result = instance.ConvertXmlFromString(xmlString);
        //System.out.println(String.format("testGetResponseFromUrl Result: %s", result));
        assertNotNull(result);
    }
}