package model.state.heap;

import exceptions.ExpressionException;
import exceptions.InvalidAddressException;
import model.values.IValue;

import java.util.Arrays;
import java.util.Set;

public interface IHeap {

    int allocate(IValue value);

    void deallocate(int address) throws ExpressionException;

    void write(int address, IValue value) throws InvalidAddressException;

    IValue read(int address) throws ExpressionException;

    public Set<Integer> keySet();
}
