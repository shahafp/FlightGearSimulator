package model.client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

class DataReaderServer {

    public static volatile boolean stop;

    public ArrayList<String> varNames;
    DataReaderServer(){
        stop=false;
        this.varNames = new ArrayList<>();
        try {
            Scanner s = new Scanner(new BufferedReader(new FileReader("./resources/simulator_vars.txt")));
            while(s.hasNext()){
                varNames.add(s.next());
            }
        } catch (FileNotFoundException e) { }
        for (String s: varNames){
            Interpreter.symbolTable.put(s,new Variable(0.0,s));
        }
    }

    void runServer(int port){
        try  {
            ServerSocket server = new ServerSocket(port);
            Socket client = server.accept();
            System.out.println("Connected to client");
            Interpreter.flag=true;
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            int i;
            while(!stop) {
                i=0;
                String[] textFromClient = input.readLine().split(",");   
                for (String s: textFromClient){
                    Interpreter.symbolTable.get(varNames.get(i)).setValue(Double.parseDouble(s));
//                    System.out.println(Interpreter.symbolTable.get(varNames.get(i)).varName);
//                    System.out.println(Double.parseDouble(s));
                    i++;
                }
                Thread.sleep(100); // make the server sleep for 100 millis and read the data 10 times in a sec
            }
            input.close();
            client.close();
            server.close();
            if (stop)
                server.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}