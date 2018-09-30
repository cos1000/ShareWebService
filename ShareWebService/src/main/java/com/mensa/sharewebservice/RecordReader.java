/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice;

import com.mensa.sharewebservice.dal.RecordHandler;
import com.mensa.sharewebservice.dal.ShareInformationHandler;
import com.mensa.sharewebservice.dal.TradingHandler;
import com.mensa.sharewebservice.dal.TradingIndexHandler;
import com.mensa.sharewebservice.dal.TradingShareHandler;
import com.mensa.sharewebservice.entity.ShareInformation;
import com.mensa.sharewebservice.entity.Trading;
import com.mensa.sharewebservice.entity.TradingIndex;
import com.mensa.sharewebservice.entity.TradingShare;
import com.mensa.sharewebservice.util.Common;
import com.mensa.sharewebservice.util.Converter;
import com.mensa.sharewebservice.util.FileHandler;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javax.enterprise.context.Conversation;
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
    
    private final Logger log = LoggerFactory.getLogger(RecordReader.class);
    private CurrentStatus status; 
    
    // <editor-fold desc="Const Variable">
    private final CharSequence HANGSENGINDEX; 
    private final CharSequence ENTERPRISESINDEX; 
    private final CharSequence AFFCORPINDEX; 
    private final CharSequence CODENAMEOFSTOCK; 
    private final CharSequence CODENAMEOFSTOCKSALESRECORD; 
    private final CharSequence TRADINGHALTED; 
    private final CharSequence TRADINGSUSPENDED; 
    private final String SEPARATE; 
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
        LocalDateTime transaction_date = Converter.ConvertDateFromFilename(filename); 
        if (answer) answer = deleteOldRecords(transaction_date); 
        if (answer) answer = this.readFromString(transaction_date, FileHandler.ReadFromFile(filename)); 
        if (answer) answer = this.writeTrading(transaction_date); 
        return answer; 
    }
    
    public boolean readFromString(LocalDateTime transaction_date, String records) {
        boolean isFinished = false; 
        String[] lines = records.split("\\n"); 
        String aboveLine = ""; 
        boolean answer = true; 
        boolean isFirstLine = false; 
        int lineCounter = 0; 
        int current_share_id = 0; 
        StringBuilder shareDetail = new StringBuilder(); 
        
        for (String line : lines) {
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
                if (lineCounter++ >= 3) {
                    if (SEPARATE.equals(line)) {
                        status = CurrentStatus.None; 
                    } else if (line.contains(TRADINGHALTED)) {
                    } else if (line.contains(TRADINGSUSPENDED)) {
                    } else if (line.isEmpty()) {
                    } else if (!isFirstLine) {
                        isFirstLine = true; 
                    } else {
                        writeTradingShare(transaction_date, removeFormatString(aboveLine), removeFormatString(line), isFinished); 
                        if (isFinished) status = CurrentStatus.None; 
                        isFirstLine = false; 
                    }
                }
            } else if (status == CurrentStatus.WriteTradingShareOpening) {
                String current_line = removeFormatString(line); 
                if (SEPARATE.equals(line)) {
                    status = CurrentStatus.None; 
                    break; 
                } if (line.trim().length() < 5) {
                    continue; 
                } else {
                    Integer share_id = Common.TryParseToInteger(current_line.substring(0, 5)); 
                    if (share_id != null) {
                        if (shareDetail.length() > 0) {
                            shareDetail = new StringBuilder(); 
                        }
                        current_share_id = share_id; 
                        if (current_share_id >= 10000) {
                            status = CurrentStatus.None; 
                        }
                    } 
                    shareDetail.append(current_line.substring(23)); 
                }
            }
            
            aboveLine = line; 
            if (!answer) break; 
        }
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
                BigDecimal numberOfShare = Common.TryParseToDecimal(recordList[0]); 
                BigDecimal price = Common.TryParseToDecimal(recordList[1]); 
                if (numberOfShare == null || price == null) continue; 
                if (opening == null) opening = price; 
                if (numberOfShare.multiply(price).compareTo(big_traded_price) > 0) count_big_traded++; 
            }
            
            int startChar_afternoon = shareDetail.substring(endChar+1).indexOf("["); 
            int endChar_afternoon = shareDetail.substring(endChar+1).indexOf("]"); 
            tradingList = shareDetail.substring(startChar_afternoon + 1, endChar_afternoon).split(" "); 
            for (int counter = 0; counter < tradingList.length; counter++) {
                if (tradingList[counter].contains(separate)) count_traded++; 
                String[] recordList = tradingList[counter].replace("C", "").replace("D", "").replace("M", "").replace("P", "").replace("U", "").replace("X", "").replace("Y", "").split("-"); 
                if (recordList.length != 2) continue; 
                BigDecimal numberOfShare = Common.TryParseToDecimal(recordList[0]); 
                BigDecimal price = Common.TryParseToDecimal(recordList[1]); 
                if (numberOfShare == null || price == null) continue; 
                if (opening == null) opening = price; 
                if (numberOfShare.multiply(price).compareTo(big_traded_price) > 0) count_big_traded++; 
            }
            
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
        }
        return answer; 
    }
    
    public String removeFormatString(String line) {
        final CharSequence fontXml = "</font></pre><pre><font size='1'>";
        final CharSequence blank = "";
        final CharSequence amp = "&amp;";
        final CharSequence ampSignal = "&";
        return line.replace(fontXml, blank).replace(amp, ampSignal); 
    }

    // </editor-fold>
    
    // <editor-fold desc="Write Record">
    
    public boolean writeTradingIndex(LocalDateTime transaction_date, Integer index, String line) {
        TradingIndexHandler tradingIndexHandler = new TradingIndexHandler(); 
        TradingIndex tradingIndex = new TradingIndex(); 
        
        String string_morning_closing = line.substring(36, 44).trim(); 
        String string_afternoon_closing = line.substring(44, 52).trim(); 
        String string_previous_closing = line.substring(54, 62).trim(); 
        
        BigDecimal morning_closing = null; 
        BigDecimal afternoon_closing = null; 
        BigDecimal previous_closing = null; 
        BigDecimal closing = null; 
        
        if (!"-".equals(string_morning_closing)) morning_closing = Common.TryParseToDecimal(string_morning_closing); 
        if (!"-".equals(string_afternoon_closing)) afternoon_closing = Common.TryParseToDecimal(string_afternoon_closing); 
        if (!"-".equals(string_previous_closing)) previous_closing = Common.TryParseToDecimal(string_previous_closing); 
        closing = (afternoon_closing == null) ? morning_closing : afternoon_closing; 
        
        tradingIndex.setTransaction_date(transaction_date);
        tradingIndex.setIndex(index);
        tradingIndex.setMorning_closing(morning_closing);
        tradingIndex.setClosing(closing);
        tradingIndex.setPrevious_closing(previous_closing);
        tradingIndex.setCreated_at(LocalDateTime.now());
        tradingIndex.setUpdated_at(LocalDateTime.now());
        
        return tradingIndexHandler.Create(tradingIndex); 
    }
    
    public boolean writeShareInformation(Integer share_id, String name) {
        ShareInformationHandler shareInformationHandler = new ShareInformationHandler(); 
        ShareInformation shareInformation = new ShareInformation(); 
        
        shareInformation.setShare_id(share_id);
        shareInformation.setName(name);
        shareInformation.setCreated_at(LocalDateTime.now());
        shareInformation.setUpdated_at(LocalDateTime.now());
        
        return shareInformationHandler.Create(shareInformation); 
    }
    
    public boolean writeTrading(LocalDateTime transaction_date) {
        TradingHandler tradingHandler = new TradingHandler(); 
        Trading maxTrading = tradingHandler.GetMax(); 
        if (maxTrading == null || !maxTrading.getTransaction_date().isAfter(transaction_date)) {
            Trading trading = new Trading(); 
            
            trading.setTransaction_date(transaction_date);
            trading.setCreated_at(LocalDateTime.now());
            trading.setUpdated_at(LocalDateTime.now());
            
            return tradingHandler.Create(trading); 
        } else {
            return false; 
        }
    }
    
    public boolean writeTradingShare(LocalDateTime transaction_date, String above_line, String line, Boolean isFinished) {
        
        String string_share_id = above_line.substring(1, 6); 
        String string_closing = line.substring(29, 36); 
        String string_previous_closing = above_line.substring(29, 36); 
        String string_ask = above_line.substring(36, 45); 
        String string_bid = line.substring(36, 45); 
        String string_high = above_line.substring(45, 54); 
        String string_low = line.substring(45, 54); 
        String string_shares_traded = above_line.substring(54, 74); 
        String string_turnover = above_line.substring(54, 74); 
        boolean isIndexShare = "*".equals(above_line.substring(0, 1)); 

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

        if (answer && !"-".equals(string_share_id)) {
            share_id = Common.TryParseToInteger(string_share_id);
            answer = share_id != null; 
            
            if (answer && share_id >= 10000) {
                isFinished = true; 
                return true; 
            }
        } 
        if (answer && !"-".equals(string_closing)) {
            closing = Common.TryParseToDecimal(string_closing); 
            answer = closing != null; 
        }
        if (answer && !"-".equals(string_previous_closing)) {
            previous_closing = Common.TryParseToDecimal(string_previous_closing); 
            answer = previous_closing != null; 
        }
        if (answer && !"-".equals(string_ask)) {
            ask = Common.TryParseToDecimal(string_ask); 
            answer = ask != null; 
        }
        if (answer && !"-".equals(string_bid)) {
            bid = Common.TryParseToDecimal(string_bid); 
            answer = bid != null; 
        }
        if (answer && !"-".equals(string_high)) {
            high = Common.TryParseToDecimal(string_high); 
            answer = high != null; 
        }
        if (answer && !"-".equals(string_low)) {
            low = Common.TryParseToDecimal(string_low); 
            answer = low != null; 
        }
        if (answer && !"-".equals(string_shares_traded)) {
            shares_traded = Common.TryParseToDecimal(string_shares_traded); 
            answer = shares_traded != null; 
        }
        if (answer && !"-".equals(string_turnover)) {
            turnover = Common.TryParseToDecimal(string_turnover); 
            answer = turnover != null; 
        }
        
        if (answer) {
            TradingShareHandler tradingShareHandler = new TradingShareHandler(); 
            TradingShare tradingShare = new TradingShare(); 
            tradingShare.setTransaction_date(transaction_date);
            tradingShare.setClosing(closing);
            tradingShare.setPrevious_closing(previous_closing);
            tradingShare.setAsk(ask);
            tradingShare.setBid(bid);
            tradingShare.setHigh(high);
            tradingShare.setLow(low);
            tradingShare.setShares_traded(shares_traded);
            tradingShare.setTurnover(turnover);
            tradingShare.setCreated_at(LocalDateTime.now());
            tradingShare.setUpdated_at(LocalDateTime.now());
            answer = tradingShareHandler.Create(tradingShare); 
        }
        
        if (answer) writeShareInformation(share_id, above_line.substring(7, 24)); 
        
        return answer; 
    }
    
    // </editor-fold>
    
    // <editor-fold desc="Data Controller">
    
    public boolean deleteOldRecords(LocalDateTime transaction_date) {
        TradingHandler tradingHandler = new TradingHandler(); 
        Trading trading = new Trading(); 
        trading.setTransaction_date(transaction_date);
        if (tradingHandler.Exist(trading)) {
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
                
                handler.getEntityManager().remove(trading);
                
                handler.setCommit(); 
                return true; 
            } catch (Exception e) {
                handler.setRollback();
                return false; 
            }
        } else {
            return true; 
        }
    }
    
    // </editor-fold>
}
