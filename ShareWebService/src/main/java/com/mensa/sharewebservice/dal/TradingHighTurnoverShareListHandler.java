/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dal;

import com.mensa.sharewebservice.entity.TradingHighTurnoverShareList;
import java.util.List;

/**
 *
 * @author matt_
 */
public class TradingHighTurnoverShareListHandler  implements IHandler<TradingHighTurnoverShareList> {
    private RecordHandler<TradingHighTurnoverShareList> handler;
    
    public TradingHighTurnoverShareListHandler() {
        handler = new RecordHandler<TradingHighTurnoverShareList>(); 
    }
    
    @Override
    public boolean Exist(TradingHighTurnoverShareList record) {
        return Get(record) != null; 
    }
    
    @Override
    public TradingHighTurnoverShareList Get(TradingHighTurnoverShareList record) {
        String sql = "select record from TradingHighTurnoverShareList record where record.share_id = :share_id";
        List<TradingHighTurnoverShareList> answer = handler.GetQuery(sql).setParameter("share_id", record.getShare_id()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public TradingHighTurnoverShareList CreateAndReturn(TradingHighTurnoverShareList record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(TradingHighTurnoverShareList record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(TradingHighTurnoverShareList record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(TradingHighTurnoverShareList record) {
        return handler.Delete(record); 
    }

}
