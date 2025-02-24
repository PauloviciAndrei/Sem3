package model.state.executionStack;

import exceptions.EmptyStackException;
import model.statements.IStmt;

public interface IExecutionStack {

    public IStmt pop() throws EmptyStackException;
    public void push(IStmt stmt);
    public boolean isEmpty();
    public int size();
    public String toString();
    public IStmt peek();
}
