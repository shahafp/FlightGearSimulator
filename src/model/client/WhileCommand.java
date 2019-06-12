package model.client;


import java.util.ArrayList;
import java.util.LinkedList;


public class WhileCommand extends ConditionCommand {

    @Override //uses the super class doCommand to lexer through the loop block
    public int doCommand(ArrayList<String> arr, int index) {
        super.doCommand(arr,index);
        while (conditionCheck(this.signList,this.conditionNumbers,this.predList)){
            //ConditionCheck - conditional command func which checks if the predicate is true or false
            parser(this.loopCommand);
        }
        System.out.println(3+this.loopCommand.size() + this.conditionNumbers.size());
        int back = 3+this.loopCommand.size() + this.conditionNumbers.size();
        this.conditionNumbers = new LinkedList<>();
        this.loopCommand = new ArrayList<>();
        this.predList = new LinkedList<>();
        this.signList = new LinkedList<>();
        return back;
    }



}
