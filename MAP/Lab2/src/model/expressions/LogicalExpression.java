package model.expressions;

import exceptions.ExpressionException;
import model.adt.IMyMap;
import model.adt.MyMap;
import model.state.ProgramState;
import model.state.heap.Heap;
import model.state.symTable.SymTable;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class LogicalExpression implements IExpression {
    private IExpression expression1;
    private IExpression expression2;
    private LogicalOperation operation;

    public LogicalExpression(IExpression expression1, IExpression expression2, LogicalOperation operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operation.toString().toLowerCase() + " " + expression2.toString();
    }

    @Override
    public IValue evaluate(SymTable symTable, Heap heap) throws ExpressionException {
        var leftExpression = expression1.evaluate(symTable, heap);
        var rightExpression = expression2.evaluate(symTable, heap);

        if(!leftExpression.getType().equals(new BoolType()) || !rightExpression.getType().equals(new BoolType())) {
            throw new ExpressionException("Left and right expressions are not the same");
        }
        if (operation == LogicalOperation.AND) {
            return new BoolValue(((BoolValue) leftExpression).getValue() && ((BoolValue) rightExpression).getValue());
        } else {
            return new BoolValue(((BoolValue) leftExpression).getValue() || ((BoolValue) rightExpression).getValue());
        }

    }

    @Override
    public IType typecheck(MyMap<String, IType> typeDictionary) throws ExpressionException {
        IType type1 = expression1.typecheck(typeDictionary);
        IType type2 = expression2.typecheck(typeDictionary);

        // Ensure both expressions are booleans
        if (!type1.equals(new BoolType())) {
            throw new ExpressionException("First operand is not of type Bool");
        }

        if (!type2.equals(new BoolType())) {
            throw new ExpressionException("Second operand is not of type Bool");
        }

        // If both are booleans, the result is also a boolean
        return new BoolType();
    }

}