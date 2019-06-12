package model.client;

import java.util.ArrayList;

public class CommandExpression implements Expression {

    private Command c;

    CommandExpression(Command c) {
        this.c = c;
    }

    @Override
    public double calculate(ArrayList<String> arr, int index) {
        return c.doCommand(arr,index);
    }
}
