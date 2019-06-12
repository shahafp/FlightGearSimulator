package model.client;

import java.util.ArrayList;
import java.util.LinkedList;


public class ConditionCommand implements Command {

    ArrayList<String> loopCommand;
    LinkedList<String> conditionNumbers;
    LinkedList<String> signList;
    LinkedList<String> predList;


    ConditionCommand() {
        this.loopCommand = new ArrayList<>();
        this.conditionNumbers = new LinkedList<>();
        this.signList = new LinkedList<>();
        this.predList = new LinkedList<>();
    }

    void parser(ArrayList<String> arr){
        for (int i=0;i<arr.size();){
            Expression e = Interpreter.map.get(arr.get(i));
            if (e!=null) i+=e.calculate(arr,i);
            else i++;
        }
    }

    boolean conditionCheck(LinkedList<String> signList, LinkedList<String> numList, LinkedList<String> predList){
        int i = 0;
        double numLeft, numRight;
        boolean flag = false;
        LinkedList<String> signs = new LinkedList<>();//Temp list for the sign
        LinkedList<String> preds = new LinkedList<>(); //Temp list for the && or || signs
        LinkedList<String> nums = new LinkedList<>(); //Temp list for the number we checking


        while (!signList.isEmpty()) {
            signs.addFirst(signList.peekLast());
            String sign = signList.pollLast();
            //The if's statement get's the left and right numbers of the condition and also checks if its 1 of the variable in the SymbolTable
            if (Interpreter.symbolTable.get(numList.get(i))!=null) numLeft=(Double)Interpreter.symbolTable.get(numList.get(i)).getValue();
            else numLeft = Double.parseDouble(numList.get(i));

            i++;

            if (Interpreter.symbolTable.get(numList.get(i))!=null) numRight=(Double)Interpreter.symbolTable.get(numList.get(i)).getValue();
            else numRight = Double.parseDouble(numList.get(i));

            nums.addFirst(numList.remove(i-1));
            nums.addFirst(numList.remove(i-1));
            i++;

            assert sign != null;
            switch (sign) {
                case "<":
                    if (numLeft < numRight) flag = true;
                    break;
                case ">":
                    if (numLeft > numRight) flag = true;
                    break;
                case "==":
                    if(numLeft == numRight) flag = true;
                    break;
                case "!=":
                    if (numLeft != numRight) flag = true;
                    break;
                    default:
                        break;
            }

            // Checking for && sign or || to build the rest of the condition
            if (!predList.isEmpty()) {
                if (predList.peekLast().equals("&&")){
                    preds.add(predList.removeLast());
                    flag = flag && conditionCheck(signList,numList,predList);
                }
                else if (predList.peekLast().equals("||")){
                    preds.add(predList.removeLast());
                    flag = flag || conditionCheck(signList, numList, predList);
                }
                break; // break the loop because we dont want to keep check the same numbers again (recursive function)
            }
        }
        // Re-enter signs to the list because the loop has to be done several times
        signList.addAll(signs);
        // Re-enter predicates sign to the list because the loop has to be done several times
        predList.addAll(preds);
        // Re-enter Numbers sign to the list because the loop has to be done several times
        for (String s:nums) {
            //Using foreach to add the number in the right order
            numList.addFirst(s);
        }

        return flag;
    }


    @Override
    public int doCommand(ArrayList<String> arr, int index) {
        int i=index+1;
        System.out.println(i);
        while (!arr.get(i).equals("{")) {
            if (arr.get(i).equals("<") || arr.get(i).equals(">") || arr.get(i).equals("==") || arr.get(i).equals("!=")){
                this.signList.addFirst(arr.get(i));
            }
            else if (arr.get(i).equals("&&") || arr.get(i).equals("||")){
                this.predList.addLast(arr.get(i));
            }
            else this.conditionNumbers.add(arr.get(i));
            i++;
        }
        i++;
        while(!arr.get(i).equals("}")) {
            this.loopCommand.add(arr.get(i));
            i++;
        }
        return 0;
    }
}
