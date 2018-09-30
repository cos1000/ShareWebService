/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.entity;

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
@Table(name = "trading_shares", uniqueConstraints = {@UniqueConstraint(columnNames = { "transaction_date", "share_id" })})
public class TradingShare implements Serializable {
    
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "transaction_date")
    private LocalDateTime transaction_date; 
    @Column(name = "share_id")
    private Integer share_id;
    @Column(name = "closing", precision = 20, scale = 6)
    private BigDecimal closing;
    @Column(name = "previous_closing", precision = 20, scale = 6)
    private BigDecimal previous_closing;
    @Column(name = "ask", precision = 20, scale = 6)
    private BigDecimal ask;
    @Column(name = "bid", precision = 20, scale = 6)
    private BigDecimal bid;
    @Column(name = "high", precision = 20, scale = 6)
    private BigDecimal high;
    @Column(name = "low", precision = 20, scale = 6)
    private BigDecimal low;
    @Column(name = "shares_traded", precision = 20, scale = 6)
    private BigDecimal shares_traded;
    @Column(name = "turnover", precision = 20, scale = 6)
    private BigDecimal turnover;
    @Column(name = "opening", precision = 20, scale = 6)
    private BigDecimal opening;
    @Column(name = "count_traded")
    private Integer count_traded;
    @Column(name = "count_big_traded")
    private Integer count_big_traded;
    @Column(name = "is_ex_dividend_date")
    private Integer is_ex_dividend_date;
    @Column(name = "is_index_share")
    private Integer is_index_share;
    @Column(name = "sequence_number")
    private Integer sequence_number;
    
    @Column(name = "created_at")
    private LocalDateTime created_at; 
    @Column(name = "updated_at")
    private LocalDateTime updated_at; 
}
