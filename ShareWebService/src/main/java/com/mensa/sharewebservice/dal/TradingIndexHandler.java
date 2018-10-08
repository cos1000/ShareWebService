/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dal;

import com.mensa.sharewebservice.entity.TradingIndex;
import com.mensa.sharewebservice.util.Common;
import com.mensa.sharewebservice.util.FileHandler;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;

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
    public List<TradingIndex> GetAll() {
        String sql = "select record from TradingIndex record";
        return handler.GetQuery(sql).getResultList(); 
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
    
    public boolean BulkInsert(String filename) throws IOException {
        String records = FileHandler.ReadFromFile(filename); 
        StringReader stringReader = new StringReader(records); 
        //InputStreamReader input = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8); 
        CSVReader reader = new CSVReader(stringReader); 
        
        EntityManager entityManager = handler.getEntityManager(); 
        entityManager.getTransaction().begin(); 
        
        boolean isFirstLine = true; 
        for (String[] fields : reader.readAll()) {
            if (isFirstLine) {
                isFirstLine = false; 
                continue; 
            }
            TradingIndex record = new TradingIndex(); 
            Integer year = Common.TryParseToInteger(fields[1].trim().substring(0, 4)); 
            Integer month = Common.TryParseToInteger(fields[1].trim().substring(5, 7)); 
            Integer day = Common.TryParseToInteger(fields[1].trim().substring(8, 10)); 
            if (year != null && month != null && day != null) record.setTransaction_date(LocalDateTime.of(year, month, day, 0, 0)); 
            if (fields[2].trim().isEmpty()) record.setIndex(Common.TryParseToInteger(fields[2].trim())); 
            if (fields[3].trim().isEmpty()) record.setMorning_closing(Common.TryParseToDecimal(fields[3].trim())); 
            if (fields[4].trim().isEmpty()) record.setClosing(Common.TryParseToDecimal(fields[4].trim())); 
            if (fields[5].trim().isEmpty()) record.setPrevious_closing(Common.TryParseToDecimal(fields[5].trim())); 
            if (fields[6].trim().isEmpty()) record.setSimple_moving_average_10(Common.TryParseToDecimal(fields[6].trim())); 
            if (fields[7].trim().isEmpty()) record.setSimple_moving_average_20(Common.TryParseToDecimal(fields[7].trim())); 
            if (fields[8].trim().isEmpty()) record.setSimple_moving_average_50(Common.TryParseToDecimal(fields[8].trim())); 
            if (fields[9].trim().isEmpty()) record.setSimple_moving_average_250(Common.TryParseToDecimal(fields[9].trim())); 
            if (fields[10].trim().isEmpty()) record.setSequence_number(Common.TryParseToDecimal(fields[10].trim())); 
            record.setCreated_at(LocalDateTime.now());
            record.setUpdated_at(LocalDateTime.now());
            entityManager.persist(record);
        }
        entityManager.getTransaction().commit();
        
        return true; 
    }
    
}
