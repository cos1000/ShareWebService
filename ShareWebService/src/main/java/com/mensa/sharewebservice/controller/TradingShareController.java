/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.controller;

import com.mensa.sharewebservice.model.TradingShare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.mensa.sharewebservice.service.IService;
import com.mensa.sharewebservice.service.TradingShareServiceImpl;

/**
 *
 * @author matt_
 */
@Controller
public class TradingShareController implements IController<TradingShare>{
    //private IService service;
    private TradingShareServiceImpl service;

    @Autowired(required=true)
    @Qualifier(value="tradingShareService")
    public void setTradingShareService(TradingShareServiceImpl value){
        this.service = value;
    }

    @Override
    @RequestMapping(value = "/tradingShares", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("record", new TradingShare());
        model.addAttribute("records", this.service.list());
        return "tradingShare";
    }
    
    @Override
    @RequestMapping(value= "/tradingShares/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("record") TradingShare record){
        if(record.getId() == 0){
            this.service.add(record);
        }else{
            this.service.update(record);
        }

        return "redirect:/tradingShares";
    }    
    
    @Override
    @RequestMapping("/tradingShares/delete/{id}")
    public String delete(@PathVariable("id") int id){
        this.service.delete(id);
        return "redirect:/tradingShares";
    }
     
    @Override
    @RequestMapping("/tradingShares/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("record", this.service.getById(id));
        model.addAttribute("records", this.service.list());
        return "tradingShare";
    }
    
    @RequestMapping(value = "/tradingShares/summary", method = RequestMethod.GET)
    public String summary(Model model) {
        model.addAttribute("records", this.service.summary());
        return "tradingShareSummary";
    }
        
    @RequestMapping(value = "/tradingShares/insertFileByMonth", method = RequestMethod.GET)
    public String insertFileByMonth(Model model){
        this.service.insertFileByMonth();
        model.addAttribute("message", "Inserting File");
        return "tradingShare";
    }
        
    @RequestMapping(value = "/tradingShares/insertFileByMonth/{year}/{month}", method = RequestMethod.GET)
    public String insertFileByMonth(@PathVariable("year") int year, @PathVariable("month") int month, Model model){
        this.service.insertFileByMonth(year, month);
        model.addAttribute("message", "Inserting File");
        return "tradingShare";
    }
    
    @RequestMapping(value = "/tradingShares/insertFileByYear", method = RequestMethod.GET)
    public String insertFileByYear(Model model){
        this.service.insertFileByYear();
        model.addAttribute("message", "Inserting File");
        return "tradingShare";
    }
    
    @RequestMapping(value = "/tradingShares/insertFileToCurrentDate", method = RequestMethod.GET)
    public String insertFileToCurrentDate(Model model){
        this.service.insertFileToCurrentDate();
        model.addAttribute("message", "Inserting File");
        return "tradingShare";
    }
    
    @RequestMapping(value = "/tradingShares/calculateSequenceNumber", method = RequestMethod.GET)
    public String insertSummary(Model model){
        this.service.calculateSequenceNumber();
        model.addAttribute("message", "calculate Sequence Number");
        return "tradingShare";
    }
    
}
