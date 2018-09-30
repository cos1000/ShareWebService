/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dal;

import com.mensa.sharewebservice.entity.DateMaster;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author matt_
 */
public class DateMasterHandler  implements IHandler<DateMaster> {
    private RecordHandler<DateMaster> handler;
    
    public DateMasterHandler() {
        handler = new RecordHandler<DateMaster>(); 
    }
    
    @Override
    public boolean Exist(DateMaster record) {
        return Get(record) != null; 
    }
    
    @Override
    public DateMaster Get(DateMaster record) {
        String sql = "select record from DateMaster record where record.date_code = :date_code";
        List<DateMaster> answer = handler.GetQuery(sql).setParameter("date_code", record.getDate_code()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public DateMaster CreateAndReturn(DateMaster record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(DateMaster record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(DateMaster record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(DateMaster record) {
        return handler.Delete(record); 
    }
    
}
