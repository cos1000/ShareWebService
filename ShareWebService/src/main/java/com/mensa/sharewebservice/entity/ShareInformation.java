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
@Table(name = "share_information", uniqueConstraints = {@UniqueConstraint(columnNames = { "share_id" })})
public class ShareInformation implements Serializable {
    
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "share_id")
    private Integer share_id;
    @Column(name = "name", length = 200)
    private String name;
    @Column(name = "principal_activities", length = 200)
    private String principal_activities;
    @Column(name = "Industry_classification1", length = 200)
    private String Industry_classification1;
    @Column(name = "Industry_classification2", length = 200)
    private String Industry_classification2;
    @Column(name = "listing_date")
    private LocalDateTime listing_date; 
    @Column(name = "issued_shares")
    private Long issued_shares; 
    @Column(name = "market_capitalisation")
    private Long market_capitalisation; 
    @Column(name = "net_asset_value")
    private Long net_asset_value; 
    @Column(name = "net_profit")
    private Long net_profit; 
    @Column(name = "earnings_per_share")
    private BigDecimal earnings_per_share; 
    @Column(name = "last_update")
    private LocalDateTime last_update; 
                
    @Column(name = "created_at")
    private LocalDateTime created_at; 
    @Column(name = "updated_at")
    private LocalDateTime updated_at; 
    
}
