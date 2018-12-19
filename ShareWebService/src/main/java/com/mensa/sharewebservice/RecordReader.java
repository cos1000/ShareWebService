/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice;

import com.mensa.sharewebservice.dao.RecordHandler;
import com.mensa.sharewebservice.dao.ShareInformationHandler;
import com.mensa.sharewebservice.dao.TradingHandler;
import com.mensa.sharewebservice.dao.TradingIndexHandler;
import com.mensa.sharewebservice.dao.TradingShareHandler;
import com.mensa.sharewebservice.model.ShareInformation;
import com.mensa.sharewebservice.model.Trading;
import com.mensa.sharewebservice.model.TradingIndex;
import com.mensa.sharewebservice.model.TradingShare;
import com.mensa.sharewebservice.util.Common;
import com.mensa.sharewebservice.util.Converter;
import com.mensa.sharewebservice.util.FileHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.Conversation;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matt_
 */
public class RecordReader {

    private enum CurrentStatus {
        None, 
        WriteTradingIndex, 
        WriteTradingShare, 
        WriteTradingShareOpening
    }
    
    private final Logger log;
    private CurrentStatus status; 
    private boolean isFinished = false; 
    private HashMap<Integer, TradingShare> tradingShareList; 
    
    // <editor-fold desc="Const Variable">
    private final CharSequence HANGSENGINDEX; 
    private final CharSequence ENTERPRISESINDEX; 
    private final CharSequence AFFCORPINDEX; 
    private final CharSequence CODENAMEOFSTOCK; 
    private final CharSequence CODENAMEOFSTOCKSALESRECORD; 
    private final CharSequence TRADINGHALTED; 
    private final CharSequence TRADINGSUSPENDED; 
    private final String SEPARATE; 
    private final int MAXSHAREID; 
    //private final String DEBUGFILENAME; 
    // </editor-fold>
     
    
    // <editor-fold desc="Testing Main">
    public static void main(String[] args) {
        String filename = "C:\\Temp\\d180502e.txt";
        RecordReader recordReader = new RecordReader(); 
        boolean answer = false; 
        try {
            answer = recordReader.readFromFile(filename); 
        } catch (IOException e)  {
            e.printStackTrace();
        } 
        System.out.println("Finished: " + Boolean.toString(answer)); 
        System.exit(0);
    }
    // </editor-fold>
    
    // <editor-fold desc="Constructor">
    public RecordReader() {
        this.HANGSENGINDEX = "HANG SENG INDEX";
        this.ENTERPRISESINDEX = "ENTERPRISES INDEX";
        this.AFFCORPINDEX = "AFF. CORP. INDEX";
        this.CODENAMEOFSTOCK = "CODE  NAME OF STOCK    CUR PRV.CLO./    ASK/    HIGH/      SHARES TRADED/";
        this.CODENAMEOFSTOCKSALESRECORD = "CODE  NAME OF STOCK    SALES RECORD";
        this.TRADINGHALTED = "TRADING HALTED"; 
        this.TRADINGSUSPENDED = "TRADING SUSPENDED";
        this.SEPARATE = "-------------------------------------------------------------------------------";
        this.status = CurrentStatus.None; 
        this.MAXSHAREID = 10000; 
        //this.DEBUGFILENAME = "C:\\Temp\\debug.txt"; 
        log = LoggerFactory.getLogger(RecordReader.class);
    }
    // </editor-fold>
    
    // <editor-fold desc="Reader">
    
    /**
     *
     * @param filename, example: d180831e.txt
     * @return
     * @throws IOException
     */
    public boolean readFromFile(String filename) throws IOException {
        boolean answer = true; 
        File file = new File(filename); 
        LocalDateTime transaction_date = Converter.ConvertDateFromFilename(file.getName()); 
        if (answer) answer = deleteOldRecords(transaction_date); 
        if (answer) answer = this.readFromString(transaction_date, FileHandler.ReadFromFile(filename)); 
        return answer; 
    }
    
