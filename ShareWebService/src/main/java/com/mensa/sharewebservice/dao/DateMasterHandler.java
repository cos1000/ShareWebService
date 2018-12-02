/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dao;

import com.mensa.sharewebservice.model.DateMaster;
import com.mensa.sharewebservice.util.Common;
import com.mensa.sharewebservice.util.FileHandler;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

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
    public List<DateMaster> GetAll() {
        String sql = "select record from DateMaster record";
        return handler.GetQuery(sql).getResultList(); 
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
    public DateMaster GetById(int id) {
        String sql = "select record from DateMaster record where record.id = :id";
        List<DateMaster> answer = handler.GetQuery(sql).setParameter("id", id).setMaxResults(1).getResultList(); 
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
    
    public boolean BulkInsert(String filename) throws IOException {
        String records = FileHandler.ReadFromFile(filename); 
        StringReader stringReader = new StringReader(records); 
        CSVReader reader = new CSVReader(stringReader); 
        
        EntityManager entityManager = handler.getEntityManager(); 
        entityManager.getTransaction().begin(); 
        
        boolean isFirstLine = true; 
        for (String[] fields : reader.readAll()) {
            if (isFirstLine) {
                isFirstLine = false; 
                continue; 
            }
            DateMaster record = new DateMaster(); 
            Integer year = Common.TryParseToInteger(fields[0].trim().substring(0, 4)); 
            Integer month = Common.TryParseToInteger(fields[0].trim().substring(5, 7)); 
            Integer day = Common.TryParseToInteger(fields[0].trim().substring(8, 10)); 
            if (year != null && month != null && day != null) record.setDate_code(LocalDateTime.of(year, month, day, 0, 0)); 
            record.setCreated_at(LocalDateTime.now());
            record.setUpdated_at(LocalDateTime.now());
            entityManager.persist(record);
        }
        entityManager.getTransaction().commit();
        
        return true; 
    }
    
}
