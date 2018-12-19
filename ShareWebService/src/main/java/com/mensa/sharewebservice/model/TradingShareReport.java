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
@Table(name = "trading_shares_reports", uniqueConstraints = {@UniqueConstraint(columnNames = { "transaction_date", "share_id" })})
public class TradingShareReport {
    
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "transaction_date")
    private LocalDateTime transaction_date; 
    @Column(name = "share_id")
    private Integer share_id;

    @Column(name = "simple_moving_average_10", precision = 20, scale = 6)
    private BigDecimal simple_moving_average_10;
    @Column(name = "simple_moving_average_20", precision = 20, scale = 6)
    private BigDecimal simple_moving_average_20;
    @Column(name = "simple_moving_average_50", precision = 20, scale = 6)
    private BigDecimal simple_moving_average_50;
    @Column(name = "simple_moving_average_250", precision = 20, scale = 6)
    private BigDecimal simple_moving_average_250;
    @Column(name = "candle_stick_type")
    private Integer candle_stick_type;
    @Column(name = "is_short_high")
    private Integer is_short_high;
    @Column(name = "is_short_low")
    private Integer is_short_low;
    @Column(name = "is_medium_high")
    private Integer is_medium_high;
    @Column(name = "is_medium_low")
    private Integer is_medium_low;
    @Column(name = "is_long_high")
    private Integer is_long_high;
    @Column(name = "is_long_low")
    private Integer is_long_low;
    @Column(name = "is_candle_stick_piercing")
    private Integer is_candle_stick_piercing;
    @Column(name = "is_candle_stick_dark_cloud_cover")
    private Integer is_candle_stick_dark_cloud_cover;
    @Column(name = "is_candle_stick_harami_cross")
    private Integer is_candle_stick_harami_cross;
    @Column(name = "is_candle_stick_harami")
    private Integer is_candle_stick_harami;
    @Column(name = "is_candle_stick_two_black_crows")
    private Integer is_candle_stick_two_black_crows;
    @Column(name = "is_candle_stick_bearish_engulfing")
    private Integer is_candle_stick_bearish_engulfing;
    @Column(name = "is_candle_stick_bullish_engulfing")
    private Integer is_candle_stick_bullish_engulfing;
    @Column(name = "is_candle_stick_morning_star")
    private Integer is_candle_stick_morning_star;
    @Column(name = "is_candle_stick_evening_star")
    private Integer is_candle_stick_evening_star;
    @Column(name = "is_candle_stick_three_white_soldiers")
    private Integer is_candle_stick_three_white_soldiers;
    @Column(name = "is_candle_stick_three_black_crows")
    private Integer is_candle_stick_three_black_crows;
    @Column(name = "bollinger_high", precision = 20, scale = 6)
    private BigDecimal bollinger_high;
    @Column(name = "bollinger_low", precision = 20, scale = 6)
    private BigDecimal bollinger_low;
    @Column(name = "rsi", precision = 20, scale = 6)
    private BigDecimal rsi;
    @Column(name = "exponential_moving_average_10", precision = 20, scale = 6)
    private BigDecimal exponential_moving_average_10;
    @Column(name = "exponential_moving_average_20", precision = 20, scale = 6)
    private BigDecimal exponential_moving_average_20;
    @Column(name = "exponential_moving_average_50", precision = 20, scale = 6)
    private BigDecimal exponential_moving_average_50;
    @Column(name = "exponential_moving_average_250", precision = 20, scale = 6)
    private BigDecimal exponential_moving_average_250;
    @Column(name = "exponential_moving_average_12", precision = 20, scale = 6)
    private BigDecimal exponential_moving_average_12;
    @Column(name = "exponential_moving_average_26", precision = 20, scale = 6)
    private BigDecimal exponential_moving_average_26;
    @Column(name = "macd_fast", precision = 20, scale = 6)
    private BigDecimal macd_fast;
    @Column(name = "macd_slow", precision = 20, scale = 6)
    private BigDecimal macd_slow;
    @Column(name = "macd_dif", precision = 20, scale = 6)
    private BigDecimal macd_dif;
    @Column(name = "stc_fast_k", precision = 20, scale = 6)
    private BigDecimal stc_fast_k;
    @Column(name = "stc_fast_d", precision = 20, scale = 6)
    private BigDecimal stc_fast_d;
    @Column(name = "stc_slow_k", precision = 20, scale = 6)
    private BigDecimal stc_slow_k;
    @Column(name = "stc_slow_d", precision = 20, scale = 6)
    private BigDecimal stc_slow_d;
    @Column(name = "mtm_9_day", precision = 20, scale = 6)
    private BigDecimal mtm_9_day;
    @Column(name = "mtm_18_day", precision = 20, scale = 6)
    private BigDecimal mtm_18_day;
    @Column(name = "william_14", precision = 20, scale = 6)
    private BigDecimal william_14;
    @Column(name = "william_18", precision = 20, scale = 6)
    private BigDecimal william_18;
    @Column(name = "roc_10", precision = 20, scale = 6)
    private BigDecimal roc_10;
    @Column(name = "roc_12", precision = 20, scale = 6)
    private BigDecimal roc_12;
    @Column(name = "roc_15", precision = 20, scale = 6)
    private BigDecimal roc_15;
    @Column(name = "roc_20", precision = 20, scale = 6)
    private BigDecimal roc_20;
    @Column(name = "roc_25", precision = 20, scale = 6)
    private BigDecimal roc_25;
    @Column(name = "obv", precision = 20, scale = 6)
    private BigDecimal obv;
    @Column(name = "dmi_up", precision = 20, scale = 6)
    private BigDecimal dmi_up;
    @Column(name = "dmi_down", precision = 20, scale = 6)
    private BigDecimal dmi_down;
    @Column(name = "dmi_adx", precision = 20, scale = 6)
    private BigDecimal dmi_adx;
    @Column(name = "dmi_tr", precision = 20, scale = 6)
    private BigDecimal dmi_tr;
    @Column(name = "pvt", precision = 20, scale = 6)
    private BigDecimal pvt;
    @Column(name = "dmi_dx", precision = 20, scale = 6)
    private BigDecimal dmi_dx;
    @Column(name = "green_light_short")
    private Integer green_light_short;
    @Column(name = "green_light_medium")
    private Integer green_light_medium;
    @Column(name = "green_light_long")
    private Integer green_light_long;
    @Column(name = "red_light_short")
    private Integer red_light_short;
    @Column(name = "red_light_medium")
    private Integer red_light_medium;
    @Column(name = "red_light_long")
    private Integer red_light_long;
    @Column(name = "high_10", precision = 20, scale = 6)
    private BigDecimal high_10;
    @Column(name = "high_20", precision = 20, scale = 6)
    private BigDecimal high_20;
    @Column(name = "high_50", precision = 20, scale = 6)
    private BigDecimal high_50;
    @Column(name = "high_250", precision = 20, scale = 6)
    private BigDecimal high_250;
    @Column(name = "low_10", precision = 20, scale = 6)
    private BigDecimal low_10;
    @Column(name = "low_20", precision = 20, scale = 6)
    private BigDecimal low_20;
    @Column(name = "low_50", precision = 20, scale = 6)
    private BigDecimal low_50;
    @Column(name = "low_250", precision = 20, scale = 6)
    private BigDecimal low_250;
    @Column(name = "bollinger_win")
    private Integer bollinger_win;
    @Column(name = "bollinger_lost")
    private Integer bollinger_lost;
    @Column(name = "rsi_win")
    private Integer rsi_win;
    @Column(name = "rsi_lost")
    private Integer rsi_lost;
    @Column(name = "sma_win")
    private Integer sma_win;
    @Column(name = "sma_lost")
    private Integer sma_lost;
    @Column(name = "macd_win")
    private Integer macd_win;
    @Column(name = "macd_lost")
    private Integer macd_lost;
    @Column(name = "stc_win")
    private Integer stc_win;
    @Column(name = "stc_lost")
    private Integer stc_lost;
    @Column(name = "mtm_win")
    private Integer mtm_win;
    @Column(name = "mtm_lost")
    private Integer mtm_lost;
    @Column(name = "william_win")
    private Integer william_win;
    @Column(name = "william_lost")
    private Integer william_lost;
    @Column(name = "roc_win")
    private Integer roc_win;
    @Column(name = "roc_lost")
    private Integer roc_lost;
    @Column(name = "obv_win")
    private Integer obv_win;
    @Column(name = "obv_lost")
    private Integer obv_lost;
    @Column(name = "dmi_win")
    private Integer dmi_win;
    @Column(name = "dmi_lost")
    private Integer dmi_lost;
    @Column(name = "pvt_win")
    private Integer pvt_win;
    @Column(name = "pvt_lost")
    private Integer pvt_lost;
    @Column(name = "bollinger_green_light")
    private Integer bollinger_green_light;
    @Column(name = "rsi_green_light")
    private Integer rsi_green_light;
    @Column(name = "sma_green_light")
    private Integer sma_green_light;
    @Column(name = "macd_green_light")
    private Integer macd_green_light;
    @Column(name = "stc_green_light")
    private Integer stc_green_light;
    @Column(name = "mtm_green_light")
    private Integer mtm_green_light;
    @Column(name = "william_green_light")
    private Integer william_green_light;
    @Column(name = "roc_green_light")
    private Integer roc_green_light;
    @Column(name = "obv_green_light")
    private Integer obv_green_light;
    @Column(name = "dmi_green_light")
    private Integer dmi_green_light;
    @Column(name = "pvt_green_light")
    private Integer pvt_green_light;
    
    @Column(name = "created_at")
    private LocalDateTime created_at; 
    @Column(name = "updated_at")
    private LocalDateTime updated_at; 
}
