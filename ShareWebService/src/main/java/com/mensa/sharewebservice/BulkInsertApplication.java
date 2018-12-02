/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice;

import com.mensa.sharewebservice.dao.RecordHandler;
import com.mensa.sharewebservice.dao.TradingShareHandler;
import com.mensa.sharewebservice.util.Common;
import com.mensa.sharewebservice.util.CommonEnum;
import com.mensa.sharewebservice.util.EmailHandler;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matt_
 */
public class BulkInsertApplication {
    private static Logger log = LoggerFactory.getLogger(BulkInsertApplication.class);
    private static boolean isProcessing = false; 
    
    public static void main(String[] args) {
        System.out.println("Starting");
        BulkInsertApplication application = new BulkInsertApplication(); 
        
        //application.bulkInsertByThread(LocalDateTime.of(2011, 8, 2, 0, 0, 0), LocalDateTime.of(2011, 8, 2, 0, 0, 0));


        if (application.bulkInsert(LocalDateTime.of(2011, 8, 8, 0, 0, 0), LocalDateTime.of(2011, 8, 31, 0, 0, 0))) {
            System.out.println("Reture true;");
        } else {
            System.out.println("Return false;");
        }

        
        System.out.println("Completed");
        System.exit(0);
    }
    
    public void bulkInsertByThread(LocalDateTime fromDate, LocalDateTime toDate) {
        Thread thread = new Thread(new BulkInsertThread(fromDate, toDate)); 
        thread.start();
    }
    
    public boolean bulkInsert(LocalDateTime fromDate, LocalDateTime toDate) {
        boolean answer = true;
        String filename; 
        String filepath = Common.getProperty(CommonEnum.PropertyKeys.DefaultBulkInsertPath); 
        DateTimeFormatter formatterYear = DateTimeFormatter.ofPattern("yyyy"); 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd"); 
        TradingShareHandler handler = new TradingShareHandler(); 
        LocalDateTime currentDate = fromDate; 
        HashMap<LocalDateTime, Integer> resultList = new HashMap<LocalDateTime, Integer>(); 
        int insertedRecordCount = 0; 
        
        while (answer && !currentDate.isAfter(toDate)) {
            filename = filepath + currentDate.format(formatterYear) + "\\" + "d" + currentDate.format(formatter) + "e.txt"; 
            log.info("Starting bulk insert - " + filename);
            File file = new File(filename); 
            if (file.exists()) {
                try {
                    RecordReader reader = new RecordReader(); 
                    answer = reader.readFromFile(filename); 
                    if (answer) {
                        insertedRecordCount = handler.Get(currentDate).size(); 
                        resultList.put(currentDate, insertedRecordCount); 
                    } else {
                        log.trace("Return Fail - " + filename);
                    }
                } catch (Exception e) {
                    answer = false; 
                    log.error("Bulk Insert Error: " + filename);
                    log.error(e.getMessage());
                }
            } else {
                log.trace("File Not Found - " + filename);
            }
            currentDate = currentDate.plusDays(1); 
        }
        
        String subject = "Share Bulk Insert: " + fromDate.format(formatter) + " - " + toDate.format(formatter);
        StringBuilder content = new StringBuilder();
        
        for(Map.Entry<LocalDateTime, Integer> entry : resultList.entrySet()) {
            LocalDateTime key = entry.getKey();
            Integer value = entry.getValue();
            content.append("<h3>").append(key.format(formatter)).append(" : ").append(value.toString()).append("</h3>"); 
        }
        EmailHandler.SendEmail(subject, content.toString());
        return answer; 
    }
    
    private class BulkInsertThread implements Runnable {
        private final LocalDateTime fromDate; 
        private final LocalDateTime toDate; 

        public BulkInsertThread(LocalDateTime fromDate, LocalDateTime toDate) {
            this.fromDate = fromDate; 
            this.toDate = toDate; 
        }
        
        @Override
        public void run() {
            if (!BulkInsertApplication.isProcessing) {
                BulkInsertApplication.isProcessing = true; 
                try {
                    BulkInsertApplication application = new BulkInsertApplication(); 
                    application.bulkInsert(fromDate, toDate); 
                    log.info("Bulk Insert Completed");
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
                BulkInsertApplication.isProcessing = false; 
            }
        }
        
    }
    
}
