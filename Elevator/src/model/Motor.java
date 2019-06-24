/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import esper.Config;
import events.FloorButtonPressing;
import events.TimeEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EngyE
 */
public class Motor extends Thread 
{

    private final ElevatorController elevatorController;
    private Alarm alarm;
    private String motorState;

    public Motor(ElevatorController elevatorController) {
        this.elevatorController = elevatorController;
        this.motorState = "";
    }


    public void setMotorState(String newMotorState) 
    {

        motorState = newMotorState;

    }

    public String getMotorState() {
        return motorState;
    }
    

    @Override
    public void run() {
        //To change body of generated methods, choose Tools | Templates.
        while (true) 
        {
           if(this.motorState=="Stop"|| this.motorState=="Slowly Stopping")
           {
              elevatorController.getTimer().decreaseForElevator();
           }
        

            try {
                this.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Motor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
