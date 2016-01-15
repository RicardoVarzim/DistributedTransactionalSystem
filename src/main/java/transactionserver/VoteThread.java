/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionserver;

import transaction.SubTransaction;
import java.rmi.RemoteException;
import java.util.*;
import rmi.AccountIf;

/**
 *
 * @author ricardo
 */

class VoteThread extends Thread{
    private List<Boolean> votes;
    //private Cohort cohort;
    private AccountIf account;
    private SubTransaction st;
    private long id;

    public VoteThread(long id, List<Boolean> votes, AccountIf account, SubTransaction st){
        this.votes = votes;
        this.account = account;
        this.st = st;
        this.id = id;
    }

    public void run(){
        try {
            
            //verificar disponibilidade de saldo
            float temp_amount = account.getAmount();
            if(!st.getBalance()){
                temp_amount -= st.getValue();
            }
            votes.add(temp_amount>=0);
            
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}