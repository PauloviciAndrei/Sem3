package model.values;

import exceptions.AppException;
import exceptions.InvalidOperationException;
import model.types.IType;
import model.types.IntType;

public class IntValue implements IValue {
    private final int value;

    public int getValue() {
        return value;
    }

    public IType getType() {
        return new IntType();
    }

    public boolean equals(IValue other) {
        return  other.getType() instanceof IntValue
                && this.getValue() == ((IntValue)other).getValue();
    }

    public IntValue(final int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public IValue copy()
    {
        return new IntValue(this.value);
    }

    private IntValue add(IntValue other) {
        return new IntValue(this.value + other.value);
    }

    private IntValue subtract(IntValue other) {
        return new IntValue(this.value - other.value);
    }

    private IntValue multiply(IntValue other) {
        return new IntValue(this.value * other.value);
    }

    private IntValue divide(IntValue other) throws AppException {
        if(other.value == 0) {
            throw new AppException("Division by zero!");
        }
        return new IntValue(this.value / other.value);
    }

    private IntValue modulo(IntValue other) throws AppException {
        if(other.value == 0) {
            throw new AppException("Division by zero!");
        }
        return new IntValue(this.value % other.value);
    }

    private BoolValue lessThan(IntValue other) {
        return new BoolValue(this.value < other.value);
    }

    private BoolValue lessThanEqual(IntValue other) {
        return new BoolValue(this.value <= other.value);
    }

    private BoolValue greaterThan(IntValue other) {
        return new BoolValue(this.value > other.value);
    }

    private BoolValue greaterThanEqual(IntValue other) {
        return new BoolValue(this.value >= other.value);
    }

    private BoolValue equal(IntValue other) {
        return new BoolValue(this.value == other.value);
    }

    private BoolValue notEqual(IntValue other) {
        return new BoolValue(this.value != other.value);
    }

    public IValue compose(IValue other, String operation) throws AppException {
        if(!(other.getType().equals(this.getType()))) {
            throw new InvalidOperationException("Invalid Operation: Cannot compose two different types using operator " + operation);
        }
        switch(operation){
            case "+": return this.add((IntValue)other);
            case "-": return this.subtract((IntValue)other);
            case "*": return this.multiply((IntValue)other);
            case "/": return this.divide((IntValue)other);
            case "<":   return this.lessThan((IntValue) other);
            case "<=":  return this.lessThanEqual((IntValue) other);
            case "==":  return this.equal((IntValue) other);
            case "!=":  return this.notEqual((IntValue) other);
            case ">":   return this.greaterThan((IntValue)other);
            case ">=":  return this.greaterThanEqual((IntValue)other);
        }
        throw new InvalidOperationException("Invalid Operation: Cannot compose two IntValue types using operator " + operation);

    }
}