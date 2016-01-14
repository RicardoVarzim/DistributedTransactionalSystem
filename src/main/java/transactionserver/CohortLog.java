/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionserver;

import java.io.Serializable;
import transactionserver.Cohort.CohortStatus;

/**
 *
 * @author ricardo
 */
class CohortLog implements Serializable{
    
    private long id;
    private CohortStatus status;

    public CohortLog(long id, CohortStatus status) {
        this.id = id;
        this.status = status;
    }

    public CohortLog(){
    }

    public String toString(){
        return this.id + this.status.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CohortStatus getStatus() {
        return status;
    }

    public void setStatus(CohortStatus status) {
        this.status = status;
    }

}
