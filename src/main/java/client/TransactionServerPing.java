/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import rmi.TransactionManagerIf;

/**
 *
 * @author ricardo
 */
public class TransactionServerPing extends Thread {
        private TransactionManagerIf coordinator;
        private MainWindow gui;
        
        public TransactionServerPing(TransactionManagerIf coordinator, MainWindow gui) {
            this.coordinator = coordinator;
            this.gui = gui;
        }

        @Override
        public void run(){
            while(true){
                
                try {
                    gui.setBanks(coordinator.getAvailableBanks());
                    Thread.sleep(30000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    
}
