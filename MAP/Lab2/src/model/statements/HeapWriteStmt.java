package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.state.heap.Heap;
import model.state.symTable.SymTable;
import model.types.IType;
import model.types.ReferenceType;
import model.values.IValue;
import model.values.ReferenceValue;

public class HeapWriteStmt implements IStmt {

    private final String variableName;
    private final IExpression expression;

    public HeapWriteStmt(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        SymTable symbolTable = state.getSymTable();
        Heap heap = state.getHeap();
        if (!symbolTable.containsValue(variableName)) {
            throw new StatementException(String.format("Heap Write Error: %s is not defined.", variableName));
        }

        IValue variableValue = symbolTable.getValue(variableName);
        if (!(variableValue instanceof ReferenceValue)) {
            throw new StatementException(String.format("Heap Write Error: %s is not of type Reference.", variableValue));
        }

        ReferenceValue referenceValue = (ReferenceValue) variableValue;
        IValue evaluated = expression.evaluate(symbolTable, heap);
        if (!evaluated.getType().equals(referenceValue.getInnerType())) {
            throw new StatementException(String.format("Heap Write Error: %s is not of type %s.", evaluated, referenceValue.getInnerType()));
        }

        heap.write(referenceValue.getAddress(), evaluated);
        return null;
    }

    public MyMap<String, IType> typecheck(MyMap<String, IType> typeTable) throws StatementException, ExpressionException {
        if (typeTable.get(variableName).equals(new ReferenceType(expression.typecheck(typeTable)))) {
            return typeTable;
        }

        throw new StatementException("Heap Write Error: Variable and expression types are incompatible.");
    }

    @Override
    public String toString() {
        return String.format("WriteHeap{%s, %s}", variableName, expression);
    }
}
