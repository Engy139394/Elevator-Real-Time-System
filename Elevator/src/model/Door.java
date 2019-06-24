/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import esper.Config;
import events.CloseDoorButtonPressing;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EngyE
 */
public class Door extends Thread {
    
    private boolean DoorOpen;
    private boolean DoorClose;
     private ElevatorController elevatorController;
    // final Object lock;
     
     public Door(ElevatorController elevatorController) // Object lock)
     {
        this.elevatorController = elevatorController; 
        //this.lock=lock;
      
    }
    

    public void setDoorOpen(boolean DoorOpen) {
        this.DoorOpen = DoorOpen;
    }

    public void setDoorClosed(boolean DoorClosed) {
        this.DoorClose = DoorClosed;
    }
   
  
    public boolean isDoorClosed() {
        return DoorClose;
    }
    


    public boolean isDoorOpen() {
        return DoorOpen;
    }
    
    
    
       @Override
    public void run() {
       //  super.run();
        
        while (true) {
            if(this.isDoorOpen()==true)
            {
                elevatorController.getDoorOpenGui().setVisible(true);
                elevatorController.getDoorGui().setVisible(false);
                elevatorController.notifyDoorState();
                this.setDoorOpen(false);
               // elevatorController.playMusic("C:\\Users\\EngyE\\Documents\\NetBeansProjects\\Elevator\\Music\\openElevator.aif");

            }
            //synchronized (lock){
             if(this.isDoorClosed()==true)
            {
               elevatorController.playMusic("src\\Music\\closeElevator.wav");
               Config.sendEvent(new CloseDoorButtonPressing(false));  
               this.setDoorClosed(false);

            }
           
            try {
                this.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Door.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    }
    
    
    
}

