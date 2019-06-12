package model;

import model.client.Command;
import model.client.ConnectCommand;
import model.client.Interpreter;
import model.client.OpenServerDataCommand;
import model.server.MyClientHandler;
import model.server.MySerialServer;
import model.server.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import static model.client.ConnectCommand.output;

public class myModel extends Observable {

    Server calcServer; //server to calc best path
    Interpreter aircraftControl; //incharge of controlling the aircraft by joystick/script
    int calcServerPort;
    PrintWriter outTocalcServer;
    BufferedReader inFromCalcServer;
    double [][] matrix;
    String path;

    public String getPath() {
        return path;
    }

    public myModel(int calcServerPort) {
        this.calcServerPort=calcServerPort;
        calcServer=new MySerialServer(calcServerPort);
        aircraftControl=new Interpreter("./resources/simulator_vars.txt");
//        Command openServer=new OpenServerDataCommand();
//        openServer.doCommand(new ArrayList<>(Arrays.asList("openDataServer", "5400", "10")),0);
        calcServer.start(new MyClientHandler());
    }
    public void runScript(String[] lines){
        aircraftControl.lexer(lines);
        aircraftControl.parser();
    }

    public void controlElevatorAileron(double elevator, double aileron){
        output.println("set /controls/flight/elevator "+elevator);
        output.println("set /controls/flight/aileron "+aileron);
        output.flush();
    }
    public void controlRudder(double rudder) {
    	output.println("set /controls/flight/rudder "+rudder);
    	output.flush();
    }
    public void controlThrottle(double throttle){
    	output.println("set /controls/engines/current-engine/throttle "+throttle);
    	output.flush();
    }
    public void connectToSim(String ip, String port){
        int c=new ConnectCommand().doCommand(new ArrayList<String>(Arrays.asList("connect", ip, port)),0);
    }
    public void connectToCalcServer(String ip, String port, double [][] matrix, String init, String goal){
        try {
            Socket theServer=new Socket(ip, Integer.parseInt(port));
            System.out.println("connected to calc server");
            outTocalcServer=new PrintWriter(theServer.getOutputStream());
            inFromCalcServer=new BufferedReader(new InputStreamReader(theServer.getInputStream()));
        } catch (IOException e) {}
        this.matrix=matrix;
        getPathFromCalcServer(init, goal);
    }
    public void getPathFromCalcServer (String init, String goal){
        int i, j;
        System.out.println("sending problem...");
        for(i=0;i<matrix.length;i++){
            System.out.print("\t");
            for(j=0;j<matrix[i].length-1;j++){
                outTocalcServer.print(matrix[i][j]+",");
                System.out.print(matrix[i][j]+",");
            }
            outTocalcServer.println(matrix[i][j]);
            System.out.println(matrix[i][j]);
        }
        outTocalcServer.println("end");
        outTocalcServer.println(init);
        outTocalcServer.println(goal);
        outTocalcServer.flush();
        System.out.println("\tend\n\t"+init+"\n\t"+goal);
        System.out.println("\tproblem sent, waiting for solution...");
        try {
            path=inFromCalcServer.readLine();
            System.out.println(path);
        } catch (IOException e) {}
        System.out.println("\tsolution received");
        this.setChanged();
        this.notifyObservers();
    }
}