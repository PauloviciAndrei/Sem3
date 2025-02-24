package model.state.symTable;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.SymbolNotFoundException;
import model.values.IValue;
import exceptions.SymbolAlredyExistsException;

public interface ISymTable
{
    public void declareValue(String name, IValue value) throws SymbolAlredyExistsException;

    public IValue getValue(String name) throws ExpressionException;

    public void setValue(String name, IValue value) throws KeyNotFoundException,ExpressionException;

    public boolean containsValue(String name);


}
