package model.statements;

import exceptions.*;
import model.adt.IMyMap;
import model.adt.MyMap;
import model.state.ProgramState;
import model.state.symTable.SymTable;
import model.types.IType;
import model.values.IValue;

import java.util.concurrent.ExecutionException;

public class VariableDeclarationStmt implements IStmt{

    String name;
    IType type;

    public VariableDeclarationStmt(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    public ProgramState execute(ProgramState state) throws ExpressionException, StatementException {

        SymTable symTable = state.getSymTable();
        try
        {
            symTable.declareValue(name, type.getDefault());
        } catch (SymbolAlredyExistsException e) {
            throw new AppException("Key " + name + " not found!");
        }
        return null;
    }

    public MyMap<String, IType> typecheck(MyMap<String, IType> typeTable) {
        typeTable.insert(name, type);
        return typeTable;
    }

    public String toString() {
        return  type + " " + name;
    }
}
