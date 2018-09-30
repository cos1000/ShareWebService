/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dal;

import com.mensa.sharewebservice.entity.TradingIndex;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author matt_
 */
public class TradingIndexHandler  implements IHandler<TradingIndex> {
    private RecordHandler<TradingIndex> handler;
    
    public TradingIndexHandler() {
        handler = new RecordHandler<TradingIndex>(); 
    }
    
    @Override
    public boolean Exist(TradingIndex record) {
        return Get(record) != null; 
    }
    
    @Override
    public TradingIndex Get(TradingIndex record) {
        String sql = "select record from TradingIndex record where record.transaction_date = :transaction_date and record.index = :index";
        List<TradingIndex> answer = handler.GetQuery(sql).setParameter("transaction_date", record.getTransaction_date()).setParameter("index", record.getIndex()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public TradingIndex CreateAndReturn(TradingIndex record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(TradingIndex record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(TradingIndex record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(TradingIndex record) {
        return handler.Delete(record); 
    }

    public List<TradingIndex> Get(LocalDateTime transaction_date) {
        String sql = "select record from TradingIndex record where record.transaction_date = :transaction_date";
        return handler.GetQuery(sql).setParameter("transaction_date", transaction_date).getResultList(); 
    }
    
}
