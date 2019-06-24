/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esper;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import events.CloseDoorButtonPressing;
import events.EmergencyButtonPressing;
import events.FloorButtonPressing;
import events.FloorSensorReading;
import events.HallButtonPressing;
import events.ObjectDetectionEvent;
import events.RequestedFloorReaching;
import events.TimeEvent;
import events.WeightSensorReading;
import events.openDoorButtonPressing;

/**
 *
 * @author EngyE
 */
public class Config {
       private static EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();

    public static void registerEvents() {
        engine.getEPAdministrator().getConfiguration().addEventType(WeightSensorReading.class);       
        engine.getEPAdministrator().getConfiguration().addEventType(EmergencyButtonPressing.class);        
        engine.getEPAdministrator().getConfiguration().addEventType(HallButtonPressing.class);
        engine.getEPAdministrator().getConfiguration().addEventType(FloorSensorReading.class);
        engine.getEPAdministrator().getConfiguration().addEventType(FloorButtonPressing.class);
        engine.getEPAdministrator().getConfiguration().addEventType(RequestedFloorReaching.class);
        engine.getEPAdministrator().getConfiguration().addEventType(TimeEvent.class);
        engine.getEPAdministrator().getConfiguration().addEventType(CloseDoorButtonPressing.class);
        engine.getEPAdministrator().getConfiguration().addEventType(openDoorButtonPressing.class);
        engine.getEPAdministrator().getConfiguration().addEventType(ObjectDetectionEvent.class);
        
        System.out.println("Events Successfully Registered.");
    }

    public static EPStatement createStatement(String s) {
        EPStatement result = engine.getEPAdministrator().createEPL(s);
        System.out.println("EPL Statement Successfully Created.");
        return result;
    }
    
    public static void sendEvent(Object o)
    {
        engine.getEPRuntime().sendEvent(o);
    }
    
}
