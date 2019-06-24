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
public class RequestedFloorReaching
{
    private boolean state;
    
    public RequestedFloorReaching(boolean state)
    {
        this.state = state;
    }
    
    public boolean getState() {
        return state;
    }
    
}
