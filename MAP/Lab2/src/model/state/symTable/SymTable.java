package model.state.symTable;

import exceptions.*;
import model.adt.MyMap;
import model.adt.IMyMap;
import model.types.IType;
import model.values.IValue;

import java.util.Collection;

public class SymTable implements ISymTable {
     IMyMap<String, IValue> map;

    public SymTable() {
        map = new MyMap<>();
    }

    public IMyMap<String, IValue> getContent() {
        return map;
    }

    @Override
    public void setValue(String name, IValue value) throws ExpressionException, KeyNotFoundException {

        if(!map.containsKey(name))
            throw new KeyNotFoundException("Symbol " + name + " not found");

        if(!map.get(name).getType().equals(value.getType()))
            throw new ExpressionException("Symbol " + name + " does not have the same type");

        map.insert(name, value);
    }

    @Override
    public boolean containsValue(String name) {
        return map.containsKey(name);
    }


    public void declareValue(String name, IValue value) throws SymbolAlredyExistsException {

        if(map.containsKey(name)) {
            throw new SymbolAlredyExistsException("Symbol " + name + " already exists!");
        }
        map.insert(name, value);
    }

    public IValue getValue(String name) throws ExpressionException{
        try {
            return map.get(name);
        } catch (ExpressionException e) {
            throw new AppException("Symbol " + name + " not found");
        }

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Symbol Table:");

        for (String key : map.keySet()) {

            IValue value = null;
            try {
                value = map.get(key);
            }
            catch (ExpressionException e) {
                sb.append("Symbol " + key + " not found\n");
            }
            sb.append(key).append(" -> ").append(value).append(" ");
        }


        return sb.toString();
    }

    public Collection<IValue> values() {
        return map.values();
    }

    public SymTable copy() {
        SymTable newSymTable = new SymTable();
        for (String key : map.keySet()) {
            try {
                // Assuming IValue has a copy method
                IValue valueCopy = map.get(key).copy();
                newSymTable.declareValue(key, valueCopy);
            } catch (Exception e) {
                throw new RuntimeException("Error copying symbol table: " + e.getMessage(), e);
            }
        }
        return newSymTable;
    }

    public int getIndex(String name) throws KeyNotFoundException {
        int index = 0;
        for (String key : map.keySet()) {
            if (key.equals(name)) {
                return index;
            }
            index++;
        }
        throw new KeyNotFoundException("Symbol " + name + " not found in the symbol table.");
    }
}

