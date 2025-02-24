package model.expressions;

import exceptions.AppException;
import exceptions.ExpressionException;
import model.adt.MyMap;
import model.state.ProgramState;
import model.state.heap.Heap;
import model.state.symTable.SymTable;
import model.types.IType;
import model.values.IValue;

public class BinaryExpression implements IExpression {

    IExpression leftHandSide;
    IExpression rightHandSide;
    String operation;

    public BinaryExpression(IExpression leftHandSide, IExpression rightHandSide, String operation) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
        this.operation = operation;
    }

    @Override
    public IValue evaluate(SymTable symTable, Heap heap) throws AppException {
        try
        {
            return leftHandSide.evaluate(symTable, heap).compose(rightHandSide.evaluate(symTable, heap), operation);
        } catch (ExpressionException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public IType typecheck(MyMap<String, IType> typeDictionary) throws AppException {

        IType firstType = null;
        IType secondType = null;
        try
        {
             firstType = leftHandSide.typecheck(typeDictionary);
             secondType = rightHandSide.typecheck(typeDictionary);
        } catch (ExpressionException e) {
            throw new RuntimeException(e);
        }
        if(!firstType.equals(secondType)) {
            throw new AppException("Different types in binary expression");
        }
        return firstType.compose(operation);
    }

    @Override
    public String toString(){
        return "("
                + leftHandSide.toString()
                + " "
                + operation
                + " "
                + rightHandSide.toString()
                + ")";
    }
}
