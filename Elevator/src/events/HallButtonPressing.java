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
public class HallButtonPressing 
{
     private final boolean upButton; 
     private final boolean downButton; 
    public HallButtonPressing (boolean upButton, boolean downButton)
    {
        this.upButton= upButton;
        this.downButton= downButton;
    }

    public boolean isUpButton() {
        return upButton;
    }

    public boolean isDownButton() {
        return downButton;
    }
    
    
    
}
