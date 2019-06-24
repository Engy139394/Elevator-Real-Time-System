/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import esper.Config;
import events.WeightSensorReading;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EngyE
 */
public class WeightSensor extends Thread{

    private ElevatorController elevatorController;
    private int weight;
      private int random(int min, int max) {
        
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
  
    public void idle() {
        {
            weight = 0+random(40, 1000);
        }
    }
    
    public WeightSensor(ElevatorController elevatorController) {
       this.elevatorController = elevatorController;
        this.weight = 0;
      
    }
        @Override
    public void run() {
        while (true) {
            
            try {
                this.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(WeightSensor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Config.sendEvent(new WeightSensorReading(weight));
           
        }
    }
    
    
}
