/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dao;

import com.mensa.sharewebservice.model.TradingReport;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import javax.persistence.LockModeType;

/**
 *
 * @author matt_
 */
public class TradingReportHandler implements IHandler<TradingReport> {
    
    private RecordHandler<TradingReport> handler;
    
    public TradingReportHandler() {
        handler = new RecordHandler<TradingReport>(); 
    }
    
    @Override
    public boolean Exist(TradingReport record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<TradingReport> GetAll() {
        String sql = "select record from TradingReport record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public TradingReport Get(TradingReport record) {
        String sql = "select record from TradingReport record where record.transaction_date = :transaction_date";
        List<TradingReport> answer = handler.GetQuery(sql).setParameter("transaction_date", record.getTransaction_date()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public TradingReport GetById(int id) {
        String sql = "select record from TradingReport record where record.id = :id";
        List<TradingReport> answer = handler.GetQuery(sql).setParameter("id", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public TradingReport CreateAndReturn(TradingReport record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(TradingReport record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(TradingReport record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(TradingReport record) {
        return handler.Delete(record); 
    }
    
}
