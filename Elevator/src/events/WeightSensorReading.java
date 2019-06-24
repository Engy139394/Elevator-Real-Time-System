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
public class WeightSensorReading
{
    private final int weight;
    
    public WeightSensorReading(int weight)
    {
        this.weight = weight;
    }
    
    public int getWeight() {
        return weight;
    }
    
}
