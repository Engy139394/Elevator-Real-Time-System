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
public class ObjectDetectionEvent 
{
    private final int object;
    
    public ObjectDetectionEvent(int object)
    {
        this.object = object;
    }
    
    public int getObject() {
        return object;
    } 
}
