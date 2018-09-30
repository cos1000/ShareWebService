/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice;

import com.mensa.sharewebservice.util.FileHandler;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
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
public class RecordReaderTest {
    
    public RecordReaderTest() {
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
     * Test of readFromFile method, of class RecordReader.
     */
    @Test
    public void testReadFromFile() throws Exception {
        System.out.println("readFromFile");
        String filename = "C:\\Temp\\d180831e.txt";
        RecordReader instance = new RecordReader();
        boolean expResult = true;
        boolean result = instance.readFromFile(filename);
        instance.deleteOldRecords(LocalDateTime.of(2018, Month.AUGUST, 31, 0, 0)); 
        assertEquals(expResult, result);
    }

    /**
     * Test of readFromString method, of class RecordReader.
     */
    @Test
    public void testReadFromString() {
        System.out.println("readFromString");
        String filename = "C:\\Temp\\d180831e.txt";
        LocalDateTime transaction_date = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0);
        String records = "";
        try {
            records = FileHandler.ReadFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RecordReader instance = new RecordReader();
        boolean expResult = true;
        boolean result = instance.readFromString(transaction_date, records);
        instance.deleteOldRecords(transaction_date); 
        assertEquals(expResult, result);
    }

    /**
     * Test of readFromShareDetail method, of class RecordReader.
     */
    @Test
    public void testReadFromShareDetail() {
        System.out.println("readFromShareDetail");
        String filename = "C:\\Temp\\ShareDetail.txt";
        LocalDateTime transaction_date = LocalDateTime.of(2000, Month.FEBRUARY, 1, 0, 0);
        Integer share_id = 1;
        String shareDetail = "";
        try {
            shareDetail = FileHandler.ReadFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RecordReader instance = new RecordReader();
        boolean expResult = true;
        boolean result = instance.readFromShareDetail(transaction_date, share_id, shareDetail);
        instance.deleteOldRecords(transaction_date); 
        assertEquals(expResult, result);
    }

    /**
     * Test of removeFormatString method, of class RecordReader.
     */
    @Test
    public void testRemoveFormatString() {
        System.out.println("removeFormatString");
        String line = "</font></pre><pre><font size='1'>                               5.48     5.41     5.14            3,157,160";
        RecordReader instance = new RecordReader();
        String expResult = "                               5.48     5.41     5.14            3,157,160";
        String result = instance.removeFormatString(line);
        assertEquals(expResult, result);
    }

    /**
     * Test of writeTradingIndex method, of class RecordReader.
     */
    @Test
    public void testWriteTradingIndex() {
        System.out.println("writeTradingIndex");
        LocalDateTime transaction_date = LocalDateTime.of(2000, Month.MARCH, 1, 0, 0);
        Integer index = 1;
        String line = "                  HANG SENG INDEX   27906.59 27888.55 28164.05   -275.50  -0.978";
        RecordReader instance = new RecordReader();
        boolean expResult = true;
        boolean result = instance.writeTradingIndex(transaction_date, index, line);
        instance.deleteOldRecords(transaction_date); 
        assertEquals(expResult, result);
    }

    /**
     * Test of writeShareInformation method, of class RecordReader.
     */
    @Test
    public void testWriteShareInformation() {
        System.out.println("writeShareInformation");
        Integer share_id = 1;
        String name = "Testing";
        RecordReader instance = new RecordReader();
        boolean expResult = true;
        boolean result = instance.writeShareInformation(share_id, name);
        assertEquals(expResult, result);
    }

    /**
     * Test of writeTrading method, of class RecordReader.
     */
    @Test
    public void testWriteTrading() {
        System.out.println("writeTrading");
        LocalDateTime transaction_date = LocalDateTime.of(2000, Month.APRIL, 1, 0, 0);
        RecordReader instance = new RecordReader();
        boolean expResult = true;
        boolean result = instance.writeTrading(transaction_date);
        instance.deleteOldRecords(transaction_date); 
        assertEquals(expResult, result);
    }

    /**
     * Test of writeTradingShare method, of class RecordReader.
     */
    @Test
    public void testWriteTradingShare() {
        System.out.println("writeTradingShare");
        LocalDateTime transaction_date = LocalDateTime.of(2000, Month.MAY, 1, 0, 0);
        String above_line = "     1 CKH HOLDINGS     HKD   90.05    90.45    90.45            6,595,110";
        String line = "                              90.45    90.40    89.00          593,442,561";
        Boolean isFinished = false;
        RecordReader instance = new RecordReader();
        boolean expResult = true;
        boolean result = instance.writeTradingShare(transaction_date, above_line, line, isFinished);
        instance.deleteOldRecords(transaction_date); 
        assertEquals(expResult, result);
    }

    /**
     * Test of deleteOldRecords method, of class RecordReader.
     */
    @Test
    public void testDeleteOldRecords() {
        System.out.println("deleteOldRecords");
        LocalDateTime transaction_date = LocalDateTime.of(2000, Month.JULY, 1, 0, 0);
        RecordReader instance = new RecordReader();
        boolean expResult = true;
        boolean result = instance.deleteOldRecords(transaction_date);
        assertEquals(expResult, result);
    }
    
}
