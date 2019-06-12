package model.client;

import java.util.ArrayList;

public class PrintCommand implements Command {
    @Override
    public int doCommand(ArrayList<String> arr,int index) {
    	if (Interpreter.symbolTable.get(arr.get(index+1)) != null)
    		System.out.println(Interpreter.symbolTable.get(arr.get(index+1)).getValue());
    	else
    		System.out.println(Interpreter.symbolTable.get(arr.get(index+1)));
        return 2;
    }
}
