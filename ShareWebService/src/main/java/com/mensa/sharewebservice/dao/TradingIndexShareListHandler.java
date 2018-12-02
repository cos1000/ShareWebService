/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dao;

import com.mensa.sharewebservice.model.TradingIndexShareList;
import java.util.List;
import javax.persistence.LockModeType;

/**
 *
 * @author matt_
 */
public class TradingIndexShareListHandler  implements IHandler<TradingIndexShareList> {
    private RecordHandler<TradingIndexShareList> handler;
    
    public TradingIndexShareListHandler() {
        handler = new RecordHandler<TradingIndexShareList>(); 
    }
    
    @Override
    public boolean Exist(TradingIndexShareList record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<TradingIndexShareList> GetAll() {
        String sql = "select record from TradingIndexShareList record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public TradingIndexShareList Get(TradingIndexShareList record) {
        String sql = "select record from TradingIndexShareList record where record.share_id = :share_id";
        List<TradingIndexShareList> answer = handler.GetQuery(sql).setParameter("share_id", record.getShare_id()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public TradingIndexShareList GetById(int id) {
        String sql = "select record from TradingIndexShareList record where record.id = :id";
        List<TradingIndexShareList> answer = handler.GetQuery(sql).setParameter("id", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public TradingIndexShareList CreateAndReturn(TradingIndexShareList record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(TradingIndexShareList record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(TradingIndexShareList record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(TradingIndexShareList record) {
        return handler.Delete(record); 
    }

}
