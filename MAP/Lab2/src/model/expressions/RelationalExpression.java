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

public class RelationalExpression implements IExpression {

    private final IExpression expression1;

    private final IExpression expression2;

    String operator;

    public RelationalExpression(IExpression expression1, IExpression expression2, String operator) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(SymTable symTable, Heap heap) throws ExpressionException {
        IValue value1 = expression1.evaluate(symTable, heap);
        IValue value2 = expression2.evaluate(symTable, heap);

        if(!value1.getType().equals(new IntType())) {
            throw new ExpressionException("Relational Error: The first operand is not an integer.");
        }
        if(!value2.getType().equals(new IntType())) {
            throw new ExpressionException("Relational Error: The second operand is not an integer.");
        }

        int intValue1 = ((IntValue)value1).getValue();
        int intValue2 = ((IntValue)value2).getValue();

        switch (operator) {
            case ">":
                return new BoolValue(intValue1 > intValue2);
            case "<":
                return new BoolValue(intValue1 < intValue2);
            case ">=":
                return new BoolValue(intValue1 >= intValue2);
            case "<=":
                return new BoolValue(intValue1 <= intValue2);
            case "==":
                return new BoolValue(intValue1 == intValue2);
            case "!=":
                return new BoolValue(intValue1 != intValue2);
            default:
                throw new ArithmeticException("Invalid operand!");
        }
    }

    @Override
    public IType typecheck(MyMap<String, IType> typeDictionary) throws ExpressionException {
        IType type1 = expression1.typecheck(typeDictionary);
        IType type2 = expression2.typecheck(typeDictionary);

        // Ensure both expressions are integers
        if (!type1.equals(new IntType())) {
            throw new ExpressionException("Relational Error: The first operand is not of type Int.");
        }

        if (!type2.equals(new IntType())) {
            throw new ExpressionException("Relational Error: The second operand is not of type Int.");
        }

        // If both operands are integers, the result is a boolean
        return new BoolType();
    }


    public String toString() {
        return expression1.toString() + " " + operator + "  " + expression2.toString();
    }
}
