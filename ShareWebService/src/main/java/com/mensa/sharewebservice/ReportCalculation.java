/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice;

import com.mensa.sharewebservice.dao.RecordHandler;
import com.mensa.sharewebservice.dao.TradingIndexHandler;
import com.mensa.sharewebservice.dao.TradingShareHandler;
import com.mensa.sharewebservice.model.TradingIndex;
import com.mensa.sharewebservice.model.TradingReport;
import com.mensa.sharewebservice.model.TradingShareReport;
import com.mensa.sharewebservice.model.TradingShare;
import com.mensa.sharewebservice.util.Common;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matt_
 */
public class ReportCalculation {
    private static Logger log = LoggerFactory.getLogger(ReportCalculation.class);
    private static boolean isProcessing = false; 
    
    public static void main(String[] args) {
        System.out.println("Starting");
        ReportCalculation calculation = new ReportCalculation(); 
        //LocalDateTime fromDate = LocalDateTime.of(2011, 8, 1, 0, 0, 0); 
        LocalDateTime fromDate = LocalDateTime.of(2014, 6, 26, 0, 0, 0); 
        //LocalDateTime toDate = LocalDateTime.of(2011, 12, 31, 0, 0, 0); 
        LocalDateTime toDate = LocalDateTime.of(2018, 12, 31,  0, 0, 0); 
        LocalDateTime currentDate = fromDate; 
        
//        TradingIndexHandler tradingIndexHandler = new TradingIndexHandler(); 
//        while (currentDate.compareTo(toDate) <= 0) {            
//            List<TradingIndex> tradingIndexList = tradingIndexHandler.Get(currentDate); 
//            for (TradingIndex tradingIndex : tradingIndexList) {
//                Long noOfRecord = tradingIndexHandler.GetNoOfRecord(tradingIndex); 
//                tradingIndex = calculation.updateIndexSequence(tradingIndex, BigDecimal.valueOf(noOfRecord)); 
//                tradingIndexHandler.Update(tradingIndex); 
//            }
//            currentDate = currentDate.plusDays(1); 
//            System.out.println(currentDate.toString()); 
//        }
        
        TradingShareHandler tradingShareHandler = new TradingShareHandler(); 
        while (currentDate.compareTo(toDate) <= 0) {
            System.out.println(LocalDateTime.now().toString()); 
            List<TradingShare> tradingShareList = tradingShareHandler.Get(currentDate); 
            for (TradingShare tradingShare : tradingShareList) {
                Long noOfRecord = tradingShareHandler.GetNoOfRecord(tradingShare); 
                TradingShare lastTradingShare = new TradingShare(); 
                lastTradingShare.setTransaction_date(currentDate.plusDays(-1));
                lastTradingShare.setShare_id(tradingShare.getShare_id());
                lastTradingShare = tradingShareHandler.Get(lastTradingShare); 
                
                tradingShare = calculation.updateSequence(tradingShare, noOfRecord.intValue()); 
                tradingShare = calculation.updateIs_ex_dividend_date(tradingShare, lastTradingShare); 
                
                tradingShareHandler.Update(tradingShare); 
            }
            currentDate = currentDate.plusDays(1); 
            RecordHandler.getEntityManager().clear(); 
            System.out.println(LocalDateTime.now().toString()); 
            System.out.println(currentDate.toString()); 
        }
        
        System.out.println("Completed");
        System.exit(0);
    }
    
    private List<TradingShare> getTradingShare(List<TradingShare> sources, LocalDateTime transaction_date, long noOfRecords) { 
        if (sources == null) return null; 
        return sources.stream()
                .filter(func -> func.getTransaction_date().compareTo(transaction_date) <= 0)
                .sorted(Comparator.comparing(TradingShare::getSequence_number).reversed())
                .limit(noOfRecords).collect(Collectors.toList()); 
    }
    
    private List<TradingShare> getTradingShare(List<TradingShare> sources, long noOfRecords) { 
        if (sources == null) return null; 
        return sources.stream()
                .sorted(Comparator.comparing(TradingShare::getTransaction_date).reversed())
                .limit(noOfRecords).collect(Collectors.toList()); 
    }
    
    private Integer getIsHigh(TradingShare source, List<TradingShare> sources) {
        List<TradingShare> checking = sources.stream().filter(func -> func.getHigh().compareTo(source.getHigh()) > 0).collect(Collectors.toList()); 
        return checking.isEmpty()? 1 : 0; 
    }
    
