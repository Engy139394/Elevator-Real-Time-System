/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import esper.Config;
import events.FloorSensorReading;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EngyE
 */
public class FloorButton 
{
    private int floor1;
    private int floor2;
    private int floor3;
    private int floor4;
    private int floor5;
    private int floor6;
    private int floor7;
    private int floor8;
    private int floor9;
    private int floor10;
   
    
    private ElevatorController elevatorController;

    public FloorButton(ElevatorController elevatorController)
    {
         this.floor1 = 0;
         this.floor2 = 0;
         this.floor3 = 0;
         this.floor4 = 0;
         this.floor5 = 0;
         this.floor6 = 0;
         this.floor7= 0;
         this.floor8= 0;
         this.floor9 = 0;
         this.floor10 =0;
         
        this.elevatorController = elevatorController;
    }
      public void setState(int floor1, int floor2, int floor3 , int floor4,int floor5 , int floor6, int floor7 , int floor8,int floor9, int floor10) 
     { 
          this.floor1=floor1;
          this.floor2=floor2;
          this.floor3=floor3;
          this.floor4=floor4;
          this.floor5=floor5;
          this.floor6=floor6;
          this.floor7=floor7;
          this.floor8=floor8;
          this.floor9=floor9;
          this.floor10=floor10;
        
        //elevatorController.getDoorGui().getjButton1().disable();
    }

    public int getFloor1() {
        return floor1;
    }

    public int getFloor2() {
        return floor2;
    }

    public int getFloor3() {
        return floor3;
    }

    public int getFloor4() {
        return floor4;
    }

    public int getFloor5() {
        return floor5;
    }

    public int getFloor6() {
        return floor6;
    }

    public int getFloor7() {
        return floor7;
    }

    public int getFloor8() {
        return floor8;
    }

    public int getFloor9() {
        return floor9;
    }

    public int getFloor10() {
        return floor10;
    }

    public ElevatorController getElevatorController() {
        return elevatorController;
    }

   
    
}
