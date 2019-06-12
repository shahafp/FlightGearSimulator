package model.client;

import java.util.ArrayList;

public class ReturnCommand implements Command {
    @Override
    public int doCommand(ArrayList<String> arr, int index) {
        StringBuilder sb = new StringBuilder();
        int i;
        double res;
        for ( i=index+1;i<arr.size();i++){
            sb.append(arr.get(i));
        }
        if (Interpreter.symbolTable.containsKey(sb.toString())){
            res = (Double)Interpreter.symbolTable.get(sb.toString()).getValue();
        }
        else {
            res = DijakstraAlgo.calc(sb.toString(), arr, index);
        }
        Interpreter.returnVal=res;
        return i;
    }
}
