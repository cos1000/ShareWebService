/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dao;

import com.mensa.sharewebservice.model.CandleStickType; 
import java.util.List;
import javax.persistence.LockModeType;

/**
 *
 * @author matt_
 */
public class CandleStickTypeHandler implements IHandler<CandleStickType> {
    private RecordHandler<CandleStickType> handler;
    
    public CandleStickTypeHandler() {
        handler = new RecordHandler<CandleStickType>(); 
    }
    
    @Override
    public boolean Exist(CandleStickType record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<CandleStickType> GetAll() {
        String sql = "select record from CandleStickType record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public CandleStickType Get(CandleStickType record) {
        String sql = "select record from CandleStickType record where record.name = :name";
        List<CandleStickType> answer = handler.GetQuery(sql).setParameter("name", record.getName()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public CandleStickType GetById(int id) {
        String sql = "select record from CandleStickType record where record.id = :id";
        List<CandleStickType> answer = handler.GetQuery(sql).setParameter("id", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public CandleStickType CreateAndReturn(CandleStickType record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(CandleStickType record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(CandleStickType record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(CandleStickType record) {
        return handler.Delete(record); 
    }
    
}
