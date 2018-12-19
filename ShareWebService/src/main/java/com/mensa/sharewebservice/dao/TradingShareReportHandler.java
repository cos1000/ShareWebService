/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dao;

import com.mensa.sharewebservice.model.TradingShareReport;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import javax.persistence.LockModeType;

/**
 *
 * @author matt_
 */
public class TradingShareReportHandler implements IHandler<TradingShareReport> {
    private RecordHandler<TradingShareReport> handler;
    
    public TradingShareReportHandler() {
        handler = new RecordHandler<TradingShareReport>(); 
    }
    
    @Override
    public boolean Exist(TradingShareReport record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<TradingShareReport> GetAll() {
        String sql = "select record from TradingShareReport record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public TradingShareReport Get(TradingShareReport record) {
        String sql = "select record from TradingShareReport record where record.transaction_date = :transaction_date";
        List<TradingShareReport> answer = handler.GetQuery(sql).setParameter("transaction_date", record.getTransaction_date()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public TradingShareReport GetById(int id) {
        String sql = "select record from TradingShareReport record where record.id = :id";
        List<TradingShareReport> answer = handler.GetQuery(sql).setParameter("id", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public TradingShareReport CreateAndReturn(TradingShareReport record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(TradingShareReport record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(TradingShareReport record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(TradingShareReport record) {
        return handler.Delete(record); 
    }

    
}
