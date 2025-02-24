package model.statements;

import com.sun.jdi.Value;
import exceptions.AppException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import javafx.util.Pair;
import model.adt.ADTPair;
import model.adt.MyMap;
import model.state.ProgramState;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.List;

public class AwaitStmt implements IStmt{
    private String variableName;

    public AwaitStmt(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public MyMap<String, IType> typecheck(MyMap<String, IType> typeTable) throws ExpressionException {

            IType variableType = typeTable.get(variableName);
            if (variableType == null)
                throw new ExpressionException(String.format("Variable %s has not been declared!", variableName));
            if (!variableType.equals(new IntType()))
                throw new ExpressionException(String.format("Variable %s should be of integer type!", variableName));

        return typeTable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException {
        try {
            IValue variableValue = state.getSymTable().getValue(variableName);
            if (variableValue == null)
                throw new ExpressionException(String.format("Variable %s has not been declared!", variableName));
            if (!variableValue.getType().equals(new IntType()))
                throw new ExpressionException(String.format("Variable %s should be of integer type!", variableName));

            int variableInt = ((IntValue) variableValue).getValue();
            Pair<Integer, List<Integer>> barrierTableEntry = state.getBarrierTable().get(variableInt);
            if (barrierTableEntry == null)
                throw new ExpressionException("Invalid barrier table location!");

            int barrierTableValue = barrierTableEntry.getKey();
            List<Integer> barrierPrograms = barrierTableEntry.getValue();

            if (barrierTableValue > barrierPrograms.size()) {
                if (!barrierPrograms.contains(state.getId()))
                    barrierPrograms.add(state.getId());
                state.getExeStack().push(this);
            }
        } catch (ExpressionException e) {
            throw new ExpressionException(e.getMessage());
        }

        return null;
    }

    @Override
    public String toString() {
        return String.format("await(%s)", variableName);
    }
}
