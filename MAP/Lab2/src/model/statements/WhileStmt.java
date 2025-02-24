package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class WhileStmt implements IStmt {
    private final IExpression expression;
    private final IStmt statement;

    public WhileStmt(IExpression expression, IStmt statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        IValue value = expression.evaluate(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new BoolType())) {
            throw new StatementException(String.format("While Error: %s is not of type bool.", value));
        }

        BoolValue boolValue = (BoolValue) value;
        if (boolValue.getValue()) {
            state.getExeStack().push(this);
            state.getExeStack().push(statement);
        }

        return null;
    }

    public MyMap<String, IType> typecheck(MyMap<String, IType> typeTable) throws StatementException, ExpressionException {
        IType type = expression.typecheck(typeTable);

        if (type.equals(new BoolType())) {
            statement.typecheck(typeTable.copy());
            return typeTable;
        }

        throw new StatementException("While Error: The condition is not of type bool.");
    }

    @Override
    public String toString() {
        return String.format("While(%s) {%s}", expression, statement);
    }
}
