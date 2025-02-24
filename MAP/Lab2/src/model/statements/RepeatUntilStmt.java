package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.IMyStack;
import model.adt.MyMap;
import model.adt.MyStack;
import model.expressions.IExpression;
import model.expressions.NotExpression;
import model.state.ProgramState;
import model.state.executionStack.ExecutionStack;
import model.types.BoolType;
import model.types.IType;

public class RepeatUntilStmt implements IStmt{
    IStmt statement;
    IExpression expression;

    public RepeatUntilStmt(IStmt statement, IExpression expression) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        ExecutionStack exeStack = state.getExeStack();
        IStmt whileIStmt = new CompositeStmt(statement, new WhileStmt(new NotExpression(expression), statement));
        exeStack.push(whileIStmt);
        return null;
    }

    @Override
    public MyMap<String, IType> typecheck(MyMap<String, IType> typeEnv) throws ExpressionException {
        IType expressionType = expression.typecheck(typeEnv);
        try
        {
            statement.typecheck(typeEnv);
        } catch (StatementException e) {
            throw new RuntimeException(e);
        }
        if (expressionType.equals(new BoolType())) {
            try
            {
                statement.typecheck(typeEnv.copy());
            } catch (StatementException e) {
                throw new RuntimeException(e);
            }
            return typeEnv;
        } else
            throw new ExpressionException("The IExpression of Repeat IStmt was not Boolean");
    }

    @Override
    public String toString() {
        return String.format("repeat(%s) until %s", statement.toString(), expression.toString());
    }
}
