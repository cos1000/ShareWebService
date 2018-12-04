/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  matt_
 * Created: Dec 3, 2018
 */





CREATE TABLE public.trading_shares_reports 
(
	id bigint NOT NULL,
	transaction_date timestamp without time zone,
	share_id integer,
	simple_moving_average_10 numeric(20,6),
	simple_moving_average_20 numeric(20,6),
	simple_moving_average_50 numeric(20,6),
	simple_moving_average_250 numeric(20,6),
	candle_stick_type integer,
	is_short_high integer,
	is_short_low integer,
	is_medium_high integer,
	is_medium_low integer,
	is_long_high integer,
	is_long_low integer,
	is_candle_stick_piercing integer,
	is_candle_stick_dark_cloud_cover integer,
	is_candle_stick_harami_cross integer,
	is_candle_stick_harami integer,
	is_candle_stick_two_black_crows integer,
	is_candle_stick_bearish_engulfing integer,
	is_candle_stick_bullish_engulfing integer,
	is_candle_stick_morning_star integer,
	is_candle_stick_evening_star integer,
	is_candle_stick_three_white_soldiers integer,
	is_candle_stick_three_black_crows integer,
	bollinger_high numeric(20,6),
	bollinger_low numeric(20,6),
	rsi numeric(20,6),
	exponential_moving_average_10 numeric(20,6),
	exponential_moving_average_20 numeric(20,6),
	exponential_moving_average_50 numeric(20,6),
	exponential_moving_average_250 numeric(20,6),
	exponential_moving_average_12 numeric(20,6),
	exponential_moving_average_26 numeric(20,6),
	macd_fast numeric(20,6),
	macd_slow numeric(20,6),
	macd_dif numeric(20,6),
	stc_fast_k numeric(20,6),
	stc_fast_d numeric(20,6),
	stc_slow_k numeric(20,6),
	stc_slow_d numeric(20,6),
	mtm_9_day numeric(20,6),
	mtm_18_day numeric(20,6),
	william_14 numeric(20,6),
	william_18 numeric(20,6),
	roc_10 numeric(20,6),
	roc_12 numeric(20,6),
	roc_15 numeric(20,6),
	roc_20 numeric(20,6),
	roc_25 numeric(20,6),
	obv numeric(20,6),
	dmi_up numeric(20,6),
	dmi_down numeric(20,6),
	dmi_adx numeric(20,6),
	dmi_tr numeric(20,6),
	pvt numeric(20,6),
	dmi_dx numeric(20,6),
	green_light_short integer,
	green_light_medium integer,
	green_light_long integer,
	red_light_short integer,
	red_light_medium integer,
	red_light_long integer,
	high_10 numeric(20,6),
	high_20 numeric(20,6),
	high_50 numeric(20,6),
	high_250 numeric(20,6),
	low_10 numeric(20,6),
	low_20 numeric(20,6),
	low_50 numeric(20,6),
	low_250 numeric(20,6),
	bollinger_win integer,
	bollinger_lost integer,
	rsi_win integer,
	rsi_lost integer,
	sma_win integer,
	sma_lost integer,
	macd_win integer,
	macd_lost integer,
	stc_win integer,
	stc_lost integer,
	mtm_win integer,
	mtm_lost integer,
	william_win integer,
	william_lost integer,
	roc_win integer,
	roc_lost integer,
	obv_win integer,
	obv_lost integer,
	dmi_win integer,
	dmi_lost integer,
	pvt_win integer,
	pvt_lost integer,
	bollinger_green_light integer,
	rsi_green_light integer,
	sma_green_light integer,
	macd_green_light integer,
	stc_green_light integer,
	mtm_green_light integer,
	william_green_light integer,
	roc_green_light integer,
	obv_green_light integer,
	dmi_green_light integer,
	pvt_green_light integer,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT trading_shares_reports_pkey PRIMARY KEY (id),
    CONSTRAINT trading_shares_reports_fkey UNIQUE (transaction_date, share_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.trading_shares_reports 
    OWNER to admin;

