package model.state.executionStack;

import exceptions.AppException;
import exceptions.EmptyStackException;
import model.state.executionStack.IExecutionStack;
import model.adt.MyStack;
import model.statements.IStmt;

public class ExecutionStack implements IExecutionStack {
    private MyStack<IStmt> stack;

    public ExecutionStack() {
        this.stack = new MyStack<>();
    }

    public MyStack<IStmt> getContent()
    {
        return this.stack;
    }

    public void push(IStmt stmt) {
        stack.push(stmt);
    }

    public IStmt pop() throws EmptyStackException {
        return stack.pop();
    }

    public IStmt peek()
    {
        try{
            return stack.peek();
        } catch (EmptyStackException e) {
            throw new AppException("Stack is empty!");
        }

    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.getSize();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        MyStack<IStmt> tempStack = new MyStack<>();

        result.append("ExecutionStack: [");

        while (!stack.isEmpty()) {
            IStmt stmt;
            try
            {
                stmt = stack.pop();
            } catch (EmptyStackException e) {
                throw new AppException("Empty stack!");
            }
            tempStack.push(stmt);
            result.append(stmt).append(", ");
        }


        if (result.length() > 16) {
            result.setLength(result.length() - 2);
        }

        result.append("]");

        while (!tempStack.isEmpty()) {
            IStmt stmt;
            try
            {
                stmt = tempStack.pop();
                stack.push(stmt);
            } catch (EmptyStackException e) {
                throw new AppException("Empty stack!");
            }
        }

        return result.toString();
    }

}
