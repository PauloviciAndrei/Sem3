package model.types;

import com.sun.jdi.BooleanType;
import com.sun.jdi.IntegerType;
import exceptions.AppException;
import exceptions.InvalidOperationException;
import model.values.IValue;
import model.values.IntValue;

public class IntType implements IType{
    public boolean equals(IType other) {
        return other instanceof IntType;
    }

    public IntType getType() {
        return new IntType();
    }

    @Override
    public IntValue getDefault() {
        return new IntValue(0);
    }

    public IType compose(String operation) throws AppException {
        switch(operation) {
            case "+":
                return new IntType();
            case "-":
                return new IntType();
            case "*":
                return new IntType();
            case "/":
                return new IntType();
            case "<":
                return new BoolType();
            case "<=":
                return new BoolType();
            case "==":
                return new BoolType();
            case "!=":
                return new BoolType();
            case ">":
                return new BoolType();
            case ">=":
                return new BoolType();
        }
        throw new InvalidOperationException("Invalid Operation: Cannot compose two IntegerType types using operator " + operation);
    }


    @Override
    public String toString() {
        return "Int";
    }
}