    private Integer getIsLow(TradingShare source, List<TradingShare> sources) {
        List<TradingShare> checking = sources.stream().filter(func -> func.getLow().compareTo(source.getLow()) < 0).collect(Collectors.toList()); 
        return checking.isEmpty()? 1 : 0; 
    }
    
    public TradingShare updateIs_ex_dividend_date(TradingShare tradingShare, TradingShare lastTradingShare) {
        if (tradingShare == null || lastTradingShare == null) return tradingShare; 
        if (tradingShare.getPrevious_closing() == null || lastTradingShare.getClosing() == null) return tradingShare; 
        if (tradingShare.getPrevious_closing() != lastTradingShare.getClosing()) tradingShare.setIs_ex_dividend_date(1);
        return tradingShare; 
    }
    
    public TradingShare updateSequence(TradingShare tradingShare, int NoOfRecord) {
        if (tradingShare == null) return tradingShare; 
        tradingShare.setSequence_number(NoOfRecord);
        return tradingShare; 
    }
    
    public TradingIndex updateIndexSequence(TradingIndex tradingIndex, BigDecimal NoOfRecord) {
        if (tradingIndex == null) return tradingIndex; 
        tradingIndex.setSequence_number(NoOfRecord);
        return tradingIndex; 
    }
    
    public TradingShareReport updateHighLow(TradingShareReport record, TradingShare source, List<TradingShare> sources) {
        if (record == null) return null; 
        if (source == null) return null; 
        if (sources == null) return null; 
        List<TradingShare> sourcesShort = getTradingShare(sources, source.getTransaction_date().plusDays(3), 7); 
        List<TradingShare> sourcesMedium = getTradingShare(sources, source.getTransaction_date().plusDays(10), 21); 
        List<TradingShare> sourcesLong = getTradingShare(sources, source.getTransaction_date().plusDays(30), 61); 
        record.setIs_short_high(getIsHigh(source, sourcesShort));
        record.setIs_short_low(getIsLow(source, sourcesShort));
        record.setIs_medium_high(getIsHigh(source, sourcesMedium));
        record.setIs_medium_low(getIsLow(source, sourcesMedium));
        record.setIs_long_high(getIsHigh(source, sourcesLong));
        record.setIs_long_low(getIsLow(source, sourcesLong));
        return record; 
    }
    
    public void CreateCalculateSequenceNumberThread() {
        Thread thread = new Thread(new CalculateSequenceNumberThread()); 
        thread.start();
    }
    
    private class CalculateSequenceNumberThread implements Runnable {

        public CalculateSequenceNumberThread() {
        }
        
        @Override
        public void run() {
            if (!ReportCalculation.isProcessing) {
                ReportCalculation.isProcessing = true; 
                try {
                    ReportCalculation calculation = new ReportCalculation(); 
                    TradingShareHandler tradingShareHandler = new TradingShareHandler(); 
                    LocalDateTime fromDate = tradingShareHandler.GetMaxSummaryDate(); 
                    LocalDateTime toDate = Common.GetDateWithoutTime(LocalDateTime.now()); 
                    LocalDateTime currentDate = fromDate; 
        
                    while (currentDate.compareTo(toDate) <= 0) {
                        log.info(LocalDateTime.now().toString()); 
                        List<TradingShare> tradingShareList = tradingShareHandler.Get(currentDate); 
                        for (TradingShare tradingShare : tradingShareList) {
                            Long noOfRecord = tradingShareHandler.GetNoOfRecord(tradingShare); 
                            TradingShare lastTradingShare = new TradingShare(); 
                            lastTradingShare.setTransaction_date(currentDate.plusDays(-1));
                            lastTradingShare.setShare_id(tradingShare.getShare_id());
                            lastTradingShare = tradingShareHandler.Get(lastTradingShare); 

                            tradingShare = calculation.updateSequence(tradingShare, noOfRecord.intValue()); 
                            tradingShare = calculation.updateIs_ex_dividend_date(tradingShare, lastTradingShare); 

                            tradingShareHandler.Update(tradingShare); 
                        }
                        currentDate = currentDate.plusDays(1); 
                        RecordHandler.getEntityManager().clear(); 
                        log.info(LocalDateTime.now().toString()); 
                        log.info(currentDate.toString()); 
                    }
                    
                    log.info("Calculate Sequence Number Completed");
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
                ReportCalculation.isProcessing = false; 
            }
        }
        
    }
    
}
