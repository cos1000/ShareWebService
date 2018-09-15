/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 *
 * @author matt_
 */
public class Downloader {
    private Logger log = LoggerFactory.getLogger(Downloader.class);
    private final String USER_AGENT = "Mozilla/5.0";
    
    public boolean SaveToFile(String filename, String content) {
        File file = new File(filename);
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
            return true; 
        } catch (IOException e) { 
            log.error(String.format("SaveToFile Error: %s", e.getMessage())); 
            return false; 
        } 
    }
    
    public Document ConvertXmlFromString(String xmlString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
        DocumentBuilder builder;  
        try {  
            builder = factory.newDocumentBuilder();  
            return builder.parse(new InputSource(new StringReader(xmlString))); 
        } catch (Exception e) {  
            //e.printStackTrace();  
            log.error(String.format("ConvertXmlFromString Error: %s", e.getMessage())); 
            return null; 
        } 
    }
    
    public String GetResponseFromUrl() {
        log.debug("Start GetResponseFromUrl");
        String answer = "";
        try {
            //String url = "https://www.hkex.com.hk/eng/stat/smstat/dayquot/d180903e.htm";
            String url = "http://www.rthk.hk/";
            URL iurl = new URL(url);
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            HttpURLConnection uc = (HttpURLConnection)iurl.openConnection();
            uc.setRequestMethod("GET"); 
            uc.setRequestProperty("User-Agent", USER_AGENT);
            uc.setDoOutput(true); 
            uc.connect();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
            }
            in.close();
            answer = response.toString(); 
            
            //answer = uc.getContentEncoding(); 
            uc.disconnect(); 
            //log.debug(answer);
        } catch (MalformedURLException e) {
            log.error(String.format("GetResponseFromUrl Error: %s", e.getMessage())); 
        } catch (IOException e) {
            log.error(String.format("GetResponseFromUrl Error: %s", e.getMessage())); 
        }
        log.debug("Finished GetResponseFromUrl");
            
        return answer; 
    }
}
