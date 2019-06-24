/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esper;

import model.ElevatorController;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author EngyE
 */
public class Elevator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
               // Disable logging
        Logger.getRootLogger().setLevel(Level.OFF);

        // Register events
        Config.registerEvents();

        // Create Kettle
       final ElevatorController elevatorController =  new ElevatorController();
        
             Config.createStatement("select emergencyButton from EmergencyButtonPressing")
                .setSubscriber(new Object() {
                    public void update(boolean state) throws InterruptedException {
                         elevatorController.setAlarmState(state);
                         
                    }
                });
               Config.createStatement("select weight from WeightSensorReading")
                .setSubscriber(new Object() {
                    public void update(int weight) throws InterruptedException {
                        elevatorController.weightSignal(weight);
                       
                    }
                });
               
                   Config.createStatement("select cfloor, vfloor from FloorSensorReading")
                .setSubscriber(new Object() {
                    public void update(int cfloor, int vfloor) throws InterruptedException {
                        elevatorController.floorSignal(cfloor, vfloor);
                       
                    }
                });
                       Config.createStatement("select floor, floorr, floorrr, floorrrr, floorrrrr, floorrrrrr, floorrrrrrr, flooreight, floornine, floorten from FloorButtonPressing")
                .setSubscriber(new Object() {
                    public void update(int floor, int floorr, int floorrr, int floorrrr, int floorrrrr, int floorrrrrr, int floorrrrrrr, int flooreight, int floornine, int floorten) throws InterruptedException {
                        elevatorController.setFloorButtonState(floor,  floorr, floorrr,  floorrrr,  floorrrrr,  floorrrrrr,  floorrrrrrr,  flooreight,  floornine,  floorten);
                       ;
                    }
                });
                   Config.createStatement("select state from RequestedFloorReaching")
                .setSubscriber(new Object() {
                    public void update(boolean state) throws InterruptedException {
                         elevatorController.setFloorReachingState(state);
                         
                    }
                });
                    Config.createStatement("select time from TimeEvent")
                .setSubscriber(new Object() {
                    public void update(int time) throws InterruptedException {
                         elevatorController.TimeSignal(time);
                         
                    }
                });
                    Config.createStatement("select closeDoorButton from CloseDoorButtonPressing")
                .setSubscriber(new Object() {
                    public void update(boolean closeDoorButton) throws InterruptedException {
                    elevatorController.closeDoor(closeDoorButton);                       
                         
                    }
                });
                
               
                    Config.createStatement("select upButton, downButton from HallButtonPressing")
                .setSubscriber(new Object() {
                    public void update(boolean upButton, boolean downButton) throws InterruptedException {
                        elevatorController.setHallButtonState(upButton, downButton);
                       ;
                    }
                });
                   
                     Config.createStatement("select object from ObjectDetectionEvent")
                .setSubscriber(new Object() {
                    public void update(int object) throws InterruptedException {
                    elevatorController.objectSignal(object);
                         
                    }
                });
                    Config.createStatement("select openDoorButton from openDoorButtonPressing")
                .setSubscriber(new Object() {
                    public void update(boolean openDoorButton) throws InterruptedException {
                    elevatorController.openElevatorDoor(openDoorButton);                       
                         
                    }
                });
               

      
    }
    
}
