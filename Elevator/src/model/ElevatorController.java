/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import esper.Config;
import events.EmergencyButtonPressing;
import events.RequestedFloorReaching;
import events.WeightSensorReading;
import java.awt.Color;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import view.HallGUI;
import view.DoorOpenGUI;
import view.Emergency;
import view.InsideElevator;
import view.Monitor;

/**
 *
 * @author EngyE
 */
public class ElevatorController {
   // private static ElevatorController elevatorController;
    private Motor motorr;
    private Alarm alarmState;
    private Door door; 
    private HallGUI hallGui;
    private WeightSensor weightSensor;
    private String state;
    private boolean emergencyState;
    private HallButton hallButton;
    private FloorButton floorButton;
    private FloorSensor floorSensor;
    private DoorOpenGUI doorOpenGui;
    private InsideElevator inElevator;
    private int insideCFloor;
    private int insideVFloor;
    private boolean requestedFloorReachingState;
    private Monitor monitor;
    private Timer timer;
    private Emergency emergency;
    private ObjectDetector objectDetect;    
    private LightBulb light;
    private Object lock;
   

    public ElevatorController() {
        monitor = new Monitor(); 
        light = new LightBulb(this);        
        timer = new Timer(this);       
        monitor.setVisible(true);
        hallButton = new HallButton(this);
        floorButton = new FloorButton(this);   
        weightSensor = new WeightSensor(this);
        objectDetect = new ObjectDetector(this);
        lock = new Object();
        door = new Door(this);        
        weightSensor.start();
        objectDetect.start();
        timer.start();
        //timer.join();
        door.start();                
        floorSensor = new FloorSensor(this);
        floorSensor.start();
        doorOpenGui = new DoorOpenGUI();
        hallGui = new HallGUI();
        this.getFloorSensor().Floor();
        inElevator = new InsideElevator();  
        hallGui.setLocationRelativeTo(null);
        hallGui.setVisible(true);
        doorOpenGui.setLocationRelativeTo(null);
        doorOpenGui.setVisible(false);
         this.monitor.setLocation(hallGui.getX()-200, hallGui.getY()-230);
        inElevator.setLocationRelativeTo(null);
        inElevator.setVisible(false);
        alarmState = new Alarm(this);
        motorr = new Motor(this);
        motorr.start();
        emergency = new Emergency();
        doorOpenGui.setVisible(false);
        
      

    }

    public void setState(String state) {
        this.state = state;
    }

  
 public boolean isRequestedFloorReachingState() {
        return requestedFloorReachingState;
    } 
    public HallGUI getDoorGui() {
        return hallGui;
    }

   

    public void weightSignal(float currentWeight) throws InterruptedException 
    {     
       
        doorOpenGui.getjTextField1().setText((" " + currentWeight + " "));
        if (currentWeight < 500) {
            //doorOpenGui.getjTextField1().setBackground(Color.green);
            this.getLight().setState(false, true);
            
            doorOpenGui.getjLabel2().setText("Allowed Weight");

        } else if (currentWeight > 500) {
           // doorOpenGui.getjTextField1().setBackground(Color.red);
           this.getLight().setState(true, false);
             doorOpenGui.getjLabel2().setText("Over Weight detected");
             

        }
        if(currentWeight> 200 && currentWeight <400&&this.door.isDoorOpen()==true)
        {
            sleep(4000);         
           door.setDoorClosed(true);
            this.door.setDoorOpen(false);            
            doorOpenGui.setVisible(false);            
            inElevator.setVisible(true); 
            monitor.setVisible(false);
            getTimer().decreaseForDoor();
            
        }
    }
    
  public void TimeSignal(int time) throws InterruptedException
    {
   

    if(time==0&& motorr.getMotorState()=="Stop")
    {
       this.setState("idle");
    }
     if(time==0&& this.isRequestedFloorReachingState()==true)
    {
       System.out.println("openDoor");
    }
       if(time==0&& door.isDoorClosed()==false)
    {
       System.out.println("closeDoor");
    }
       //    if(time==0&& door.isDoorOpen()==true)
//    {
//       door.setDoorClosed(true);
//    }
    
    
    }
  
