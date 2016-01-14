/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction;

import java.io.Serializable;

/**
 *
 * @author ricardo
 */
public class SubTransaction implements Serializable {
    
    private String bankname;
    private float value;
    private boolean balance; // 0 - withdraw // 1 - deposit
    private String account;
    
    public SubTransaction(String bank, String account,float value, boolean balance){
        this.bankname = bank;
        this.value = value;
        this.balance = balance;
        this.account = account;
    }

    /**
     * @return the bankname
     */
    public String getBankname() {
        return bankname;
    }

    /**
     * @param bankname the bankname to set
     */
    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    /**
     * @return the value
     */
    public float getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(float value) {
        this.value = value;
    }

    /**
     * @return the balance
     */
    public boolean getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(boolean balance) {
        this.balance = balance;
    }

    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

}
