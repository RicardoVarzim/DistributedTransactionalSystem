/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ricardo
 */
class Transaction implements Serializable{
    
    private ArrayList<SubTransaction> subTransactions;
    private long transId;
    
    public Transaction(ArrayList<SubTransaction> list){
        this.subTransactions = list;
        this.transId = new Date().getTime();//TODO: 
    }
    
    public Transaction(){
        this.transId = new Date().getTime();//TODO: 
    }

    /**
     * @return the subTransactions
     */
    public ArrayList<SubTransaction> getSubTransactions() {
        return subTransactions;
    }

    /**
     * @param subTransactions the subTransactions to set
     */
    public void setSubTransactions(ArrayList<SubTransaction> subTransactions) {
        this.subTransactions = subTransactions;
    }

    /**
     * @return the transId
     */
    public long getTransId() {
        return transId;
    }

    /**
     * @param transId the transId to set
     */
    public void setTransId(long transId) {
        this.transId = transId;
    }
    
}
