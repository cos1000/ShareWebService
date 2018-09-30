/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.util;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
/*
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
*/
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element; 


/**
 *
 * @author matt_
 */
public class Converter {
    
    // <editor-fold desc="Convert Methods">    
      
    public static LocalDateTime ConvertDateFromFilename(String filename) {
        Integer year = Common.TryParseToInteger(filename.substring(1, 3)); 
        Integer month = Common.TryParseToInteger(filename.substring(3, 5)); 
        Integer day = Common.TryParseToInteger(filename.substring(5, 7)); 
        LocalDateTime transaction_date = null; 
        
        if (year != null && month != null && day != null) {
            
            System.out.println("Matt Testing"); 
            System.out.println(year); 
            System.out.println(month); 
            System.out.println(day); 
            
            transaction_date = LocalDateTime.of(year + 2000, month, day, 0, 0);
        }
        return transaction_date; 
    }
    
    public static Document ConvertXmlFromString(String xmlString) {
        return Jsoup.parse(xmlString); 
    }
    
    public static String ConvertHtmlToString(Document doc) {
        StringBuilder answer = new StringBuilder(); 
        Elements elementList = doc.select("font"); 
        for (Element element : elementList) {
            answer.append(element.text()).append("\n"); 
        }
        return answer.toString(); 
    }
    
    // </editor-fold>
    
}
