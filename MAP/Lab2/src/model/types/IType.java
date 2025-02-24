package model.types;
import exceptions.AppException;
import model.values.IValue;

public interface IType {
    boolean equals(IType other);
    public IValue getDefault();
    public IType compose(String operation) throws AppException;
}