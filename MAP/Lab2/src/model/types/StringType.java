package model.types;

import com.sun.jdi.BooleanType;
import exceptions.AppException;
import exceptions.InvalidOperationException;
import model.values.IValue;
import model.values.StringValue;

public class StringType implements IType {
    public boolean equals(IType other) {
        return (other instanceof StringType);
    }

    public IType compose(String operation) throws AppException {
        switch (operation) {
            case "+":
                return new StringType();
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
        throw new InvalidOperationException("Invalid Operation: Cannot compose two StringValue entities using operation " + operation);
    }

    @Override
    public IValue getDefault() {
        return new StringValue("");
    }

    public String toString() {
        return "String";
    }
}
