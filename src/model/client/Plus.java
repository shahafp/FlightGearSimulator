package model.client;

import java.util.ArrayList;

public class Plus extends BinaryExpression {

    Plus(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double calculate(ArrayList<String> arr, int index) {
        return this.left.calculate(arr,index) + this.right.calculate(arr,index);
    }

}
