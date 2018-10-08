/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dal;

import com.mensa.sharewebservice.entity.ShareInformation;
import java.util.List;

/**
 *
 * @author matt_
 */
public class ShareInformationHandler  implements IHandler<ShareInformation> {
    private RecordHandler<ShareInformation> handler;
    
    public ShareInformationHandler() {
        handler = new RecordHandler<ShareInformation>(); 
    }
    
    @Override
    public boolean Exist(ShareInformation record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<ShareInformation> GetAll() {
        String sql = "select record from ShareInformation record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public ShareInformation Get(ShareInformation record) {
        String sql = "select record from ShareInformation record where record.share_id = :share_id";
        List<ShareInformation> answer = handler.GetQuery(sql).setParameter("share_id", record.getShare_id()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public ShareInformation CreateAndReturn(ShareInformation record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(ShareInformation record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(ShareInformation record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(ShareInformation record) {
        return handler.Delete(record); 
    }

}
