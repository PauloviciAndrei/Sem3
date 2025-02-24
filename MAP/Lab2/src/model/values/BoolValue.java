package model.values;

import exceptions.InvalidOperationException;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;

public class BoolValue implements IValue {
    private final boolean value;

    public boolean getValue() {
        return value;
    }

    public IType getType() {
        return new BoolType();
    }

    public boolean equals(IValue other) {
        return  other.getType() instanceof BoolValue
                && this.getValue() == ((BoolValue)other).getValue();
    }

    public BoolValue(final boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public IValue copy()
    {
        return new BoolValue(this.value);
    }

    public IValue compose(IValue other, String operation) throws InvalidOperationException {
        if(other instanceof BoolValue){
            BoolValue otherBoolValue = (BoolValue) other;
            switch(operation){
                case "and": return new BoolValue(this.value && otherBoolValue.value);
                case "or":  return new BoolValue(this.value || otherBoolValue.value);
                case "==":  return new BoolValue(this.value == otherBoolValue.value);
                case "!=":  return new BoolValue(this.value != otherBoolValue.value);
            }
        }
        throw new InvalidOperationException("Invalid Operation: Cannot compose two BoolValue type values using operation " + operation);
    }
}