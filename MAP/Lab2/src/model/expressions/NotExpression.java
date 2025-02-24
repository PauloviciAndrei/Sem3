package model.expressions;

import com.sun.jdi.Value;
import exceptions.ExpressionException;
import model.adt.MyMap;
import model.state.heap.Heap;
import model.state.symTable.SymTable;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class NotExpression implements IExpression {

    IExpression expression;

    public NotExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IType typecheck(MyMap<String, IType> typeDictionary) throws ExpressionException {
        IType type1;
        type1 = expression.typecheck(typeDictionary);
        if (type1.equals(new BoolType())) {
            return new BoolType();
        }else
            throw new ExpressionException("First operand is not an boolean");
    }

    @Override
    public IValue evaluate(SymTable table, Heap heap) throws ExpressionException {
        IValue v1;
        v1 = expression.evaluate(table, heap);
        if (v1.getType().equals(new BoolType())) {
            BoolValue b1 = (BoolValue) v1;
            boolean firstBool = b1.getValue();
            return new BoolValue(!firstBool);
        } else
            throw new ExpressionException("First operand is not an boolean");
    }

    @Override
    public String toString() {
        return "!" + expression.toString();
    }
}
