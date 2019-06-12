package model.client;

import java.util.ArrayList;

public class SleepCommand implements Command {
    @Override
    public int doCommand(ArrayList<String> arr,int index) {
        try {
            Thread.sleep(Integer.parseInt(arr.get(index+1)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 2;
    }
}
