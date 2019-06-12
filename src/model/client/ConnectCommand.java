package model.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ConnectCommand implements Command {

    public String ip;
    public int port;
    public static PrintWriter output;


    @Override
    public int doCommand(ArrayList<String> arr,int index) {

        this.ip = arr.get(index+1);
        this.port = Integer.parseInt(arr.get(index+2));
        try {
            Socket theServer = new Socket(ip,port);
            output = new PrintWriter(theServer.getOutputStream());
            System.out.println("Connected to Server");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 3;
    }
}
