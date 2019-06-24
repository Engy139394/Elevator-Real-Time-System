/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import esper.Config;
import events.FloorSensorReading;
import events.ObjectDetectionEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EngyE
 */
public class ObjectDetector extends Thread
{
    private ElevatorController elevatorController;
    private int objectrange;
    public ObjectDetector(ElevatorController elevatorController) 
    {
        this.elevatorController = elevatorController;
         this.objectrange=0;
      
    }
          private int random(int min, int max) {
        
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
   
    public void ObjectDetecting() 
    
        {
            objectrange = 0+random(100, 100000);
           
             
        }
 
  
        public void run() {
        while (true) {
            
            try {
                this.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(FloorSensor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Config.sendEvent(new ObjectDetectionEvent(objectrange));
       
           
        }
    }
    
}
