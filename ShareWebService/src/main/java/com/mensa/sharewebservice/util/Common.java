/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.util;

import com.mensa.sharewebservice.dao.RecordHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matt_
 */
public class Common {
    private static Logger log = LoggerFactory.getLogger(Common.class);
    
    // <editor-fold desc="Try Parse">
    
    public static LocalDateTime GetDateWithoutTime(LocalDateTime time) {
        return LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), 0, 0, 0); 
    }
    
    // </editor-fold>
    
    // <editor-fold desc="Try Parse">
    
    public static Integer TryParseToInteger(String value) {
        try {
            return Integer.parseInt(value); 
        } catch (Exception e) {
            return null; 
        }
    }
    
    public static Long TryParseToLong(String value) {
        try {
            return Long.parseLong(value); 
        } catch (Exception e) {
            return null; 
        }
    }
    
    public static Double TryParseToDouble(String value) {
        try {
            return Double.parseDouble(value); 
        } catch (Exception e) {
            return null; 
        }
    }
    
    public static BigDecimal TryParseToDecimal(String value) {
        try {
            value = value.replace(",", ""); 
            Long longValue = TryParseToLong(value); 
            Double doubleValue = TryParseToDouble(value); 
            
            if (doubleValue != null) {
                return BigDecimal.valueOf(doubleValue); 
            } else if (longValue != null) {
                return BigDecimal.valueOf(longValue); 
            } else {
                return null; 
            }
        } catch (Exception e) {
            return null; 
        }
    }
    
    // </editor-fold>
    
    // <editor-fold desc="Properties">
    private static Properties properties = new Properties();

    public static Properties getProperties() {
        return properties;
    }
    
    public static String getProperty(CommonEnum.PropertyKeys key) {
        return getProperty(key.toString()); 
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key); 
    }
    // </editor-fold>
    
    // <editor-fold desc="Constructor">    
    
    static {
        properties = new Properties();
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String configFile = rootPath + "../classes/config.properties";
        log.debug(rootPath);
        try {
            File myFile = new File(configFile); 
            if (myFile.exists()) {
                log.debug("Existed Config File");
                properties.load(new FileInputStream(configFile));
            } else {
                log.debug("Not Exist Config File");
                properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
            }
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
    }
    
    // </editor-fold>
    
}
