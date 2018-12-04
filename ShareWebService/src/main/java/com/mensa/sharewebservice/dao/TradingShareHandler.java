/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dao;

import com.mensa.sharewebservice.model.TradingShare;
import com.mensa.sharewebservice.util.Common;
import com.mensa.sharewebservice.util.FileHandler;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import org.springframework.stereotype.Repository;

/**
 *
 * @author matt_
 */
@Repository
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
    public List<TradingShare> GetAll() {
        String sql = "select record from TradingShare record order by updated_at desc";
        return handler.GetQuery(sql).setMaxResults(100).getResultList(); 
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
    public TradingShare GetById(int id) {
        String sql = "select record from TradingShare record where record.id = :id";
        List<TradingShare> answer = handler.GetQuery(sql).setParameter("id", id).setMaxResults(1).getResultList(); 
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
            TradingShare record = new TradingShare(); 
            Integer year = Common.TryParseToInteger(fields[1].trim().substring(0, 4)); 
            Integer month = Common.TryParseToInteger(fields[1].trim().substring(5, 7)); 
            Integer day = Common.TryParseToInteger(fields[1].trim().substring(8, 10)); 
            if (year != null && month != null && day != null) record.setTransaction_date(LocalDateTime.of(year, month, day, 0, 0)); 
            if (!fields[2].trim().isEmpty()) record.setShare_id(Common.TryParseToInteger(fields[2].trim())); 
            if (!fields[3].trim().isEmpty()) record.setClosing(Common.TryParseToDecimal(fields[3].trim())); 
            if (!fields[4].trim().isEmpty()) record.setPrevious_closing(Common.TryParseToDecimal(fields[4].trim())); 
            if (!fields[5].trim().isEmpty()) record.setAsk(Common.TryParseToDecimal(fields[5].trim())); 
            if (!fields[6].trim().isEmpty()) record.setBid(Common.TryParseToDecimal(fields[6].trim())); 
            if (!fields[7].trim().isEmpty()) record.setHigh(Common.TryParseToDecimal(fields[7].trim())); 
            if (!fields[8].trim().isEmpty()) record.setLow(Common.TryParseToDecimal(fields[8].trim())); 
            if (!fields[9].trim().isEmpty()) record.setShares_traded(Common.TryParseToDecimal(fields[9].trim())); 
            if (!fields[10].trim().isEmpty()) record.setTurnover(Common.TryParseToDecimal(fields[10].trim())); 
            if (!fields[11].trim().isEmpty()) record.setOpening(Common.TryParseToDecimal(fields[11].trim())); 
            if (!fields[12].trim().isEmpty()) record.setCount_traded(Common.TryParseToInteger(fields[12].trim())); 
            if (!fields[13].trim().isEmpty()) record.setCount_big_traded(Common.TryParseToInteger(fields[13].trim())); 
            if (!fields[14].trim().isEmpty()) record.setIs_ex_dividend_date(Common.TryParseToInteger(fields[14].trim())); 
            if (!fields[15].trim().isEmpty()) record.setIs_index_share(Common.TryParseToInteger(fields[15].trim())); 
            if (!fields[16].trim().isEmpty()) record.setSequence_number(Common.TryParseToInteger(fields[16].trim())); 
            
            record.setCreated_at(LocalDateTime.now());
            record.setUpdated_at(LocalDateTime.now());
            entityManager.persist(record);
        }
        entityManager.getTransaction().commit();
        
        return true; 
    }
    
    public Map<LocalDateTime, Long> GetSummary() {
        Map<LocalDateTime, Long> answer = new HashMap<LocalDateTime, Long>(); 
        String sql = "select record.transaction_date, count(record.share_id) as noOfRecord from TradingShare record group by record.transaction_date order by record.transaction_date desc";
        List sqlResult = handler.GetQuery(sql).setMaxResults(100).getResultList(); 
        for (Object object : sqlResult) {
            getSummaryResult(object, answer); 
        }
        return answer; 
    }
    
    private void getSummaryResult(Object result, Map<LocalDateTime, Long> answer) {
        if (result == null) return; 
        if (result instanceof Object[]) {
            LocalDateTime transaction_date = null; 
            Long noOfRecord = null; 
            Object[] row = (Object[]) result;
            if (row.length < 2) return; 
            if (row[0] instanceof LocalDateTime) transaction_date = (LocalDateTime) row[0]; 
            if (row[1] instanceof Long) noOfRecord = (Long) row[1]; 
            if ((transaction_date != null) && (noOfRecord != null)) answer.put(transaction_date, noOfRecord); 
        }
    }
    
}
