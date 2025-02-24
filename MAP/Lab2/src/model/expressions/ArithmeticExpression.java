package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyMap;
import model.state.ProgramState;
import model.state.heap.Heap;
import model.state.symTable.SymTable;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class ArithmeticExpression implements IExpression {

    private IExpression expression1;
    private IExpression expression2;
    private char operation;

    public ArithmeticExpression(char op, IExpression expression1, IExpression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = op;
    }

    public IValue evaluate(SymTable state, Heap heap) throws ExpressionException {
        var leftExpr = expression1.evaluate(state, heap);
        var rightExpr = expression2.evaluate(state, heap);

        if(!leftExpr.getType().equals(new IntType()) || !rightExpr.getType().equals(new IntType()))
            throw new ExpressionException("Expressions are not integers");

        int value1 = ((IntValue)leftExpr).getValue();
        int value2 = ((IntValue)rightExpr).getValue();

        if(operation == '+')
            return new IntValue(value1 + value2);

        if(operation == '-')
            return new IntValue(value1 - value2);

        if(operation == '*')
            return new IntValue(value1 * value2);
        else
            if(value2 == 0)
                throw new ExpressionException("Cannot divide by zero");
            else
                return new IntValue(value1 / value2);

    }

    public IType typecheck(MyMap<String, IType> typeDictionary) throws ExpressionException {
        IType type1 = expression1.typecheck(typeDictionary);
        IType type2 = expression2.typecheck(typeDictionary);

        if (!type1.equals(new IntType())) {
            throw new ExpressionException("First operand is not of type Int");
        }

        if (!type2.equals(new IntType())) {
            throw new ExpressionException("Second operand is not of type Int");
        }

        return new IntType();
    }

    public String toString() {
        return expression1.toString() + " " + operation + " " + expression2.toString();
    }
}
