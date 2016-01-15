/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionserver;

import transaction.Transaction;
import transactionserver.Coordinator.CoordinatorStatus;

/**
 *
 * @author ricardo
 */
public class CoordinatorLog {

    private CoordinatorStatus status;
    private long id;
    private Transaction transaction;

    public CoordinatorLog(long id, CoordinatorStatus status) {
        this.id = id;
        this.status = status;
    }

    public CoordinatorLog(){
    }

    public CoordinatorLog(Transaction transaction){
        this.transaction = transaction;
        this.id = transaction.getTransId();
        this.status = CoordinatorStatus.INIT;
    }

    public CoordinatorStatus getStatus() {
        return status;
    }

    public void setStatus(CoordinatorStatus status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    
}
