package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyMap;
import model.state.ProgramState;
import model.state.executionStack.ExecutionStack;
import model.types.IType;
import repository.Repository;

public class ForkStmt implements IStmt {
    private final IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        ProgramState newProgramState = new ProgramState(
                new ExecutionStack(),
                state.getSymTable().copy(),
                state.getOut(),
                statement,
                state.getFileTable(),
                state.getHeap(),
                state.getBarrierTable()
        );

        return newProgramState;

    }

    public MyMap<String, IType> typecheck(MyMap<String, IType> typeTable) throws StatementException, ExpressionException {
        statement.typecheck(typeTable.copy());
        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("fork(%s)", statement);
    }
}
