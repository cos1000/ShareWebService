/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.dao;

import com.mensa.sharewebservice.model.CandleStickType;
import java.util.List;
import java.util.Map;

/**
 *
 * @author matt_
 */
public interface IHandler<T> {
    public boolean Exist(T record); 
    public T Get(T record); 
    public T GetById(int id); 
    public List<T> GetAll(); 
    public T CreateAndReturn(T record); 
    public boolean Create(T record); 
    public boolean Update(T record); 
    public boolean Delete(T record); 
}
