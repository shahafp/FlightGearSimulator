package model.client;

import java.util.ArrayList;

import static model.client.ConnectCommand.output;


public class AssignCommand implements Command {

    @Override
    
    public int doCommand(ArrayList<String> args, int index) {
        if(args.get(index+1).equals("bind")){
            return 1;
        }
        Variable tempVar=Interpreter.symbolTable.get(args.get(index-1));
        if(tempVar.varName.contains("/")) {
            ConnectCommand.output.println("set " + tempVar.varName + " " + DijakstraAlgo.calc(args.get(index + 1),args,index));
            ConnectCommand.output.flush();
            return 1;
        }
        tempVar.setValue(DijakstraAlgo.calc(args.get(index + 1),args,index));
        return 1;
    }
    
    
    
//    public int doCommand(ArrayList<String> arr,int index) {
////        if (Interpreter.symbolTable.get(arr.get(index-1))!=null){
////            if (!arr.get(index+1).equals("bind")) {
////                Variable tempVar = Interpreter.symbolTable.get(arr.get(index-1));
////                double res = DijakstraAlgo.calc(arr.get(index+1),arr,index);
////                if (tempVar.varName.contains("/")){
////                    output.println("set " + tempVar.varName + " " + res);
////                    System.out.println("set " + tempVar.varName + " " + res);
////                    output.flush();
////                }
////                else{
////                    tempVar.setValue(res);
////                }
////            }
////            else{
////                if (!Interpreter.symbolTable.containsKey(arr.get(index+2))) {
////                    Interpreter.symbolTable.put(arr.get(index+2), new Variable(0.0,arr.get(index + 2)));
////                }
////                Interpreter.symbolTable.replace(arr.get(index-1),Interpreter.symbolTable.get(arr.get(index+2)));
////            }
////        }
////        return 2;
//    	
//    	
//    }
}
