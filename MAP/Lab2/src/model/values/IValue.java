package model.values;

import exceptions.AppException;
import model.types.IType;

public interface IValue {
    IType getType();
    boolean equals(IValue other);
    public IValue compose(IValue other, String operation) throws AppException;
    IValue copy();
}