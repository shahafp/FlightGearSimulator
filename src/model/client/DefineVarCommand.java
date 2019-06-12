package model.client;

import java.util.ArrayList;
import java.util.HashMap;


public class DefineVarCommand implements Command {

    String token;

    public DefineVarCommand() {}

    @Override
    public int doCommand(ArrayList<String> arr,int index) {
        double res=0.0;
        if (arr.get(index+1).contains("=")){
            String[] temp = arr.get(index+1).split("=");
            token = temp[0];
            res = DijakstraAlgo.calc(temp[1],arr,index);
        }
        else {
            token = arr.get(index+1);
        }
        Interpreter.symbolTable.put(token, new Variable(res,token));
        return 2;
    }
}
