/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;

/**
 *
 * @author EngyE
 */
public class LightBulb 
{
    private boolean redLight;
    private boolean greenLight;
    private ElevatorController elevatorController;

    public LightBulb(ElevatorController elevatorController) {
        this.redLight=false;
        this.greenLight=false;         
        this.elevatorController=elevatorController;
    }

    public void setState(boolean redLight, boolean greenLight) {
        this.redLight=redLight;
        this.greenLight=greenLight;
        if (redLight) {
            elevatorController.getDoorOpenGui().getjTextField1().setBackground(Color.red);
            
        } 
        else if (greenLight) {
           elevatorController.getDoorOpenGui().getjTextField1().setBackground(Color.green);
        }
         
        if(redLight==true && elevatorController.getAlarm().isState()==true)
        {
             elevatorController.getInElevator().getFloorStateTxt2().setBackground(Color.red);
        }

    }
    
}
