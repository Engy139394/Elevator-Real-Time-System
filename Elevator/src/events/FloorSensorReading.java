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
public class FloorSensorReading 
{
       private final int cfloor;
       private final int vfloor;
    
    public FloorSensorReading (int cfloor, int vfloor)
    {
        this.cfloor = cfloor;
        this.vfloor = vfloor;
    }

    public int getCfloor() {
        return cfloor;
    }

    public int getVfloor() {
        return vfloor;
    }
    
   
}
