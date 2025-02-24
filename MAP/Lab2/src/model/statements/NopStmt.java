package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyMap;
import model.state.ProgramState;
import model.types.IType;

public class NopStmt implements IStmt{
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public MyMap<String, IType> typecheck(MyMap<String, IType> typeTable) throws ExpressionException {
        return typeTable;
    }

    @Override
    public String toString() {
        return "no operation";
    }
}
