package model.client;

import java.util.ArrayList;

public class DisconnectCommand implements Command {

    //public static volatile boolean stop=false;
    @Override
    public int doCommand(ArrayList<String> arr, int index) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ConnectCommand.output.println("bye");
        ConnectCommand.output.flush();
        DataReaderServer.stop=true;
        //stop=true;
        return 1;
    }
}
