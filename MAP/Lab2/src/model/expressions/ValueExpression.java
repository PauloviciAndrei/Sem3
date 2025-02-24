package model.expressions;

import model.adt.IMyMap;
import model.adt.MyMap;
import model.state.ProgramState;
import model.state.heap.Heap;
import model.state.symTable.SymTable;
import model.types.IType;
import model.values.IValue;

public class ValueExpression implements IExpression {
    private IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue evaluate(SymTable symTable, Heap heap) {
        return value;
    }

    @Override
    public IType typecheck(MyMap<String, IType> typeDictionary) {
        return value.getType();
    }


    @Override
    public String toString() {
        return value.toString();
    }
}