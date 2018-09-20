/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.util;

import com.mensa.sharewebservice.Downloader;
import java.io.File;
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
public class FileHandlerTest {
    
    public FileHandlerTest() {
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
     * Test of SaveToFile method, of class FileHandler.
     */
    @Test
    public void testSaveToFile() throws Exception {
        System.out.println("Test SaveToFile");
        
        String filename = "C:\\Temp\\testing.txt";
        String xmlString = "<Html><Head>Testing</Head></Html>";
        Boolean result = FileHandler.SaveToFile(filename, xmlString);
        File file = new File(filename); 
        file.delete(); 
        assertNotNull(result);
    }
    
    /**
     * Test of ReadFromFile method, of class FileHandler.
     */
    @Test
    public void testReadFromFile() throws Exception {
        System.out.println("Test ReadFromFile");
        
        String filename = "C:\\Temp\\testing.txt";
        String xmlString = "<Html><Head>Testing</Head></Html>";
        String result = "";
        Boolean answer = FileHandler.SaveToFile(filename, xmlString);
        result = FileHandler.ReadFromFile(filename); 
        
        File file = new File(filename); 
        file.delete(); 
        assertEquals(xmlString, result);
    }
    
}
