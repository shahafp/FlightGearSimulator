package model.client;

public class Variable {

    public double value;
    public String varName;

    public Variable(double value, String varName) {
        this.value = value;
        this.varName = varName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
