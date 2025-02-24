package model.expressions;

import exceptions.ExpressionException;
import model.adt.IMyMap;
import model.adt.MyMap;
import model.state.ProgramState;
import model.state.heap.Heap;
import model.state.symTable.SymTable;
import model.types.IType;
import model.values.IValue;

public class VariableExpression implements IExpression {
    private String variable;

    public VariableExpression(String variable) {
        this.variable = variable;
    }

    @Override
    public IValue evaluate(SymTable symTable, Heap heap) throws ExpressionException {
        return symTable.getValue(variable);
    }

    @Override
    public IType typecheck(MyMap<String, IType> typeDictionary) throws ExpressionException {
        if (!typeDictionary.containsKey(variable)) {
            throw new ExpressionException("Variable '" + variable + "' is not defined in the type environment.");
        }
        return typeDictionary.get(variable);
    }


    @Override
    public String toString() {
        return variable;
    }
}