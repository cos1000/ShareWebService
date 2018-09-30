/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dal;

import com.mensa.sharewebservice.entity.TradingShare;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author matt_
 */
public class TradingShareHandler  implements IHandler<TradingShare> {
    private RecordHandler<TradingShare> handler;
    
    public TradingShareHandler() {
        handler = new RecordHandler<TradingShare>(); 
    }
    
    @Override
    public boolean Exist(TradingShare record) {
        return Get(record) != null; 
    }
    
    @Override
    public TradingShare Get(TradingShare record) {
        String sql = "select record from TradingShare record where record.transaction_date = :transaction_date and record.share_id = :share_id";
        List<TradingShare> answer = handler.GetQuery(sql).setParameter("transaction_date", record.getTransaction_date()).setParameter("share_id", record.getShare_id()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public TradingShare CreateAndReturn(TradingShare record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(TradingShare record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(TradingShare record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(TradingShare record) {
        return handler.Delete(record); 
    }

    public List<TradingShare> Get(LocalDateTime transaction_date) {
        String sql = "select record from TradingShare record where record.transaction_date = :transaction_date";
        return handler.GetQuery(sql).setParameter("transaction_date", transaction_date).getResultList(); 
    }
}
