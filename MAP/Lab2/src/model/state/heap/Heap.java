package model.state.heap;

import exceptions.*;
import model.adt.MyMap;
import model.values.IValue;
import exceptions.ExpressionException;

import java.util.Map;
import java.util.Set;

public class Heap implements IHeap {
    MyMap<Integer, IValue> heap;
    Integer nextFreeAddress;

    public Heap() {
        this.heap = new MyMap<>();
        this.nextFreeAddress = 0;
    }

    @Override
    public int allocate(IValue value) {
        heap.insert(nextFreeAddress, value);
        nextFreeAddress += 1;
        return nextFreeAddress - 1;
    }

    @Override
    public void deallocate(int address) throws ExpressionException {
        try {
            heap.remove(address);
        } catch (ExpressionException e) {
            throw new InvalidAddressException("Address " + Integer.toString(address) + " out of bounds.");
        }
    }

    @Override
    public void write(int address, IValue value) throws InvalidAddressException {
        if (!heap.keySet().contains(address)) {
            throw new InvalidAddressException("Address "
                    + Integer.toString(address)
                    + " out of bounds.");
        }
        heap.insert(address, value);
    }


    @Override
    public IValue read(int address) throws ExpressionException {
        try {
            return heap.get(address);
        } catch (ExpressionException e) {
            throw new InvalidAddressException("Address "
                    + Integer.toString(address)
                    + " out of bounds.");
        }
    }

    public Set<Integer> keySet()
    {
        return heap.keySet();
    }

    public boolean containsKey(int address) {
        return heap.keySet().contains(address);
    }

    public IValue get(int address) {
        try{
            return heap.get(address);
        } catch (ExpressionException e) {
            throw new AppException("Key" + Integer.toString(address) + " not found.");
        }
    }

    public void setContent(Map<Integer, IValue> newMap) {
        heap.setContent(newMap);
    }

    public MyMap<Integer, IValue> getContent() {
        return this.heap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Heap:\n");
        for (Map.Entry<Integer, IValue> entry : heap.getContent().entrySet()) {
            sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

}
