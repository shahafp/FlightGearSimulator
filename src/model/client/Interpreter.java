package model.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class Interpreter {

     public static HashMap<String,Expression> map = new HashMap<>();
     public static HashMap<String,Variable> symbolTable;
     private ArrayList<String> wordsArr;
     public static String simulatorVarsScript;
     public static double returnVal = 0;
     public volatile static boolean flag=false;

    public Interpreter(String simulatorVarsScript) {
        this.wordsArr = new ArrayList<>();
        this.symbolTable = new HashMap<>();
        this.simulatorVarsScript=simulatorVarsScript;
        //this.map = new HashMap<>();

        map.put("openDataServer",new CommandExpression(new OpenServerDataCommand()));
        map.put("connect",new CommandExpression(new ConnectCommand()));
        map.put("var", new CommandExpression(new DefineVarCommand()));
        map.put("=", new CommandExpression(new AssignCommand()));
        map.put("while", new CommandExpression(new WhileCommand()));
        map.put("print" , new CommandExpression(new PrintCommand()));
        map.put("sleep", new CommandExpression(new SleepCommand()));
        map.put("disconnect", new CommandExpression(new DisconnectCommand()));
        map.put("return" ,new CommandExpression(new ReturnCommand()));
        map.put("bind" ,new CommandExpression(new BindCommand()));
    }

    public void lexer(String[] lines){
        StringBuilder sb = new StringBuilder();
        for (String s : lines) {
            s = s.replaceAll(" \\+ ", "+");
            s = s.replaceAll(" \\- ", "-");
            s = s.replaceAll(" \\* ", "*");
            s = s.replaceAll(" / ", "/");
            s = s.replaceAll("\\( ", "(");
            s = s.replaceAll("\\) ", ")");
            s = s.replaceAll("\"", "");
            sb.append(s).append(" ");
        }

        Collections.addAll(wordsArr, sb.toString().replaceAll("=", " = ").split("\\s"));
        wordsArr.removeIf(s->(s.equals("")));
    }

    public void parser(){
        for(int i=0; i<wordsArr.size(); ){
           Expression e = map.get(wordsArr.get(i));
           if (e!= null) i += e.calculate(wordsArr, i);
           else{
               i++;
           }
        }
    }
}
