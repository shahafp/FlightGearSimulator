package model.client;

import java.util.ArrayList;

public class BindCommand implements Command {

    @Override
    public int doCommand(ArrayList<String> args, int index) {
        if(!Interpreter.symbolTable.containsKey(args.get(index+1))) {
            Interpreter.symbolTable.put(args.get(index+1), new Variable(0.0,args.get(index+1)));
        }
        Interpreter.symbolTable.replace(args.get(index-2), Interpreter.symbolTable.get(args.get(index+1)));
        return 1;
    }
}