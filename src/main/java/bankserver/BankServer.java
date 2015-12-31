/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver;

import java.rmi.Naming;
import java.rmi.Remote;

/**
 *
 * @author ricardo
 */
public class BankServer {
    public static void main(String[] args) throws Exception{
        Bank b = new Bank();
        
        for (int i = 0; i < 10; i++) {
            b.makeAccount();
        }
        Naming.rebind("bank", b);
    }
}
