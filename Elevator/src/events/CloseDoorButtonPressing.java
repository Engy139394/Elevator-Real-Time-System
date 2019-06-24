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
public class CloseDoorButtonPressing
{
   private boolean closeDoorButton; 
    public CloseDoorButtonPressing(boolean closeDoorButton)
    {
        this.closeDoorButton = closeDoorButton;
    }
    
    public boolean getCloseDoorButton() 
    {
        return closeDoorButton;
    }
}
