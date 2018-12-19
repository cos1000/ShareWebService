/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  matt_
 * Created: Dec 3, 2018
 */

-- Table: public.candle_stick_type

-- DROP TABLE public.candle_stick_type;

CREATE TABLE public.candle_stick_type
(
    id bigint NOT NULL,
    name character varying(200) COLLATE pg_catalog."default",
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT candle_stick_type_pkey PRIMARY KEY (id),
    CONSTRAINT candle_stick_type_unique UNIQUE (name)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.candle_stick_type
    OWNER to admin;

-- Table: public.date_master

-- DROP TABLE public.date_master;

CREATE TABLE public.date_master
(
    id bigint NOT NULL,
    date_code timestamp without time zone,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT date_master_pkey PRIMARY KEY (id),
    CONSTRAINT date_master_unique UNIQUE (date_code)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.date_master
    OWNER to admin;


-- Table: public.share_information

-- DROP TABLE public.share_information;

CREATE TABLE public.share_information
(
    id bigint NOT NULL,
    share_id integer,
    name character varying(200) COLLATE pg_catalog."default",
    industry_classification1 character varying(200) COLLATE pg_catalog."default",
    industry_classification2 character varying(200) COLLATE pg_catalog."default",
    principal_activities character varying(200) COLLATE pg_catalog."default",
    earnings_per_share numeric(19,2),
    issued_shares bigint,
    market_capitalisation bigint,
    net_asset_value bigint,
    net_profit bigint,
    last_update timestamp without time zone,
    listing_date timestamp without time zone,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT share_information_pkey PRIMARY KEY (id),
    CONSTRAINT share_information_unique UNIQUE (share_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.share_information
    OWNER to admin;

-- Table: public.trading

-- DROP TABLE public.trading;

CREATE TABLE public.trading
(
    id bigint NOT NULL,
    transaction_date timestamp without time zone,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT trading_pkey PRIMARY KEY (id),
    CONSTRAINT trading_unique UNIQUE (transaction_date)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.trading
    OWNER to admin;


-- Table: public.trading_high_turnover_share_list

-- DROP TABLE public.trading_high_turnover_share_list;

CREATE TABLE public.trading_high_turnover_share_list
(
    id bigint NOT NULL,
    share_id integer,
    ranking integer,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT trading_high_turnover_share_list_pkey PRIMARY KEY (id),
    CONSTRAINT trading_high_turnover_share_list_unique UNIQUE (share_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.trading_high_turnover_share_list
    OWNER to admin;

-- Table: public.trading_index

-- DROP TABLE public.trading_index;

CREATE TABLE public.trading_index
(
    id bigint NOT NULL,
    transaction_date timestamp without time zone,
    index integer,
    previous_closing numeric(20,6),
    morning_closing numeric(20,6),
    closing numeric(20,6),
    sequence_number numeric(20,6),
    simple_moving_average_10 numeric(20,6),
    simple_moving_average_20 numeric(20,6),
    simple_moving_average_50 numeric(20,6),
    simple_moving_average_250 numeric(20,6),
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT trading_index_pkey PRIMARY KEY (id),
    CONSTRAINT trading_index_unique UNIQUE (transaction_date, index)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.trading_index
    OWNER to admin;


-- Table: public.trading_index_share_list

-- DROP TABLE public.trading_index_share_list;

CREATE TABLE public.trading_index_share_list
(
    id bigint NOT NULL,
    share_id integer,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT trading_index_share_list_pkey PRIMARY KEY (id),
    CONSTRAINT trading_index_share_list_unique UNIQUE (share_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.trading_index_share_list
    OWNER to admin;

-- Table: public.trading_shares

-- DROP TABLE public.trading_shares;

CREATE TABLE public.trading_shares
(
    id bigint NOT NULL,
    transaction_date timestamp without time zone,
    share_id integer,
    sequence_number integer,
    previous_closing numeric(20,6),
    opening numeric(20,6),
    closing numeric(20,6),
    high numeric(20,6),
    low numeric(20,6),
    ask numeric(20,6),
    bid numeric(20,6),
    shares_traded numeric(20,6),
    turnover numeric(20,6),
    count_big_traded integer,
    count_traded integer,
    is_ex_dividend_date integer,
    is_index_share integer,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT trading_shares_pkey PRIMARY KEY (id),
    CONSTRAINT trading_shares_unique UNIQUE (transaction_date, share_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.trading_shares
    OWNER to admin;


CREATE TABLE public.trading_reports
(
	id bigint NOT NULL,
	transaction_date timestamp without time zone,
	top_10_traded_rise_count integer,
	top_10_traded_fall_count integer,
	top_10_turnover_rise_count integer,
	top_10_turnover_fall_count integer,
	total_rise_count integer,
	total_fall_count integer,
	advance_decline_line integer,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT trading_eports_pkey PRIMARY KEY (id),
    CONSTRAINT trading_reports_fkey UNIQUE (transaction_date)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.trading_reports 
    OWNER to admin;


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

