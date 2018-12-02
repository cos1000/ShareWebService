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
@Table(name = "trading_index", uniqueConstraints = {@UniqueConstraint(columnNames = { "transaction_date", "index" })})
public class TradingIndex  implements Serializable {
    
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "transaction_date")
    private LocalDateTime transaction_date; 
    @Column(name = "index")
    private Integer index;
    @Column(name = "morning_closing", precision = 20, scale = 6)
    private BigDecimal morning_closing;
    @Column(name = "closing", precision = 20, scale = 6)
    private BigDecimal closing;
    @Column(name = "previous_closing", precision = 20, scale = 6)
    private BigDecimal previous_closing;
    @Column(name = "simple_moving_average_10", precision = 20, scale = 6)
    private BigDecimal simple_moving_average_10;
    @Column(name = "simple_moving_average_20", precision = 20, scale = 6)
    private BigDecimal simple_moving_average_20;
    @Column(name = "simple_moving_average_50", precision = 20, scale = 6)
    private BigDecimal simple_moving_average_50;
    @Column(name = "simple_moving_average_250", precision = 20, scale = 6)
    private BigDecimal simple_moving_average_250;
    @Column(name = "sequence_number", precision = 20, scale = 6)
    private BigDecimal sequence_number;

    @Column(name = "created_at")
    private LocalDateTime created_at; 
    @Column(name = "updated_at")
    private LocalDateTime updated_at; 
    
}
