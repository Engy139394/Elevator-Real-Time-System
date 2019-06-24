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
public class openDoorButtonPressing 
{
    private boolean openDoorButton; 
    public openDoorButtonPressing(boolean openDoorButton)
    {
        this.openDoorButton = openDoorButton;
    }
    
    public boolean getOpenDoorButton() 
    {
        return openDoorButton;
    }
    
}
