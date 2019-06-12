package model.client;

public abstract class BinaryExpression implements Expression {
    protected Expression left;
    protected Expression right;

    BinaryExpression(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }

}
