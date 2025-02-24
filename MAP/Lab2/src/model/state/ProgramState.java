package model.state;
import exceptions.*;
import model.state.barrierTable.BarrierTable;
import model.state.executionStack.ExecutionStack;
import model.state.fileTable.FileTable;
import model.state.heap.Heap;
import model.state.output.Output;
import model.state.barrierTable.BarrierTable;
import model.state.symTable.SymTable;
import model.statements.*;

import java.util.concurrent.atomic.AtomicInteger;

public class ProgramState {
    ExecutionStack exeStack;
    SymTable symTable;
    Output out;
    IStmt originalProgram;
    FileTable fileTable;
    Heap heap;
    BarrierTable barrierTable;
    int id;
    private static AtomicInteger idCounter = new AtomicInteger(0);

    public ProgramState(ExecutionStack stk, SymTable symtbl, Output out, IStmt prg, FileTable fileTable, Heap heap, BarrierTable barrierTable)
    {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = out;
        this.originalProgram = prg;
        this.fileTable = fileTable;
        this.heap = heap;
        this.barrierTable = barrierTable;
        exeStack.push(prg);
        id = setId();
    }

    public ProgramState(IStmt originalProgram) {
        exeStack = new ExecutionStack();
        symTable = new SymTable();
        out = new Output();
        heap = new Heap();
        fileTable = new FileTable();
        barrierTable = new BarrierTable();
        exeStack.push(originalProgram);
        id = setId();
    }

    private Integer setId() {
        return idCounter.incrementAndGet();
    }

    public int getId() {
        return id;
    }

    public ExecutionStack getExeStack() {
        return this.exeStack;
    }

    public SymTable getSymTable() {
        return this.symTable;
    }

    public Output getOut() {
        return this.out;
    }

    public FileTable getFileTable() {
        return fileTable;
    }

    public Heap getHeap()
    {
        return heap;
    }

    public BarrierTable getBarrierTable() { return this.barrierTable; }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public ProgramState executeOneStep() throws StatementException, ExpressionException
    {
        IStmt currentStatement = null;

        if (exeStack.isEmpty()) {
            System.out.println("EXE STACK: " + exeStack.toString() + "IS EMPTY: " + exeStack.isEmpty());
            //throw new StatementException("Execution Stack Error: Execution stack is empty.");
        }

        try
        {
            currentStatement = exeStack.pop();
        }
        catch (EmptyStackException e)
        {

            throw new StatementException("Execution Stack Error: Execution stack is empty.");
        }

        try
        {
            return currentStatement.execute(this);
        } catch (KeyNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public String toString()
    {
        return  "===============" + " ID = " + id + " ==============\n" +
                "\n" + exeStack.toString() +
                "\n" + symTable.toString() +
                "\n" + out.toString() +
                 fileTable.toString() +
                heap.toString()+
                barrierTable.toString()+
                "\n==============================\n";
    }

}