    public boolean readFromString(LocalDateTime transaction_date, String records) throws IOException {
        String[] lines = records.split("\n"); 
        String aboveLine = ""; 
        boolean answer = true; 
        boolean isFirstLine = false; 
        int lineCounter = 0; 
        int current_share_id = 0; 
        StringBuilder shareDetail = new StringBuilder(); 
        
        tradingShareList = new HashMap<Integer, TradingShare>(); 
        RecordHandler.setTransaction();
        isFinished = false; 
        //int testing = 0; 
        for (String line : lines) {
            //System.out.println(Integer.toString(++testing) + ": " + status.toString() + ", " + Boolean.toString(answer));
            if (line.contains(HANGSENGINDEX)) {
                answer = writeTradingIndex(transaction_date, 1, line); 
            } else if (line.contains(ENTERPRISESINDEX)) {
                answer = writeTradingIndex(transaction_date, 2, line); 
            } else if (line.contains(AFFCORPINDEX)) {
                answer = writeTradingIndex(transaction_date, 3, line); 
            } else if (line.contains(CODENAMEOFSTOCK)) {
                status = CurrentStatus.WriteTradingShare; 
            } else if (line.contains(CODENAMEOFSTOCKSALESRECORD)) {
                status = CurrentStatus.WriteTradingShareOpening; 
            } else if (status == CurrentStatus.WriteTradingShare) {
                if (++lineCounter >= 3) {
                    if (SEPARATE.equals(line)) {
                        status = CurrentStatus.None; 
                    } else if (line.contains(TRADINGHALTED)) {
                    } else if (line.contains(TRADINGSUSPENDED)) {
                    } else if (line.isEmpty()) {
                    } else if (removeFormatString(line).length() < 74) {
                    } else if (!isFirstLine) {
                        isFirstLine = true; 
                    } else {
                        answer = writeTradingShare(transaction_date, removeFormatString(aboveLine), removeFormatString(line)); 
                        if (isFinished) status = CurrentStatus.None; 
                        isFirstLine = false; 
                    }
                }
            } else if (status == CurrentStatus.WriteTradingShareOpening) {
                String current_line = removeFormatString(line); 
                if (SEPARATE.equals(line)) {
                    readFromShareDetail(transaction_date, current_share_id, shareDetail.toString()); 
                    status = CurrentStatus.None; 
                    break; 
                } if (current_line.length() < 5) {
                    continue; 
                } else {
                    Integer share_id = Common.TryParseToInteger(current_line.substring(0, 5).trim()); 
                    if (share_id != null) {
                        if (shareDetail.length() > 0) {
                            readFromShareDetail(transaction_date, current_share_id, shareDetail.toString()); 
                            shareDetail = new StringBuilder(); 
                        }
                        current_share_id = share_id; 
                        if (current_share_id >= MAXSHAREID) {
                            status = CurrentStatus.None; 
                            break; 
                        }
                    } 
                    shareDetail.append(current_line.substring(23)); 
                }
            }
            
            if (!line.isEmpty()) aboveLine = line; 
            if (!answer) break; 
        }
        if (answer) {
            Set set = tradingShareList.entrySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry)iterator.next();
                TradingShare tradingShare = (TradingShare)mentry.getValue(); 
                RecordHandler.getEntityManager().persist(tradingShare);
            }
        }
        
        if (answer) answer = this.writeTrading(transaction_date); 
        
        if (answer) {
            RecordHandler.setCommit();
        } else {
            RecordHandler.setRollback();
        }
        RecordHandler.getEntityManager().clear();
        
        /*
        if (RecordHandler.getEntityManager() != null) {
            if (answer) {
                RecordHandler.getEntityManager().getTransaction().commit();
            } else {
                RecordHandler.getEntityManager().getTransaction().rollback();
            }
        }
        */
        return answer; 
    }
    
    public boolean readFromShareDetail(LocalDateTime transaction_date, Integer share_id, String shareDetail) {
        boolean answer = true; 
        CharSequence separate = "-";
        int startChar = shareDetail.indexOf("["); 
        int endChar = shareDetail.indexOf("]"); 
        BigDecimal opening = null; 
        Integer count_traded = 0; 
        Integer count_big_traded = 0; 
        BigDecimal big_traded_price = BigDecimal.valueOf(2000000); 
        
        if (startChar >= 0 && endChar >= 0) {
            String[] tradingList = shareDetail.substring(startChar + 1, endChar).split(" "); 
            for (int counter = 0; counter < tradingList.length; counter++) {
                if (tradingList[counter].contains(separate)) count_traded++; 
                String[] recordList = tradingList[counter].replace("C", "").replace("D", "").replace("M", "").replace("P", "").replace("U", "").replace("X", "").replace("Y", "").split("-"); 
                if (recordList.length != 2) continue; 
                BigDecimal numberOfShare = Common.TryParseToDecimal(recordList[0].trim()); 
                BigDecimal price = Common.TryParseToDecimal(recordList[1].trim()); 
                if (numberOfShare == null || price == null) continue; 
                if (opening == null) opening = price; 
                if (numberOfShare.multiply(price).compareTo(big_traded_price) > 0) count_big_traded++; 
            }
            
            int startChar_afternoon = shareDetail.substring(endChar+1).indexOf("["); 
            int endChar_afternoon = shareDetail.substring(endChar+1).indexOf("]"); 
            if (startChar_afternoon >= 0 && endChar_afternoon >= 0)
            {
                tradingList = shareDetail.substring(endChar+1).substring(startChar_afternoon + 1, endChar_afternoon).split(" "); 
                for (int counter = 0; counter < tradingList.length; counter++) {
                    if (tradingList[counter].contains(separate)) count_traded++; 
                    String[] recordList = tradingList[counter].replace("C", "").replace("D", "").replace("M", "").replace("P", "").replace("U", "").replace("X", "").replace("Y", "").split("-"); 
                    if (recordList.length != 2) continue; 
                    BigDecimal numberOfShare = Common.TryParseToDecimal(recordList[0].trim()); 
                    BigDecimal price = Common.TryParseToDecimal(recordList[1].trim()); 
                    if (numberOfShare == null || price == null) continue; 
                    if (opening == null) opening = price; 
                    if (numberOfShare.multiply(price).compareTo(big_traded_price) > 0) count_big_traded++; 
                }
            }
            
            TradingShare tradingShare = tradingShareList.get(share_id); 
            if (tradingShare != null) {
                tradingShare.setOpening(opening);
                tradingShare.setCount_traded(count_traded);
                tradingShare.setCount_big_traded(count_big_traded);
            }
            
            /*
            TradingShareHandler tradingShareHandler = new TradingShareHandler(); 
            TradingShare tradingShare = new TradingShare(); 
            tradingShare.setTransaction_date(transaction_date);
            tradingShare.setShare_id(share_id);
            tradingShare = tradingShareHandler.Get(tradingShare); 
            if (tradingShare != null) {
                tradingShare.setOpening(opening);
                tradingShare.setCount_traded(count_traded);
                tradingShare.setCount_big_traded(count_big_traded);
                answer = tradingShareHandler.Update(tradingShare); 
            }
            */
        }
        return answer; 
    }
    
    public String removeFormatString(String line) {
        final CharSequence fontXml = "</font></pre><pre><font size='1'>";
        final CharSequence fontXml2 = "</font></pre><pre><font size=\"1\">";
        final CharSequence blank = "";
        final CharSequence amp = "&amp;";
        final CharSequence ampSignal = "&";
        return line.replace(fontXml, blank).replace(fontXml2, blank).replace(amp, ampSignal); 
    }

    // </editor-fold>
    
    // <editor-fold desc="Write Record">
    
    public boolean writeTradingIndex(LocalDateTime transaction_date, Integer index, String line) {
        //TradingIndexHandler tradingIndexHandler = new TradingIndexHandler(); 
        TradingIndex tradingIndex = new TradingIndex(); 
        
        String string_morning_closing = line.substring(36, 44).trim(); 
        String string_afternoon_closing = line.substring(44, 52).trim(); 
        String string_previous_closing = line.substring(54, 62).trim(); 
        
        BigDecimal morning_closing = null; 
        BigDecimal afternoon_closing = null; 
        BigDecimal previous_closing = null; 
        BigDecimal closing = null; 
        
        if (!"-".equals(string_morning_closing.trim())) morning_closing = Common.TryParseToDecimal(string_morning_closing.trim()); 
        if (!"-".equals(string_afternoon_closing.trim())) afternoon_closing = Common.TryParseToDecimal(string_afternoon_closing.trim()); 
        if (!"-".equals(string_previous_closing.trim())) previous_closing = Common.TryParseToDecimal(string_previous_closing.trim()); 
        closing = (afternoon_closing == null) ? morning_closing : afternoon_closing; 
        
        tradingIndex.setTransaction_date(transaction_date);
        tradingIndex.setIndex(index);
        
        tradingIndex.setMorning_closing(morning_closing);
        tradingIndex.setClosing(closing);
        tradingIndex.setPrevious_closing(previous_closing);
        tradingIndex.setCreated_at(LocalDateTime.now());
        tradingIndex.setUpdated_at(LocalDateTime.now());
        
        //SetEntityManager(); 
        //RecordHandler.setTransaction();
        RecordHandler.getEntityManager().persist(tradingIndex);
        //RecordHandler.setCommit();
        return true; 
        //return tradingIndexHandler.Create(tradingIndex); 
    }
    
    public boolean writeShareInformation(Integer share_id, String name) {
        //ShareInformationHandler shareInformationHandler = new ShareInformationHandler(); 
        ShareInformation shareInformation = new ShareInformation(); 
        ShareInformationHandler shareInformationHandler = new ShareInformationHandler(); 

        shareInformation.setShare_id(share_id);
        shareInformation.setCreated_at(LocalDateTime.now());
        if (shareInformationHandler.Exist(shareInformation)) shareInformation = shareInformationHandler.Get(shareInformation); 
        
        shareInformation.setName(name);
        shareInformation.setUpdated_at(LocalDateTime.now());
        
        //SetEntityManager(); 
        //RecordHandler.setTransaction();
        RecordHandler.getEntityManager().persist(shareInformation);
        //RecordHandler.setCommit();
        return true; 
        //return shareInformationHandler.Create(shareInformation); 
    }
    
    public boolean writeTrading(LocalDateTime transaction_date) {
        //TradingHandler tradingHandler = new TradingHandler(); 
        Trading trading = new Trading(); 

        trading.setTransaction_date(transaction_date);
        trading.setCreated_at(LocalDateTime.now());
        trading.setUpdated_at(LocalDateTime.now());

        //SetEntityManager(); 
        //RecordHandler.setTransaction();
        RecordHandler.getEntityManager().persist(trading);
        //RecordHandler.setCommit();
        return true; 
        //return tradingHandler.Create(trading); 
    }
    
    public boolean writeTradingShare(LocalDateTime transaction_date, String above_line, String line) {
        
        String string_share_id = above_line.substring(1, 6); 
        String string_closing = line.substring(27, 36); 
        String string_previous_closing = above_line.substring(27, 36); 
        String string_ask = above_line.substring(36, 45); 
        String string_bid = line.substring(36, 45); 
        String string_high = above_line.substring(45, 54); 
        String string_low = line.substring(45, 54); 
        String string_shares_traded = above_line.substring(54, 74); 
        String string_turnover = line.substring(54, 74); 
        boolean isIndexShare = "*".equals(above_line.substring(0, 1)); 

//        log.debug("Write Trading Share");
//        log.debug(string_share_id);
//        log.debug(string_closing);
//        log.debug(string_previous_closing);
//        log.debug(string_ask);
//        log.debug(string_bid);
//        log.debug(string_high);
//        log.debug(string_low);
//        log.debug(string_shares_traded);
//        log.debug(string_turnover);
//        log.debug(isIndexShare? "Y" : "N");
        
        Integer share_id = null; 
        BigDecimal closing = null; 
        BigDecimal previous_closing = null; 
        BigDecimal ask = null; 
        BigDecimal bid = null; 
        BigDecimal high = null; 
        BigDecimal low = null; 
        BigDecimal shares_traded = null; 
        BigDecimal turnover = null; 
        
        boolean answer = true; 

        if (answer && !"-".equals(string_share_id.trim())) {
            share_id = Common.TryParseToInteger(string_share_id.trim());
            answer = share_id != null; 
            if (answer && share_id >= MAXSHAREID) {
                isFinished = true; 
                return true; 
            }
        } 
        if (answer && !"-".equals(string_closing.trim()) && !"N/A".equals(string_closing.trim())) {
            closing = Common.TryParseToDecimal(string_closing.trim()); 
            answer = closing != null; 
        }
        if (answer && !"-".equals(string_previous_closing.trim()) && !"N/A".equals(string_previous_closing.trim())) {
            previous_closing = Common.TryParseToDecimal(string_previous_closing.trim()); 
            answer = previous_closing != null; 
        }
        if (answer && !"-".equals(string_ask.trim()) && !"N/A".equals(string_ask.trim())) {
            ask = Common.TryParseToDecimal(string_ask.trim()); 
            answer = ask != null; 
        }
        if (answer && !"-".equals(string_bid.trim()) && !"N/A".equals(string_bid.trim())) {
            bid = Common.TryParseToDecimal(string_bid.trim()); 
            answer = bid != null; 
        }
        if (answer && !"-".equals(string_high.trim()) && !"N/A".equals(string_high.trim())) {
            high = Common.TryParseToDecimal(string_high.trim()); 
            answer = high != null; 
        }
        if (answer && !"-".equals(string_low.trim()) && !"N/A".equals(string_low.trim())) {
            low = Common.TryParseToDecimal(string_low.trim()); 
            answer = low != null; 
        }
        if (answer && !"-".equals(string_shares_traded.trim()) && !"N/A".equals(string_shares_traded.trim())) {
            shares_traded = Common.TryParseToDecimal(string_shares_traded.trim()); 
            answer = shares_traded != null; 
        }
        if (answer && !"-".equals(string_turnover.trim()) && !"N/A".equals(string_turnover.trim())) {
            turnover = Common.TryParseToDecimal(string_turnover.trim()); 
            answer = turnover != null; 
        }
        
        if (answer) {
            //TradingShareHandler tradingShareHandler = new TradingShareHandler(); 
            TradingShare tradingShare = new TradingShare(); 
            tradingShare.setShare_id(share_id);
            tradingShare.setTransaction_date(transaction_date);
            tradingShare.setClosing(closing);
            tradingShare.setPrevious_closing(previous_closing);
            tradingShare.setAsk(ask);
            tradingShare.setBid(bid);
            tradingShare.setHigh(high);
            tradingShare.setLow(low);
            tradingShare.setShares_traded(shares_traded);
            tradingShare.setTurnover(turnover);
            if (isIndexShare) tradingShare.setIs_index_share(1);
            tradingShare.setCreated_at(LocalDateTime.now());
            tradingShare.setUpdated_at(LocalDateTime.now());
            
            //SetEntityManager(); 
            //RecordHandler.setTransaction();
            //RecordHandler.getEntityManager().persist(tradingShare);
            //RecordHandler.setCommit();
            
            tradingShareList.put(share_id, tradingShare); 
            
            //answer = tradingShareHandler.Create(tradingShare); 
        }
        
        if (answer) writeShareInformation(share_id, above_line.substring(7, 24)); 
        
        return answer; 
    }