   public void objectSignal(int objectRange) throws InterruptedException
    {
      // System.out.println("objectRange"+objectRange+"");
        if(objectRange==333)
        {
            
           System.out.println("ObjectDetected!!");
    
          }
    }
public void playMusic(String filePath)
{
    InputStream music;
    try
    {
        music = new FileInputStream(new File(filePath));
        AudioStream  audios = new AudioStream(music);
        AudioPlayer.player.start(audios);
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
}
    public void display() {
        insideCFloor--;
    }

    public void setFloorButtonState(int floor1, int floor2, int floor3, int floor4, int floor5, int floor6, int floor7, int floor8, int floor9, int floor10) throws InterruptedException {
       
        this.floorButton.setState(floor1, floor2, floor3, floor4, floor5, floor6, floor7, floor8, floor9, floor10);    
        this.playMusic("src\\Music\\click2.wav");
        this.playMusic("src\\Music\\elevatorMoving4.wav");

        if (floorButton.getFloor1() == 1) 
        {
  
         inElevator.getFloorStateTxt2().setText(" " + getInsideCFloor() + " ");
       
            Thread t = new Thread() {
                public void run() {
                    int curFloor = insideCFloor;
                    while (1<= curFloor) {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        inElevator.getFloorStateTxt2().setText(" " + curFloor + " ");                       
                        curFloor--;
                        //System.out.println(insideCFloor);
                      
                    }
                    playMusic("src\\Music\\ding.wav");
                    openElevatorDoor(true);
                }
            };
            t.start();
        }

         if (floorButton.getFloor3() == 3) {

               inElevator.getFloorStateTxt2().setText(" " + insideCFloor + " ");

            Thread t2 = new Thread() {
                public void run() {      
                    int curFloor = insideCFloor;
                    int curFloor2 = insideCFloor;                
                 
                    while (curFloor>=3) {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         inElevator.getFloorStateTxt2().setText(" " + curFloor + " ");
                         hallGui.getjTextField3().setText(" " + curFloor + " ");
                        curFloor--;
                        //System.out.println(getInsideCFloor());
                    }
                     if(curFloor==3)
                    {
                    //playMusic("C:\\Users\\EngyE\\Documents\\NetBeansProjects\\Elevator\\Music\\ding.wav");
                    openElevatorDoor(true);
                    }
                    
                      while (curFloor2<=3) {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         inElevator.getFloorStateTxt2().setText(" " + curFloor2 + " ");
                         hallGui.getjTextField3().setText(" " + curFloor + " ");
                        curFloor2++;
                       // System.out.println(getInsideCFloor());
                    }
                   playMusic("src\\Music\\ding.wav");
                }
            };
            t2.start();
         }
            if (floorButton.getFloor4() == 4) {

               inElevator.getFloorStateTxt2().setText(" " + insideCFloor + " ");

            Thread t3 = new Thread() {
                public void run() {      
                    int curFloor = insideCFloor;
                    int curFloor2 = insideCFloor;                
                 
                    while (curFloor>=4) {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         inElevator.getFloorStateTxt2().setText(" " + curFloor + " ");
                         hallGui.getjTextField3().setText(" " + curFloor + " ");
                        curFloor--;
                        //System.out.println(getInsideCFloor());
                    }
                     if(curFloor==4)
                    {
                    //playMusic("C:\\Users\\EngyE\\Documents\\NetBeansProjects\\Elevator\\Music\\ding.wav");
                    openElevatorDoor(true);
                    }
                      while (curFloor2<=4) {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         inElevator.getFloorStateTxt2().setText(" " + curFloor2 + " ");
                         hallGui.getjTextField3().setText(" " + curFloor + " ");
                        curFloor2++;
                        //System.out.println(getInsideCFloor());
                    }
                      playMusic("src\\Music\\ding.wav");
                      openElevatorDoor(true);
                }
            };
            t3.start();
            }
          if (floorButton.getFloor5() == 5) {

               inElevator.getFloorStateTxt2().setText(" " + insideCFloor + " ");

            Thread t4 = new Thread() {
                public void run() {      
                    int curFloor = insideCFloor;
                    int curFloor2 = insideCFloor;                
                 
                    while (curFloor>=5) {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         inElevator.getFloorStateTxt2().setText(" " + curFloor + " ");
                         hallGui.getjTextField3().setText(" " + curFloor + " ");
                        curFloor--;
                        //System.out.println(getInsideCFloor());
                    }
                    if(curFloor==5)
                    {
                    //playMusic("C:\\Users\\EngyE\\Documents\\NetBeansProjects\\Elevator\\Music\\ding.wav");
                    openElevatorDoor(true);
                    }
                
                      while (curFloor2<=5) {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         inElevator.getFloorStateTxt2().setText(" " + curFloor2 + " ");
                         hallGui.getjTextField3().setText(" " + curFloor + " ");
                        curFloor2++;
                        //System.out.println(getInsideCFloor());
                    }
                      playMusic("src\\Music\\ding.wav");
                      openElevatorDoor(true);
                }
            };
            t4.start();
          }
        if (floorButton.getFloor6() == 6) {

               inElevator.getFloorStateTxt2().setText(" " + insideCFloor + " ");

            Thread t5 = new Thread() {
                public void run() {      
                    int curFloor = insideCFloor;
                    int curFloor2 = insideCFloor;                
                 
                    while (curFloor>=6) {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         inElevator.getFloorStateTxt2().setText(" " + curFloor + " ");
                         hallGui.getjTextField3().setText(" " + curFloor + " ");
                        curFloor--;
                        //System.out.println(getInsideCFloor());
                    }
                     if(curFloor==6)
                    {
                    //playMusic("C:\\Users\\EngyE\\Documents\\NetBeansProjects\\Elevator\\Music\\ding.wav");
                    openElevatorDoor(true);
                    }
                      while (curFloor2<=6) {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         inElevator.getFloorStateTxt2().setText(" " + curFloor2 + " ");
                         hallGui.getjTextField3().setText(" " + curFloor + " ");
                        curFloor2++;
                        //System.out.println(getInsideCFloor());
                    }
                      playMusic("src\\Music\\ding.wav");
                      openElevatorDoor(true);
                }
            };
            t5.start();
        }
             
           if (floorButton.getFloor7() == 7) {

               inElevator.getFloorStateTxt2().setText(" " + insideCFloor + " ");

            Thread t6 = new Thread() {
                public void run() {      
                    int curFloor = insideCFloor;
                    int curFloor2 = insideCFloor;                
                 
                    while (curFloor>=7) {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         inElevator.getFloorStateTxt2().setText(" " + curFloor + " ");
                         hallGui.getjTextField3().setText(" " + curFloor + " ");
                        curFloor--;
                        //System.out.println(getInsideCFloor());
                    }
                     if(curFloor==7)
                    {
                   // playMusic("C:\\Users\\EngyE\\Documents\\NetBeansProjects\\Elevator\\Music\\ding.wav");
                    openElevatorDoor(true);
                    }
                      while (curFloor2<=7) {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         inElevator.getFloorStateTxt2().setText(" " + curFloor2 + "");
                         hallGui.getjTextField3().setText(" " + curFloor + " ");
                        curFloor2++;
                        //System.out.println(getInsideCFloor());
                    }
                      playMusic("src\\Music\\ding.wav");
                      
                      openElevatorDoor(true);
                }
            };
            t6.start();
       
           }
          if (floorButton.getFloor8() == 8) {

               inElevator.getFloorStateTxt2().setText("" + insideCFloor + "");

            Thread t7 = new Thread() {
                public void run() {      
                    int curFloor = insideCFloor;
                    int curFloor2 = insideCFloor;                
                 
                    while (curFloor>=8) {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         inElevator.getFloorStateTxt2().setText(" " + curFloor + " ");
                         hallGui.getjTextField3().setText(" " + curFloor + " ");
                        curFloor--;
                        //System.out.println(getInsideCFloor());
                    }
                    if(curFloor==8)
                    {
                    //playMusic("C:\\Users\\EngyE\\Documents\\NetBeansProjects\\Elevator\\Music\\ding.wav");
                    openElevatorDoor(true);
                    }
                      while (curFloor2<=8) {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         inElevator.getFloorStateTxt2().setText(" " + curFloor2 + " ");
                         hallGui.getjTextField3().setText(" " + curFloor + " ");
                        curFloor2++;
                        //System.out.println(getInsideCFloor());
                    }
                      playMusic("src\\Music\\ding.wav");
                      openElevatorDoor(true);
                }
            };
            t7.start();
          }
            if (floorButton.getFloor9() == 9) {

               inElevator.getFloorStateTxt2().setText(" " + insideCFloor + " ");

            Thread t8 = new Thread() {
                public void run() {      
                    int curFloor = insideCFloor;
                    int curFloor2 = insideCFloor;                
                 
                    while (curFloor>=9) {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         inElevator.getFloorStateTxt2().setText(" " + curFloor + " ");
                         hallGui.getjTextField3().setText(" " + curFloor + " ");
                        curFloor--;
                        //System.out.println(getInsideCFloor());
                    }
                     if(curFloor==9)
                    {
                   // playMusic("C:\\Users\\EngyE\\Documents\\NetBeansProjects\\Elevator\\Music\\ding.wav");
                    openElevatorDoor(true);
                    }
                      while (curFloor2<=9) {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         inElevator.getFloorStateTxt2().setText(" " + curFloor2 + " ");
                         hallGui.getjTextField3().setText(" " + curFloor + " ");
                        curFloor2++;
                       // System.out.println(getInsideCFloor());
                    }
                      playMusic("src\\Music\\ding.wav");
                      openElevatorDoor(true);
                }
            };
            t8.start();
           }
      if (floorButton.getFloor10() == 10) {

               inElevator.getFloorStateTxt2().setText(" " + insideCFloor + " ");

            Thread t9 = new Thread() {
                public void run() {      
                    int curFloor = insideCFloor;
                    int curFloor2 = insideCFloor;                
                 
                    while (curFloor>=10) {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         inElevator.getFloorStateTxt2().setText("" + curFloor + "");
                         hallGui.getjTextField3().setText(" " + curFloor + " ");
                        curFloor--;
                       // System.out.println(getInsideCFloor());
                    }
                     if(curFloor==10)
                    {
                   // playMusic("C:\\Users\\EngyE\\Documents\\NetBeansProjects\\Elevator\\Music\\ding.wav");
                    openElevatorDoor(true);
                    }
                      while (curFloor2<=10)
                      {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         inElevator.getFloorStateTxt2().setText("" + curFloor2 + "");
                         hallGui.getjTextField3().setText(" " + curFloor + " ");
                        curFloor2++;
                        //System.out.println(getInsideCFloor());
                    }
                      playMusic("src\\Music\\ding.wav");
                      openElevatorDoor(true);
                }
            };
            t9.start();
      }
      
    }

    public void floorSignal(int Cfloor, int Vfloor) throws InterruptedException 
    {  

        hallGui.getjTextField3().setText(" " + Vfloor + " ");
        hallGui.getjTextField4().setText(" " + Cfloor + " ");
        int tempVfloor;
        int tempCfloor;
        tempVfloor=Vfloor;
        tempCfloor=Cfloor;
        
//        if(tempVfloor==tempCfloor)
//        {
//             Config.sendEvent(new RequestedFloorReaching(true)); 
//        }
        
       if(Vfloor>Cfloor)
       {
         this.getHallGui().getjButton1().setEnabled(false);
       }
        if(Vfloor<Cfloor)
       {
         this.getHallGui().getjButton3().setEnabled(false);
       }
        if (Vfloor > Cfloor && hallButton.isDownstate() == true) 
        {
            this.getFloorSensor().decreaseFloor();                
            hallGui.getjTextField3().setText(" " + Vfloor + " ");
            tempVfloor=Vfloor-1;
            tempCfloor=Cfloor;
             if(tempVfloor ==1)
             {
                 this.monitor.floor1().setVisible(true);
                 this.monitor.f1().setBackground(Color.green);
                 this.monitor.floor2().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                 this.monitor.f2().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
             if(tempVfloor ==2)
             {
               this.monitor.floor2().setVisible(true);
                 this.monitor.f2().setBackground(Color.green);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                 this.monitor.f1().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
               if(tempVfloor ==3)
             {
                 this.monitor.floor3().setVisible(true);
                 this.monitor.f3().setBackground(Color.green);
                 this.monitor.floor2().setVisible(false);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                 this.monitor.f2().setBackground(Color.red);
                 this.monitor.f1().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
                 if(tempVfloor ==4)
             {
                     this.monitor.floor4().setVisible(true);
                 this.monitor.f4().setBackground(Color.green);
                 this.monitor.floor2().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                 this.monitor.f2().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f1().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
                   if(tempVfloor ==5)
             {
                this.monitor.floor5().setVisible(true);
                 this.monitor.f5().setBackground(Color.green);
                 this.monitor.floor2().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                 this.monitor.f2().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f1().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
                     if(tempVfloor ==6)
             {
                 this.monitor.floor6().setVisible(true);
                 this.monitor.f6().setBackground(Color.green);
                 this.monitor.floor2().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                this.monitor.f2().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f1().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
                       if(tempVfloor ==7)
             {
                    this.monitor.floor7().setVisible(true);
                  this.monitor.f7().setBackground(Color.green);
                 this.monitor.floor2().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                 this.monitor.f2().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f1().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
                         if(tempVfloor ==8)
             {
                  this.monitor.floor8().setVisible(true);
                   this.monitor.f8().setBackground(Color.green);
                  this.monitor.floor2().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                 this.monitor.f2().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f1().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
               if(tempVfloor ==9)
             {
                  this.monitor.floor9().setVisible(true);
                  this.monitor.f9().setBackground(Color.green);
                 this.monitor.floor2().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                 this.monitor.f2().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f1().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
               if(tempVfloor ==10)
             {
                this.monitor.floor10().setVisible(true);
                 this.monitor.f10().setBackground(Color.green);
                 this.monitor.floor2().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.f2().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f1().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
             if(Vfloor -Cfloor==1||Vfloor-Cfloor==-1)
             {
             this.motorr.setMotorState("Slowly Stopping");
             Config.sendEvent(new RequestedFloorReaching(true)); 
             

             }
             
             
             
        }

        if (Vfloor < Cfloor && hallButton.isUpstate() == true) 
        {
            this.getFloorSensor().raiseFloor();           
            hallGui.getjTextField3().setText(" " + Vfloor + " ");
             tempVfloor = Vfloor+1;
             if(tempVfloor ==1)
                {
                 this.monitor.floor1().setVisible(true);
                 this.monitor.f1().setBackground(Color.green);
                 this.monitor.floor2().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                 this.monitor.f2().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
             if(tempVfloor ==2)
             {
                this.monitor.floor2().setVisible(true);
                 this.monitor.f2().setBackground(Color.green);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                 this.monitor.f1().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
               if(tempVfloor ==3)
             {
                this.monitor.floor3().setVisible(true);
                 this.monitor.f3().setBackground(Color.green);
                 this.monitor.floor2().setVisible(false);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                 this.monitor.f2().setBackground(Color.red);
                 this.monitor.f1().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
                 if(tempVfloor ==4)
             {
                this.monitor.floor4().setVisible(true);
                 this.monitor.f4().setBackground(Color.green);
                 this.monitor.floor2().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                 this.monitor.f2().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f1().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
                   if(tempVfloor ==5)
             {
                 this.monitor.floor5().setVisible(true);
                  this.monitor.f5().setBackground(Color.green);
                 this.monitor.floor2().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                this.monitor.f2().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f1().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
                     if(tempVfloor ==6)
             {
                 this.monitor.floor6().setVisible(true);
                  this.monitor.f6().setBackground(Color.green);
                 this.monitor.floor2().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                this.monitor.f2().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f1().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
                       if(tempVfloor ==7)
             {
                 this.monitor.floor7().setVisible(true);
                  this.monitor.f7().setBackground(Color.green);
                 this.monitor.floor2().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                 this.monitor.f2().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f1().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
              if(tempVfloor ==8)
             {
                  this.monitor.floor8().setVisible(true);
                   this.monitor.f8().setBackground(Color.green);
                  this.monitor.floor2().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                 this.monitor.f2().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f1().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
               if(tempVfloor ==9)
             {
                 this.monitor.floor9().setVisible(true);
                  this.monitor.f9().setBackground(Color.green);
                 this.monitor.floor2().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.floor10().setVisible(false);
                 this.monitor.f2().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f1().setBackground(Color.red);
                 this.monitor.f10().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
               if(tempVfloor ==10)
             {
                this.monitor.floor10().setVisible(true);
                 this.monitor.f10().setBackground(Color.green);
                 this.monitor.floor2().setVisible(false);
                 this.monitor.floor3().setVisible(false);
                 this.monitor.floor4().setVisible(false);
                 this.monitor.floor5().setVisible(false);
                 this.monitor.floor6().setVisible(false);
                 this.monitor.floor7().setVisible(false);
                 this.monitor.floor8().setVisible(false);
                 this.monitor.floor9().setVisible(false);
                 this.monitor.floor1().setVisible(false);
                 this.monitor.f2().setBackground(Color.red);
                 this.monitor.f3().setBackground(Color.red);
                 this.monitor.f4().setBackground(Color.red);
                 this.monitor.f5().setBackground(Color.red);
                 this.monitor.f6().setBackground(Color.red);
                 this.monitor.f7().setBackground(Color.red);
                 this.monitor.f8().setBackground(Color.red);
                 this.monitor.f9().setBackground(Color.red);
                 this.monitor.f1().setBackground(Color.red);
                 insideCFloor = tempVfloor;
             }
            if(Vfloor -Cfloor==1||Vfloor-Cfloor==-1)
            {  
                this.motorr.setMotorState("Slowly Stopping");
             Config.sendEvent(new RequestedFloorReaching(true)); 
             
            }
            
         
  
    }
         //insideCFloor = Cfloor;
    }
    

    public void setAlarmState(boolean state) throws InterruptedException {
         
        this.alarmState.setState(state);        
        if (alarmState.isState() == true) 
        {
        this.getLight().setState(true,false);
        this.motorr.setMotorState("Stop");
        emergency.setLocation(inElevator.getX(), inElevator.getY());
        emergency.setVisible(true);     

        }

    }
   
      public void openElevatorDoor(boolean openDoorButton) 
      {
          //this.door.setDoorOpen(state);
         // System.out.println("open door");
      }
      public void OpenHallDoor() 
      {
           
        {
            if(this.motorr.getMotorState()=="Stop")  
            {
               this.door.setDoorOpen(true);      

            }

      }
      }
      public void closeDoor(boolean closeDoorButton)
      {
          door.setDoorClosed(closeDoorButton);
      }
      public void notifyDoorState()
      {
        
            while (door.isDoorOpen()==true) 
            {
                        try {
                            sleep(600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElevatorController.class.getName()).log(Level.SEVERE, null, ex);
                        }                            
                            getWeightSensor().idle();
                            getObjectDetect().ObjectDetecting();
                           // getTimer().decreaseForDoor();
           }
    }

      public void setFloorReachingState(boolean state) 
      {      
     
           if(state==true)
           { 
            
            this.motorr.setMotorState("Stop");
            this.OpenHallDoor();
           }

      }

    public Timer getTimer() {
        return timer;
    }

    

    public int getInsideCFloor() {
        return insideCFloor;
    }

    public int getInsideVFloor() {
        return insideVFloor;
    }

    public void setHallButtonState(boolean upstate, boolean downstate) 
    {
        this.hallButton.setState(upstate, downstate);
        this.playMusic("src\\Music\\click2.wav");
         this.playMusic("src\\Music\\elevArrives2.wav");
       
        if (hallButton.isUpstate() == true) 
        {
          
          hallGui.getjButton4().setVisible(true);       

        } 
        
        else if (hallButton.isDownstate() == true) 
        {
           
           hallGui.getjButton5().setVisible(true);
        }
    }

    public WeightSensor getWeightSensor() {
        return weightSensor;
    }

    public Alarm getAlarm() {
        return alarmState;
    }

    public FloorSensor getFloorSensor() {
        return floorSensor;

    }

    public FloorButton getFloorButton() {
        return floorButton;
    }

    public InsideElevator getInElevator() {
        return inElevator;
    }

    public DoorOpenGUI getDoorOpenGui() {
        return doorOpenGui;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public Motor getMotorr() {
        return motorr;
    }

    public Alarm getAlarmState() {
        return alarmState;
    }

    public Door getDoor() {
        return door;
    }
  
    public boolean isEmergencyState() {
        return emergencyState;
    }

    public HallButton getHallButton() {
        return hallButton;
    }

    public Emergency getEmergency() {
        return emergency;
    }

    public HallGUI getHallGui() {
        return hallGui;
    }

    public String getState() {
        return state;
    }

    public ObjectDetector getObjectDetect() {
        return objectDetect;
    }

    public Object getLock() {
        return lock;
    }

    public LightBulb getLight() {
        return light;
    }

  

}
