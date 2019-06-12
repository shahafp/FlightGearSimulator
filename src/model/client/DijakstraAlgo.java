package model.client;
import java.util.ArrayList;
import java.util.Stack;
import java.util.LinkedList;

class DijakstraAlgo {
    static double calc(String expression, ArrayList<String> arr, int index) {

        LinkedList<String> queue = new  LinkedList<>();
        Stack<String> stack = new Stack<>();

        String[] token = expression.split("(?<=[-+*/()])|(?=[-+*/()])");

        for (String s : token) {

            if (s.equals("(")) {
                stack.push(s);
                continue;
            }
            if (s.equals(")")) {
                while (!(stack.isEmpty()) && !(stack.peek().equals("("))) {
                    queue.addLast(stack.pop());
                }
                stack.pop();
                continue;
            }

            if (s.equals("+") || s.equals("-")) {
                while (!stack.isEmpty() && !((stack.peek().equals("(")))) {
                    queue.addLast(stack.pop());
                }

                stack.push(s);
                continue;
            }
            if (s.equals("*") || s.equals("/")) {
                stack.push(s);
                continue;
            }//s.equals("x") || s.equals("y") || s.equals("z")
            if (Interpreter.symbolTable.get(s) != null) {
                queue.addLast(String.valueOf(Interpreter.symbolTable.get(s).getValue()));
            } else {//if the variable in the token is a number
                queue.addLast(s);
            }
        }

        while (!stack.isEmpty()){
            queue.addLast(stack.pop());
        }


        return (float)ExTree(queue).calculate(arr,index);
    }
    public static Expression ExTree(LinkedList<String> queue){

        if (queue.isEmpty()){return new Number(0);}
        String last = queue.pollLast();

        if (last.equals("+")){
            Expression left = ExTree(queue);
            //queue.removeLast();
            Expression right = ExTree(queue);
            return new Plus(left,right);
        }
        if (last.equals("-")){
            Expression left = ExTree(queue);
            //queue.removeLast();
            Expression right = ExTree(queue);
            return new Minus(right,left);
        }
        if (last.equals("*")){
            Expression left = ExTree(queue);
            //queue.removeLast();
            Expression right = ExTree(queue);
            return new Mul(left,right);
        }
        if (last.equals("/")){
            Expression left = ExTree(queue);
            //queue.removeLast();
            Expression right = ExTree(queue);
            return new Div(right,left);
        }
        else {
            double num = Double.parseDouble(last);
            return new Number(num);
        }
    }
}
