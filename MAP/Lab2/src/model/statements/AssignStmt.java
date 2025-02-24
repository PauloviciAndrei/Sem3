package model.statements;

import exceptions.AppException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.MyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.IType;
import model.values.IValue;

public class AssignStmt implements IStmt{
    private String variable;
    private IExpression exp;

    public AssignStmt(String variable, IExpression exp) {
        this.variable = variable;
        this.exp = exp;
    }

    public ProgramState execute(ProgramState state) throws ExpressionException, StatementException {
        if (!state.getSymTable().containsValue(this.variable)) {
            throw new ExpressionException("The variable is not defined.");
        }


        IValue evalValue = this.exp.evaluate(state.getSymTable(), state.getHeap());
        IType type = state.getSymTable().getValue(this.variable).getType();

        if (evalValue.getType().equals(type)) {
            try
            {
                state.getSymTable().setValue(this.variable, evalValue);
            } catch (KeyNotFoundException e) {
                throw new AppException("Key " + this.variable + " not found.");
            }
            catch (ExpressionException e)
            {
                throw new AppException("Expression " + this.variable + " and " + evalValue + " cannot be evaluated.");
            }

        } else {
            throw new StatementException("The values do not match.");
        }

        return null;
    }

    public MyMap<String, IType> typecheck(MyMap<String, IType> typeTable) throws StatementException, ExpressionException {
        if (!typeTable.get(variable).equals(exp.typecheck(typeTable))) {
            throw new StatementException("Assignment Error: Variable and expression types are not compatible.");
        }
        return typeTable;
    }

    public String toString(){
        return variable + " = " + exp.toString();
    }
}