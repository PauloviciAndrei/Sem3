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

public class IfStmt implements IStmt {
    private IExpression exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(IExpression exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public String toString() {
        return "IF(" + exp.toString() + ") THEN {" + thenS.toString() +
                "}ELSE {" + elseS.toString() +"}";
    }

    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        IValue expVal = this.exp.evaluate(state.getSymTable(), state.getHeap());
        if (!(expVal.getType() instanceof BoolType)) {
            throw new StatementException(expVal.toString() + " is not a boolean");
        }
        if (((BoolValue)expVal).getValue()) {
            state.getExeStack().push(thenS);
        } else {
            state.getExeStack().push(elseS);
        }

        return null;
    }

    public MyMap<String, IType> typecheck(MyMap<String, IType> typeTable) throws StatementException, ExpressionException {
        if (!exp.typecheck(typeTable).equals(new BoolType())) {
            throw new StatementException("Conditional Error: The condition is not of type bool.");
        }

        thenS.typecheck(typeTable.copy());
        elseS.typecheck(typeTable.copy());
        return typeTable;
    }
}