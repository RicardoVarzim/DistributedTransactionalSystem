/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionserver;

import transaction.SubTransaction;
import java.rmi.RemoteException;
import java.util.*;

/**
 *
 * @author ricardo
 */

class VoteThread extends Thread{
    private List<Boolean> votes;
    private Cohort cohort;
    private SubTransaction st;
    private long id;

    public VoteThread(long id, List<Boolean> votes, Cohort cohort, SubTransaction st){
        this.votes = votes;
        this.cohort = cohort;
        this.st = st;
        this.id = id;
    }

    public void run(){
        try {
            votes.add(cohort.voteRequest(id, st.getValue(),st.getBalance()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}