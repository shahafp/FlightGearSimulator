package model.client;

import java.util.ArrayList;

public class OpenServerDataCommand implements Command {

    public int port;
    public int milliSec;
    public String[] parameters;

    public OpenServerDataCommand() {
        this.parameters = new String[2];
    }

    @Override
    public int doCommand(ArrayList<String> arr, int index) {
        // initialization
        index++;
        int i=0;
        System.out.println("Open server Command");
        for (;i<parameters.length;i++){
            parameters[i]=arr.get(index+i);
        }
        this.port = Integer.parseInt(parameters[0]);
        this.milliSec = Integer.parseInt(parameters[1]);

        //open server
        new Thread (()-> {
            DataReaderServer ds = new DataReaderServer();
            ds.runServer(port);
        }).start();
        while(!Interpreter.flag);
        return i+1;
    }
}
