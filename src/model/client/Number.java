package model.client;

import java.util.ArrayList;

public class Number implements Expression {

    private double value;

    Number(double val){
        this.value = val;
    }

    @Override
    public double calculate(ArrayList<String> arr, int index) {
        return this.value;
    }
}
