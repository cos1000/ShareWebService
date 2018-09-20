/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice;

import com.mensa.sharewebservice.util.Converter;
import com.mensa.sharewebservice.util.FileHandler;
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

import javax.swing.text.html.*;  
import javax.swing.text.html.parser.*;

/**
 *
 * @author matt_
 */
public class Downloader {
    private Logger log = LoggerFactory.getLogger(Downloader.class);
    private final String USER_AGENT = "Mozilla/5.0";
    
    // <editor-fold desc="Testing Main">
    public static void main(String[] args) {
        String url = "https://www.hkex.com.hk/eng/stat/smstat/dayquot/d180903e.htm";
        String filename = "C:\\Temp\\Downloader.txt";
        Downloader downloader = new Downloader(); 
        try {
            FileHandler.SaveToFile(filename, downloader.GetResponseFromUrl(url)); 
        } catch (IOException e)  {
            e.printStackTrace();
        } 
        /*
        try {
            String response = downloader.GetResponseFromUrl(url); 
            //System.out.println(response);
            response = Converter.ConvertHtmlToString(Converter.ConvertXmlFromString(response)); 
            FileHandler.SaveToFile(filename, response); 
        } catch (IOException e)  {
            e.printStackTrace();
        }
*/
    }
    // </editor-fold>
    
    // <editor-fold desc="Public Tools Methods">
    
    public String GetResponseFromUrl(String url) {
        log.debug("Start GetResponseFromUrl");
        String answer = "";
        try {
            //url = "https://www.hkex.com.hk/eng/stat/smstat/dayquot/d180903e.htm";
            //url = "http://www.rthk.hk/";
            URL iurl = new URL(url);
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            HttpURLConnection uc = (HttpURLConnection)iurl.openConnection();
            uc.setRequestMethod("GET"); 
            uc.setRequestProperty("User-Agent", USER_AGENT);
            uc.setDoOutput(true); 
            uc.connect();
            
            try (BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()))) {
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine).append("\n");
                }
                answer = response.toString(); 
            }
            
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
    
    // </editor-fold>
}
