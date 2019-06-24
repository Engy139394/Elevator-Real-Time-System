/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import esper.Config;
import events.FloorSensorReading;
import events.WeightSensorReading;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EngyE
 */
public class FloorSensor extends Thread{
     private ElevatorController elevatorController;
    private int Vfloor;
    private int Cfloor;
          private int random(int min, int max) {
        
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
   
    public void Floor() {
        {
            Vfloor = 0+random(1, 10);
            Cfloor = 0+random(1, 10);
            System.out.print("ay");
             
        }
    }
    
      public void raiseFloor() {
        Vfloor = Vfloor+1;
        
    }
       public void decreaseFloor() {
        Vfloor=Vfloor-1;
    }
    public FloorSensor(ElevatorController elevatorController) {
        this.elevatorController = elevatorController;
        this.Cfloor = 0;
        this.Vfloor = 0;
      
    }
        public void run() {
        while (true) {
            
            try {
                this.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(FloorSensor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Config.sendEvent(new FloorSensorReading(Cfloor,Vfloor));
       
           
        }
    }
 
    
}
