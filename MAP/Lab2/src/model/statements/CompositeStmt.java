package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.state.ProgramState;
import model.types.IType;
import model.adt.MyMap;

public class CompositeStmt implements IStmt{

    IStmt firstStmt;
    IStmt secondStmt;

    public CompositeStmt(IStmt firstStmt, IStmt secondStmt) {
        this.firstStmt = firstStmt;
        this.secondStmt = secondStmt;
    }

    public ProgramState execute(ProgramState state) {
        state.getExeStack().push(secondStmt);
        state.getExeStack().push(firstStmt);
        return null;
    }

    @Override
    public MyMap<String, IType> typecheck(MyMap<String, IType> typeTable) throws StatementException, ExpressionException {
        return secondStmt.typecheck(firstStmt.typecheck(typeTable));
    }

    public String toString() {
        return "(" + firstStmt.toString() + " ; " + secondStmt.toString() + ")";
    }
}
