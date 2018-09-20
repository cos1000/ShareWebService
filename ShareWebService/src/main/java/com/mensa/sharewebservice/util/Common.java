/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author matt_
 */
public class Common {
    
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
        try {
            properties.load(new FileInputStream(configFile));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    // </editor-fold>
    
}
