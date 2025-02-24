package model.statements;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.MyMap;
import model.state.ProgramState;
import model.types.IType;

public interface IStmt {
    ProgramState execute(ProgramState state) throws StatementException, ExpressionException, KeyNotFoundException;

    MyMap<String, IType> typecheck(MyMap<String, IType> typeTable) throws StatementException, ExpressionException;

    public String toString();
}