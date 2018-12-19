/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author matt_
 */

@Entity
@Data
@Table(name = "trading_reports", uniqueConstraints = {@UniqueConstraint(columnNames = { "transaction_date" })})
public class TradingReport {
    
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "transaction_date")
    private LocalDateTime transaction_date; 

    @Column(name = "top_10_traded_rise_count")
    private Integer top_10_traded_rise_count;
    @Column(name = "top_10_traded_fall_count")
    private Integer top_10_traded_fall_count;
    @Column(name = "top_10_turnover_rise_count")
    private Integer top_10_turnover_rise_count;
    @Column(name = "top_10_turnover_fall_count")
    private Integer top_10_turnover_fall_count;
    @Column(name = "total_rise_count")
    private Integer total_rise_count;
    @Column(name = "total_fall_count")
    private Integer total_fall_count;
    @Column(name = "advance_decline_line")
    private Integer advance_decline_line;
    
    @Column(name = "created_at")
    private LocalDateTime created_at; 
    @Column(name = "updated_at")
    private LocalDateTime updated_at; 
}
