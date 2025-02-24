package model.types;

import com.sun.jdi.BooleanType;
import exceptions.AppException;
import exceptions.InvalidOperationException;
import model.values.BoolValue;
import model.values.IValue;

public class BoolType implements IType{
    public boolean equals(IType other) {
        return other instanceof BoolType;
    }

    public BoolType getType() {
        return new BoolType();
    }

    public IValue getDefault()
    {
        return new BoolValue(false);
    }

    public IType compose(String operation) throws AppException {
        switch(operation){
            case "and": return new BoolType();
            case "or":  return new BoolType();
            case "==":  return new BoolType();
            case "!=":  return new BoolType();
        }
        throw new InvalidOperationException("Invalid Operation: Cannot compose two BooleanValue types using operation " + operation);
    }

    @Override
    public String toString() {
        return "Boolean";
    }
}