/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import esper.Config;
import events.TimeEvent;
import events.WeightSensorReading;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EngyE
 */
public class Timer extends Thread
{
   private ElevatorController elevatorController;
    private int time;
  //  final Object lock;
    public Timer (ElevatorController elevatorController)// Object lock) 
       {
        this.elevatorController = elevatorController;
        this.time= 5000000;
        //this.lock =lock;
      
    }
    public void decreaseForElevator()
    {
       time=5;
        time=time-1;
    }
     public void decreaseForDoor()
    {
        time=4;
        time=time-1;
    }
            @Override
    public void run() {
        while (true) 
        {
            
        // super.run();
       //  synchronized (lock)
         {   
            try 
            {
              this.sleep(1000);
            } catch (InterruptedException ex) 
            {
                Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Config.sendEvent(new TimeEvent(time));
           
        }
    }
}
}
