/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.service;

import com.mensa.sharewebservice.BulkInsertApplication;
import com.mensa.sharewebservice.dao.DateMasterHandler;
import com.mensa.sharewebservice.model.DateMaster;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author matt_
 */
@Service
public class DateMasterServiceImpl implements IService<DateMaster> {
    private static Logger log = LoggerFactory.getLogger(TradingShareServiceImpl.class);
    private DateMasterHandler recordHandler;

    public static void main(String[] args) {
        System.out.println("Starting");
        DateMasterServiceImpl service = new DateMasterServiceImpl(); 
        service.init();
        System.out.println("Completed");
        System.exit(0);
    }
    
    public void setTradingShareHandler(DateMasterHandler handler)
    {
        this.recordHandler = handler; 
    }
    
    @Override
    public boolean add(DateMaster record) {
        return recordHandler.Create(record); 
    }

    @Override
    public boolean update(DateMaster record) {
        return recordHandler.Update(record); 
    }

    @Override
    public List<DateMaster> list() {
        return recordHandler.GetAll(); 
    }

    @Override
    public DateMaster get(DateMaster record) {
        return recordHandler.Get(record); 
    }

    @Override
    public DateMaster getById(int id) {
        return recordHandler.GetById(id); 
    }

    @Override
    public boolean delete(int id) {
        return recordHandler.Delete(getById(id)); 
    }
    
    public void init() {
        LocalDateTime fromDate = LocalDateTime.of(2011, 8, 1, 0, 0, 0); 
        LocalDateTime toDate = LocalDateTime.of(2030, 12, 31, 0, 0, 0); 
        DateMaster maxRecord = recordHandler.GetMax(); 
        if (maxRecord != null) fromDate = maxRecord.getDate_code().plusDays(1); 
        if (fromDate.compareTo(toDate) > 0) toDate = fromDate.plusYears(10); 
        
        LocalDateTime currentDate = fromDate; 
        while (currentDate.compareTo(toDate) <= 0) {
            DateMaster record = new DateMaster(); 
            record.setDate_code(currentDate);
            record.setCreated_at(LocalDateTime.now());
            record.setUpdated_at(LocalDateTime.now());
            
            recordHandler.Create(record); 
            currentDate = currentDate.plusDays(1); 
        }
        
    }
    
}
