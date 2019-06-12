package viewmodel;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.myModel;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class ViewModel extends Observable implements Observer {

    myModel model;
    public DoubleProperty joyStickX, joyStickY, throttle, rudder; //value of the joyStick position
    public StringProperty pathVm;

    public ViewModel(myModel m) {
        this.model = m;
        joyStickY=new SimpleDoubleProperty();
        joyStickX=new SimpleDoubleProperty();
        throttle=new SimpleDoubleProperty();
        rudder=new SimpleDoubleProperty();
        pathVm=new SimpleStringProperty();
    }

    public void runScriptVm(String script){
    	model.runScript(script.split("\n"));
    }
    public void controlElevatorAileronVm(){
        double elevatorVal;
        double aileronVal;
        elevatorVal=-joyStickY.doubleValue()/70; //Double.min(0-(joyStickY.doubleValue()/70), 1);
        aileronVal=joyStickX.doubleValue()/70; //Double.min(joyStickX.doubleValue()/70, 1);
        model.controlElevatorAileron(elevatorVal, aileronVal);
    }
    public void controlRudderVm(){
    	model.controlRudder(rudder.doubleValue());
    }
    public void controlThrottleVm(){
    	model.controlThrottle(throttle.doubleValue());
    }
    public void connectToSimVM(String ip, String port){
    	model.connectToSim(ip, port);
    }
    public void getPathFromCalcServerVm(){
        pathVm.setValue(model.getPath());

    }
    public void connectToCalcServerVm(String ip, String port, double[][] matrix){
//        Random r=new Random() ;       
//        for(int i=0;i<matrix.length;i++)
//            for(int j=0;j<matrix[i].length;j++)
//                matrix[i][j]=String.valueOf(100+r.nextInt(101));
        model.connectToCalcServer(ip, port,matrix,"0,0", "3,3");

    }

    @Override
    public void update(Observable o, Object arg) {
        if(o.equals(model)){
            pathVm.set(model.getPath());
            System.out.println(pathVm);
        }
    }
}