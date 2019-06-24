/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import esper.Config;
import events.EmergencyButtonPressing;
import java.awt.Color;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EngyE
 */
public class Alarm {
   // private Motor motor;
    private boolean state;
    private ElevatorController elevatorController;

    public Alarm (ElevatorController elevatorController) {
        this.state = false;
        this.elevatorController = elevatorController;
    }

    public boolean isState() {
        return state;
    }

   

    

    public void setState(boolean state) throws InterruptedException {
        this.state = state;
        if (this.state==true) 
        {
       // this.elevatorController.getInElevator().getFloorStateTxt2().setBackground(Color.red);
         elevatorController.playMusic("src\\Music\\Alarm.wav"); 
          
   
         

           
        }
        if (this.state==false) 
        {
      this.elevatorController.getInElevator().getFloorStateTxt2().setBackground(Color.black);
        }
    }
}

