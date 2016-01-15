/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionserver;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.AccountIf;
import transaction.SubTransaction;

/**
 *
 * @author ricardo
 */
class CommitVoteThread extends Thread {
    
    private List<Boolean> votes;
    //private Cohort cohort;
    private AccountIf account;
    private SubTransaction st;
    private long id;

    public CommitVoteThread(long id, List<Boolean> votes, AccountIf account, SubTransaction st){
        this.votes = votes;
        this.account = account;
        this.st = st;
        this.id = id;
    }

    public void run(){
        boolean result = false;
        float value = st.getValue();
        if(!st.getBalance()){
            try {
                account.withdraw(value);
                result = true;
            } catch (Exception ex) {
                result = false;
            }
        }else{
            try {
                account.deposit(value);
                result = true;
            } catch (Exception ex) { 
                result = false;
            }
        }
        votes.add(result);
    }
}
