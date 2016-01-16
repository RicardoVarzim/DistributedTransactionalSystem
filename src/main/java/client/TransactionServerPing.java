/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import rmi.CoordinatorIf;

/**
 *
 * @author ricardo
 */
public class TransactionServerPing extends Thread {
        private CoordinatorIf coordinator;
        private MainWindow gui;
        
        public TransactionServerPing(CoordinatorIf coordinator, MainWindow gui) {
            this.coordinator = coordinator;
            this.gui = gui;
        }

        @Override
        public void run(){
            while(true){
                
                try {
                    gui.setBanks(coordinator.getAvailableBanks());
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    
}
