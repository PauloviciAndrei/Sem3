package model.statements;

import exceptions.AppException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import javafx.util.Pair;
import model.adt.ADTPair;
import model.adt.MyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.Vector;

public class NewBarrierStmt implements IStmt{
    public String variableName;
    public IExpression expression;

    public NewBarrierStmt(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }
    public String getVariableName() {
        return variableName;
    }
    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
    public IExpression getExpression() {
        return expression;
    }
    public void setExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public MyMap<String, IType> typecheck(MyMap<String, IType> typeTable) throws AppException {
        try {
            IType expressionType = expression.typecheck(typeTable);
            if (!expressionType.equals(new IntType()))
                throw new AppException(String.format("Expression %s should evaluate to an integer type!", expression.toString()));

            IType variableType = typeTable.get(variableName);
            if (variableType == null)
                throw new AppException(String.format("Variable %s has not been declared!", variableName));
            if (!variableType.equals(new IntType()))
                throw new AppException(String.format("Variable %s should be of integer type!", variableName));

        } catch (ExpressionException e) {
            throw new AppException(e.getMessage());
        }

        return typeTable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws AppException {
        try {
            IValue expressionEval = expression.evaluate(state.getSymTable(), state.getHeap());
            if (!expressionEval.getType().equals(new IntType()))
                throw new AppException(String.format("Expression %s should evaluate to an integer type!", expression.toString()));

            int expressionValue = ((IntValue) expressionEval).getValue();
            int newLocation = state.getBarrierTable().put(new Pair<>(expressionValue, new Vector<>()));

            IValue variableValue = state.getSymTable().getValue(variableName);
            if (variableValue == null)
                throw new AppException(String.format("Variable %s has not been declared!", variableName));
            if (!variableValue.getType().equals(new IntType()))
                throw new AppException(String.format("Variable %s should be of integer type!", variableName));

            state.getSymTable().setValue(variableName, new IntValue(newLocation));

        } catch (ExpressionException | KeyNotFoundException e) {
            throw new AppException(e.getMessage());
        }

        return null;
    }

    @Override
    public String toString() {
        return String.format("newBarrier(%s, %s)", variableName, expression.toString());
    }
}