/*    
    private void SetEntityManager() {
        if (RecordHandler.getEntityManager() == null) {
            System.out.println("SetEntityManager");
            //RecordHandler recordHandler = new RecordHandler(); 
            //entityManager = RecordHandler.getEntityManager(); 
            RecordHandler.setEntityManager();
            RecordHandler.getEntityManager().getTransaction().begin(); 
        } 
    }
*/

    // </editor-fold>
    
    // <editor-fold desc="Data Controller">
    
    public boolean deleteOldRecords(LocalDateTime transaction_date) {
        TradingHandler tradingHandler = new TradingHandler(); 
        Trading trading = new Trading(); 
        trading.setTransaction_date(transaction_date);
        RecordHandler handler = new RecordHandler(); 
        handler.setTransaction();
        try {
            TradingShareHandler tradingShareHandler = new TradingShareHandler(); 
            List<TradingShare> tradingShareList = tradingShareHandler.Get(transaction_date); 

            for (TradingShare tradingShare : tradingShareList) {
                handler.getEntityManager().remove(tradingShare);
            }

            TradingIndexHandler tradingIndexHandler = new TradingIndexHandler(); 
            List<TradingIndex> tradingIndexList = tradingIndexHandler.Get(transaction_date); 

            for (TradingIndex tradingIndex : tradingIndexList) {
                handler.getEntityManager().remove(tradingIndex);
            }

            if (tradingHandler.Exist(trading)) {
                trading = tradingHandler.Get(trading); 
                handler.getEntityManager().remove(trading);
            }

            handler.setCommit(); 
            return true; 
        } catch (Exception e) {
            handler.setRollback();
            return false; 
        }
    }
    
    // </editor-fold>
}
