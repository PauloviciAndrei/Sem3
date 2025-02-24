package model.adt;

import exceptions.EmptyStackException;

public interface IMyStack<T> {
    T pop() throws EmptyStackException;
    void push(T element);
    int getSize();
    boolean isEmpty();
    T peek() throws EmptyStackException;
}