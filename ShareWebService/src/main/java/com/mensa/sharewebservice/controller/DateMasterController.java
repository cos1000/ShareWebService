/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.controller;

import com.mensa.sharewebservice.model.DateMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.mensa.sharewebservice.service.IService;
import com.mensa.sharewebservice.service.DateMasterServiceImpl;

/**
 *
 * @author matt_
 */
@Controller
public class DateMasterController implements IController<DateMaster>{
    private DateMasterServiceImpl service;
    
    @Autowired(required=true)
    @Qualifier(value="dateMasterService")
    public void setDateMasterService(DateMasterServiceImpl value){
        this.service = value;
    }
    
    @Override
    @RequestMapping(value= "/dateMaster/add", method = RequestMethod.POST)
    public String add(DateMaster record) {
        if(record.getId() == 0){
            this.service.add(record);
        }else{
            this.service.update(record);
        }

        return "redirect:/dateMaster";

    }

    @Override
    @RequestMapping("/dateMaster/edit/{id}")
    public String edit(int id, Model model) {
        model.addAttribute("record", this.service.getById(id));
        model.addAttribute("records", this.service.list());
        return "dateMaster";
    }

    @Override
    @RequestMapping(value = "/dateMaster", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("record", new DateMaster());
        model.addAttribute("records", this.service.list());
        return "dateMaster";
    }

    @Override
    @RequestMapping("/dateMaster/delete/{id}")
    public String delete(int id) {
        this.service.delete(id);
        return "redirect:/dateMaster";
    }
    
    @RequestMapping(value = "/dateMaster/init", method = RequestMethod.GET)
    public String init(Model model){
        this.service.init();
        return "redirect:/dateMaster";
    }
    
}
