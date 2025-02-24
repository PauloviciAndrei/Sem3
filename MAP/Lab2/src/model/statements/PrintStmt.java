package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.IType;

public class PrintStmt implements IStmt {
    private final IExpression expr;

    public PrintStmt(IExpression e) {
        this.expr = e;
    }

    public ProgramState execute(ProgramState state) throws ExpressionException {
        var res = expr.evaluate(state.getSymTable(), state.getHeap());
        state.getOut().appendToOutput(res.toString());
        return  null;
    }

    public MyMap<String, IType> typecheck(MyMap<String, IType> typeTable) throws StatementException, ExpressionException {
        expr.typecheck(typeTable);
        return typeTable;
    }

    @Override
    public String toString() {
        return  "print("+expr.toString()+")";
    }
}