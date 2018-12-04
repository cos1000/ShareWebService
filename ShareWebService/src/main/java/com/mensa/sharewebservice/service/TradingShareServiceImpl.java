/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.service;

import com.mensa.sharewebservice.BulkInsertApplication;
import com.mensa.sharewebservice.dao.TradingHandler;
import com.mensa.sharewebservice.dao.TradingShareHandler;
import com.mensa.sharewebservice.model.Trading;
import com.mensa.sharewebservice.model.TradingShare;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author matt_
 */
@Service
public class TradingShareServiceImpl implements IService<TradingShare> {
    private TradingShareHandler recordHandler;
    
    public void setTradingShareHandler(TradingShareHandler handler)
    {
        this.recordHandler = handler; 
    }
    
    public List<TradingShare> get(LocalDateTime transaction_date)
    {
        return recordHandler.Get(transaction_date); 
    }
    
    @Override
    public boolean add(TradingShare record) {
        return recordHandler.Create(record); 
    }

    @Override
    public boolean update(TradingShare record) {
        return recordHandler.Update(record); 
    }

    @Override
    public List<TradingShare> list() {
        return recordHandler.GetAll(); 
    }

    @Override
    public TradingShare get(TradingShare record) {
        return recordHandler.Get(record); 
    }

    @Override
    public TradingShare getById(int id) {
        return recordHandler.GetById(id); 
    }

    @Override
    public boolean delete(int id) {
        return recordHandler.Delete(getById(id)); 
    }
    
    public Map<LocalDateTime, Long> summary() {
        return recordHandler.GetSummary(); 
    }

    public void insertFileByMonth(int year, int month) {
        BulkInsertApplication application = new BulkInsertApplication(); 
        LocalDateTime fromDate = LocalDateTime.of(year, month, 1, 0, 0, 0); 
        LocalDateTime toDate = fromDate.plusMonths(1).plusDays(-1); 
        application.bulkInsertByThread(fromDate, toDate); 
    }
    
    public void insertFileByMonth() {
        TradingHandler tradingHandler = new TradingHandler(); 
        Trading trading = tradingHandler.GetMax(); 
        BulkInsertApplication application = new BulkInsertApplication(); 
        LocalDateTime fromDate = LocalDateTime.of(2011, 8, 1, 0, 0, 0); 
        if (trading != null && trading.getTransaction_date() != null) fromDate = trading.getTransaction_date().plusDays(1); 
        LocalDateTime toDate = fromDate.plusMonths(1).plusDays(-1); 
        application.bulkInsertByThread(fromDate, toDate); 
    }
    
}
