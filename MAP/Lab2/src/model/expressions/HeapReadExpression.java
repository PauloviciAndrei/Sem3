package model.expressions;

import exceptions.AppException;
import exceptions.ExpressionException;
import model.adt.MyMap;
import model.state.ProgramState;
import model.state.heap.Heap;
import model.state.symTable.SymTable;
import model.types.IType;
import model.types.ReferenceType;
import model.values.IValue;
import model.values.ReferenceValue;

public class HeapReadExpression implements IExpression{
    IExpression expression;

    public HeapReadExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(SymTable state, Heap heap) throws AppException {
        IValue value = null;
        try
        {
           value  = expression.evaluate(state, heap);
        } catch (ExpressionException e) {
            throw new RuntimeException(e);
        }

        System.out.println("VALUE: " + value + " " + "TYPE: " + value.getType().toString());

        if(!(value.getType() instanceof ReferenceType)){
            throw new AppException("Heap should only be accessed through references");
        }

        try
        {
            return heap.read(((ReferenceValue)value).getAddress());
        } catch (ExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public IType typecheck(MyMap<String, IType> typeDictionary) throws ExpressionException {
        IType exprType = expression.typecheck(typeDictionary);
        if(!(exprType instanceof ReferenceType)) {
            throw new AppException("Read Heap expression evaluates to a non RefType");
        }
        return ((ReferenceType)exprType).getInner();
    }

    public String toString() {
        return String.format("HeapRead(%s)", expression);
    }

}
