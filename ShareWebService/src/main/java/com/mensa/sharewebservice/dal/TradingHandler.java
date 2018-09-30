/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dal;

import com.mensa.sharewebservice.entity.Trading;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author matt_
 */
public class TradingHandler implements IHandler<Trading> {
    private RecordHandler<Trading> handler;
    
    public TradingHandler() {
        handler = new RecordHandler<Trading>(); 
    }
    
    @Override
    public boolean Exist(Trading record) {
        return Get(record) != null; 
    }
    
    @Override
    public Trading Get(Trading record) {
        String sql = "select record from Trading record where record.transaction_date = :transaction_date";
        List<Trading> answer = handler.GetQuery(sql).setParameter("transaction_date", record.getTransaction_date()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public Trading CreateAndReturn(Trading record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(Trading record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(Trading record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(Trading record) {
        return handler.Delete(record); 
    }

    public Trading GetMax() {
        String sql = "select max(record.transaction_date) from Trading record";
        LocalDateTime transaction_date = (LocalDateTime) handler.GetQuery(sql).getSingleResult(); 
        Trading record = new Trading(); 
        record.setTransaction_date(transaction_date);
        return Get(record); 
    }
    
}
