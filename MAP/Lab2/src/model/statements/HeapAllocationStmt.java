package model.statements;

import exceptions.*;
import model.adt.MyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.state.heap.Heap;
import model.state.symTable.SymTable;
import model.types.IType;
import model.types.ReferenceType;
import model.values.IValue;
import model.values.ReferenceValue;

public class HeapAllocationStmt implements IStmt {
    private final String variableName;
    private final IExpression expression;

    public HeapAllocationStmt(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, KeyNotFoundException {
        SymTable symbolTable = state.getSymTable();
        Heap heap = state.getHeap();

        if (!symbolTable.containsValue(variableName)) {
            throw new StatementException(String.format("Heap Allocation Error: %s is not defined.", variableName));
        }

        IValue variableValue = symbolTable.getValue(variableName);
        if (!(variableValue.getType() instanceof ReferenceType)) {
            throw new StatementException(String.format("Heap Allocation Error: %s is not of type Reference.", variableName));
        }

        IValue evaluated = expression.evaluate(symbolTable, state.getHeap());
        IType locationType = ((ReferenceValue) variableValue).getInnerType();
        if (!locationType.equals(evaluated.getType())) {
            throw new StatementException(String.format("Heap Allocation Error: %s is not of type %s.", variableName, evaluated.getType()));
        }

        Integer newPosition = heap.allocate(evaluated);
        try{
            symbolTable.setValue(variableName, new ReferenceValue(newPosition, locationType));
        }
        catch (KeyNotFoundException e)
        {
            throw new StatementException(String.format("Heap Allocation Error: %s is not defined.", variableName));
        }


        return null;
    }

    public MyMap<String, IType> typecheck(MyMap<String, IType> typeTable) throws StatementException, ExpressionException {
        IType typeVariable = typeTable.get(variableName);
        IType typeExpression = expression.typecheck(typeTable);

        if (!typeVariable.equals(new ReferenceType(typeExpression))) {
            throw new StatementException("Heap Allocation Error: Variable and expression types are not compatible.");
        }

        return typeTable;
    }


    @Override
    public String toString() {
        return String.format("New(%s, %s)", variableName, expression);
    }
}
