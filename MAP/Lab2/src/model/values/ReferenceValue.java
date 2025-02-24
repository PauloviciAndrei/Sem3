package model.values;

import exceptions.AppException;
import exceptions.InvalidOperationException;
import model.types.IType;
import model.types.ReferenceType;

public class ReferenceValue implements IValue {
    int address;
    IType innerType;

    public ReferenceValue(int address, IType innerType) {
        this.address = address;
        this.innerType = innerType;
    }


    @Override
    public IType getType() {
        return new ReferenceType(innerType);
    }

    public int getAddress() {
        return address;
    }

    @Override
    public boolean equals(IValue other) {
        return (other instanceof ReferenceValue)
                && ((ReferenceValue) other).innerType != null
                && ((ReferenceValue) other).innerType.equals(this.innerType)
                && ((ReferenceValue) other).address == this.address;
    }

    public IType getInnerType() {
        return innerType;
    }

    @Override
    public String toString() {
        return "Reference("
                + String.valueOf(this.address)
                + ", "
                + this.innerType.toString()
                + ")";
    }

    public IValue copy()
    {
        return new ReferenceValue(this.address, this.innerType);
    }

    public IValue compose(IValue other, String operation) throws AppException {
        throw new InvalidOperationException("InvalidOperationAppException: Cannot compose two RefValue entities using operator " + operation);
    }
}
