/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.util;

import java.io.IOException;
import java.io.StringReader;
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
      
    /*
    private static String ConvertHtmlToString(NodeList list) {
        StringBuilder answer = new StringBuilder(); 
        for (int counter = 0; counter < list.getLength() - 1; counter++) {
            if (list.item(counter).getChildNodes().getLength() > 0) {
                answer.append(ConvertHtmlToString(list.item(counter).getChildNodes())); 
            } 
            if (!list.item(counter).getTextContent().isEmpty()) {
                answer.append(list.item(counter).getTextContent()).append("\n"); 
            }
        }
        return answer.toString(); 
    }
    
    public static String ConvertHtmlToString(Document xmlDoc) {
        StringBuilder answer = new StringBuilder(); 
        answer.append(ConvertHtmlToString(xmlDoc.getChildNodes())); 
        return answer.toString(); 
    }
    */
    /*
    public static String ConvertHtmlToString(String xmlString) throws IOException {
        final StringBuilder cleaned = new StringBuilder();
        HTMLEditorKit.ParserCallback callback = new HTMLEditorKit.ParserCallback()
        {
            public boolean readyForNewline;
            public void handleText(char[] data, int pos)
            {
//                String line = new String(data); 
//                if (!line.isEmpty()) {
//                    cleaned.append(line).append("\n");
//                }
                cleaned.append(new String(data));
                readyForNewline = true;
            }
            public void handleStartTag(final HTML.Tag t, final MutableAttributeSet a, final int pos) {
                if (readyForNewline && (t == HTML.Tag.DIV || t == HTML.Tag.BR || t == HTML.Tag.P)) {
                    cleaned.append("\n");
                    readyForNewline = false;
                }
            }
            
            public void handleSimpleTag(final HTML.Tag t, final MutableAttributeSet a, final int pos) {
                handleStartTag(t, a, pos);
            }
        };
        StringReader reader = new StringReader(xmlString); 
        ParserDelegator delegator = new ParserDelegator();  
        delegator.parse(reader, callback, false);  
        return cleaned.toString(); 
    }
    */
    /*
    public static Document ConvertXmlFromString(String xmlString) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
        DocumentBuilder builder;  
        builder = factory.newDocumentBuilder();  
        return builder.parse(new InputSource(new StringReader(xmlString))); 
    }
    */
    
    public static Document ConvertXmlFromString(String xmlString) {
        return Jsoup.parse(xmlString); 
    }
    
    
    
    public static String ConvertHtmlToString(Document doc) {
        //HtmlToPlainText formatter = new HtmlToPlainText();
        //return formatter.getPlainText(doc);
        StringBuilder answer = new StringBuilder(); 
        Elements elementList = doc.select("font"); 
        for (Element element : elementList) {
            answer.append(element.text()).append("\n"); 
        }
        return answer.toString(); 
    }
    
    // </editor-fold>
    
}
