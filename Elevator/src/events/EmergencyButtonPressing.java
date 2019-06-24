/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

/**
 *
 * @author EngyE
 */
public class EmergencyButtonPressing {
    private final boolean emergencyButton; 
    public EmergencyButtonPressing(boolean emergencyButton)
    {
        this.emergencyButton = emergencyButton;
    }
    
    public boolean getEmergencyButton() 
    {
        return emergencyButton;
    }
}
