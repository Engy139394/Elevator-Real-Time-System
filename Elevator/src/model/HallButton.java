/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import esper.Config;
import events.HallButtonPressing;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EngyE
 */
public class HallButton extends Thread {
    
    private boolean upstate;
    private boolean downstate;
    private ElevatorController elevatorController;

    public HallButton(ElevatorController elevatorController) {
        this.upstate = false;
         this.downstate = false;
        this.elevatorController = elevatorController;
    }

    public boolean isUpstate() {
        return upstate;
    }

    public boolean isDownstate() {
        return downstate;
    }
   

    public void setState(boolean upstate, boolean downstate) {
        this.upstate=upstate;
        this.downstate=downstate;       
    }

}
