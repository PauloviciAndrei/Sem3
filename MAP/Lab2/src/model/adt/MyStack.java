package model.adt;

import exceptions.EmptyStackException;
import model.statements.IStmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements IMyStack<T> {
    Stack<T> stack;

    public MyStack() {
        stack = new Stack<T>();
    }

    public T pop() throws EmptyStackException {
        if (stack.isEmpty()) {
            throw new EmptyStackException("Stack is empty!");
        }
        return stack.pop();
    }

    public void push(T element) {
        stack.push(element);
    }

    public int getSize() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    Stack<T> getStack() {
        return this.stack;
    }

    void setStack(Stack<T> stack) {
        this.stack = stack;
    }

    public T peek() throws EmptyStackException{
        if(stack.isEmpty()){
            throw new EmptyStackException("Stack is empty!");
        }
        return stack.peek();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (T item : stack) {
            str.append(item);
            str.append("\n");
        }
        return "MyStack contains: " + str.toString();
    }

    public List<T> getReversed() {
        List<T> reversedList = new ArrayList<>();
        Stack<T> tempStack = new Stack<>();

        // Use a temporary stack to reverse the elements
        while (!stack.isEmpty()) {
            tempStack.push(stack.pop());
        }

        // Populate the reversed list and restore the original stack
        while (!tempStack.isEmpty()) {
            T element = tempStack.pop();
            reversedList.add(element);
            stack.push(element); // Restore the original stack
        }

        return reversedList;
    }

}