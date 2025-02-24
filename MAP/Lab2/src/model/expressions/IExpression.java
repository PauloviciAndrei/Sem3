package model.expressions;

import exceptions.AppException;
import exceptions.ExpressionException;
import model.adt.IMyMap;
import model.adt.MyMap;
import model.state.ProgramState;
import model.state.heap.Heap;
import model.state.symTable.SymTable;
import model.types.IType;
import model.values.IValue;

public interface IExpression {
    IValue evaluate(SymTable state, Heap heap) throws ExpressionException;

    IType typecheck(MyMap<String, IType> typeDictionary) throws ExpressionException;
}