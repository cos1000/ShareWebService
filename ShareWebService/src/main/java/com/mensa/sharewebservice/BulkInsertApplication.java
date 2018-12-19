/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice;

import com.mensa.sharewebservice.dao.RecordHandler;
import com.mensa.sharewebservice.dao.TradingHandler;
import com.mensa.sharewebservice.dao.TradingShareHandler;
import com.mensa.sharewebservice.model.Trading;
import com.mensa.sharewebservice.model.TradingShare;
import com.mensa.sharewebservice.util.Common;
import com.mensa.sharewebservice.util.CommonEnum;
import com.mensa.sharewebservice.util.EmailHandler;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
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
        
        //application.bulkInsertByThread(LocalDateTime.of(2011, 8, 1, 0, 0, 0), LocalDateTime.of(2011, 8, 1, 0, 0, 0), false);
        //application.bulkInsert(LocalDateTime.of(2012, 12, 24, 0, 0, 0), LocalDateTime.of(2012, 12, 24, 0, 0, 0), false);
        
        //application.insertSpecialDate(LocalDateTime.of(2012, 01, 16, 0, 0, 0), LocalDateTime.of(2012, 01, 17, 0, 0, 0), LocalDateTime.of(2012, 01, 18, 0, 0, 0));
        application.insertSpecialDate(LocalDateTime.of(2012, 06, 15, 0, 0, 0), LocalDateTime.of(2012, 06, 18, 0, 0, 0), LocalDateTime.of(2012, 06, 19, 0, 0, 0));
        application.insertSpecialDate(LocalDateTime.of(2012, 12, 31, 0, 0, 0), LocalDateTime.of(2013, 01, 02, 0, 0, 0), LocalDateTime.of(2013, 01, 03, 0, 0, 0));

//        if (application.bulkInsert(LocalDateTime.of(2011, 8, 8, 0, 0, 0), LocalDateTime.of(2011, 8, 31, 0, 0, 0), false)) {
//            System.out.println("Reture true;");
//        } else {
//            System.out.println("Return false;");
//        }

        
        System.out.println("Completed");
        System.exit(0);
    }
    
    public void bulkInsertByThread(LocalDateTime fromDate, LocalDateTime toDate, boolean sendEmail) {
        Thread thread = new Thread(new BulkInsertThread(fromDate, toDate, sendEmail)); 
        thread.start();
    }
    
    public boolean bulkInsert(LocalDateTime fromDate, LocalDateTime toDate, boolean sendEmail) {
        log.info(fromDate.toString());
        log.info(toDate.toString());
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
        
        if (sendEmail) {
            String subject = "Share Bulk Insert: " + fromDate.format(formatter) + " - " + toDate.format(formatter);
            StringBuilder content = new StringBuilder();

            for(Map.Entry<LocalDateTime, Integer> entry : resultList.entrySet()) {
                LocalDateTime key = entry.getKey();
                Integer value = entry.getValue();
                content.append("<h3>").append(key.format(formatter)).append(" : ").append(value.toString()).append("</h3>"); 
            }
            EmailHandler.SendEmail(subject, content.toString());
        }
        return answer; 
    }
    
    public void insertSpecialDate(LocalDateTime previousDate, LocalDateTime currentDate, LocalDateTime nextDate) {
        TradingShareHandler handler = new TradingShareHandler(); 
        List<TradingShare> previousRecords = handler.Get(previousDate); 
        List<TradingShare> currentRecords = new ArrayList<TradingShare>(); 
        
        for (TradingShare previousRecord : previousRecords) {
            TradingShare nextRecord = new TradingShare(); 
            nextRecord.setTransaction_date(nextDate);
            nextRecord.setShare_id(previousRecord.getShare_id());
            nextRecord = handler.Get(nextRecord); 
            
            TradingShare record = new TradingShare(); 
            record.setTransaction_date(currentDate);
            record.setShare_id(previousRecord.getShare_id());
            record.setPrevious_closing(previousRecord.getClosing());
            if (nextRecord != null) {
                record.setClosing(nextRecord.getPrevious_closing());
                record.setAsk(nextRecord.getPrevious_closing());
                record.setBid(nextRecord.getPrevious_closing());
                
                if (previousRecord.getClosing() == null) {
                    record.setHigh(previousRecord.getClosing());
                    record.setLow(previousRecord.getClosing());
                } else if (nextRecord.getPrevious_closing() == null) {
                    record.setHigh(nextRecord.getClosing());
                    record.setLow(nextRecord.getClosing());
                } else if (previousRecord.getClosing().compareTo(nextRecord.getPrevious_closing()) > 0) {
                    record.setHigh(previousRecord.getClosing());
                    record.setLow(nextRecord.getPrevious_closing());
                } else {
                    record.setHigh(nextRecord.getPrevious_closing());
                    record.setLow(previousRecord.getClosing());
                }
            }
            record.setIs_ex_dividend_date(previousRecord.getIs_ex_dividend_date());
            record.setIs_index_share(previousRecord.getIs_index_share());
            record.setCreated_at(LocalDateTime.now());
            record.setUpdated_at(LocalDateTime.now());
            
            currentRecords.add(record); 
        }
        
        for (TradingShare currentRecord : currentRecords) {
            handler.Create(currentRecord); 
        }

        TradingHandler tradingHandler = new TradingHandler(); 
        Trading trading = new Trading();
        trading.setTransaction_date(currentDate);
        trading.setCreated_at(LocalDateTime.now());
        trading.setUpdated_at(LocalDateTime.now());
        tradingHandler.Create(trading); 
    }
    
    private class BulkInsertThread implements Runnable {
        private final LocalDateTime fromDate; 
        private final LocalDateTime toDate; 
        private final boolean sendEmail; 

        public BulkInsertThread(LocalDateTime fromDate, LocalDateTime toDate, boolean sendEmail) {
            this.fromDate = fromDate; 
            this.toDate = toDate; 
            this.sendEmail = sendEmail; 
        }
        
        @Override
        public void run() {
            if (!BulkInsertApplication.isProcessing) {
                BulkInsertApplication.isProcessing = true; 
                try {
                    log.info(this.fromDate.toString());
                    log.info(this.toDate.toString());
                    BulkInsertApplication application = new BulkInsertApplication(); 
                    application.bulkInsert(this.fromDate, this.toDate, this.sendEmail); 
                    log.info("Bulk Insert Completed");
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
                BulkInsertApplication.isProcessing = false; 
            }
        }
        
    }
    
}
