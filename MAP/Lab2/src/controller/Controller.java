package controller;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyMap;
import model.state.ProgramState;
import model.state.executionStack.ExecutionStack;
import model.state.fileTable.FileTable;
import model.state.heap.Heap;
import model.types.IType;
import model.types.ReferenceType;
import model.values.IValue;
import model.values.ReferenceValue;
import repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import exceptions.AppException;
import model.statements.IStmt;
import model.state.symTable.SymTable;
import model.state.output.Output;

public class Controller implements IController{

    IRepository repository;
    boolean dispFlag = true;

    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
        executor = Executors.newFixedThreadPool(2);
    }

    public void setDisplayFLag(boolean state)
    {
        this.dispFlag = state;
    }

    public void addProgram(IStmt statement) {
       ProgramState programState = new ProgramState(statement);
        repository.addProgram(programState);
        System.out.println("ProgramState added to repository with ID: " + programState.toString());
    }

    public List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromHeap(Collection<IValue> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates){
        List<ProgramState> toReturn = new ArrayList<>();
        for (ProgramState programState : programStates) {
            if (programState.isNotCompleted()) {
                toReturn.add(programState);
            }
        }

        /*
        if (toReturn.isEmpty() && !programStates.isEmpty()) {
            toReturn.add(programStates.get(0));
        }

         */
        return toReturn;
    }

    public Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, MyMap<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> ( symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void conservativeGarbageCollector(List<ProgramState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymTable(p.getSymTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        programStates.forEach(p -> {
            p.getHeap().setContent((HashMap<Integer, IValue>) safeGarbageCollector(symTableAddresses, getAddrFromHeap(p.getHeap().getContent().values()), p.getHeap().getContent()));
        });
    }

    public void runTypeChecker() throws StatementException, ExpressionException {
        for (ProgramState state: repository.getProgramsList()) {
            MyMap<String, IType> typeTable = new MyMap<>();

            if(!state.getExeStack().isEmpty()) {
                state.getExeStack().peek().typecheck(typeTable);
            }
        }
    }

    public void allStep() throws InterruptedException {
        try
        {
            runTypeChecker();
            List<ProgramState> programStates = removeCompletedPrograms(repository.getProgramsList());
            while (programStates.size() > 0) {
                conservativeGarbageCollector(programStates);
                executeOneStepForAll(programStates);
                programStates = removeCompletedPrograms(repository.getProgramsList());
            }
            executor.shutdownNow();
            repository.setProgramsList(programStates);
        }
        catch (StatementException e)
        {
            System.out.println(e.getMessage());
            System.out.println("EXECUTION STOPPED!");

        } catch (ExpressionException e) {
            System.out.println(e.getMessage());
            System.out.println("EXECUTION STOPPED!");
        }

    }

    public void executeOneStepForAll(List<ProgramState> programStates) throws InterruptedException {

        List<Callable<ProgramState>> callList = programStates.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::executeOneStep))
                .collect(Collectors.toList());

        List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (ExecutionException | InterruptedException e) {
                        System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        programStates.addAll(newProgramList);

        programStates.forEach(programState -> {
                repository.logProgramState(programState);
        });
        repository.setProgramsList(programStates);
    }


    public IRepository getRepository() {

        return this.repository;
    }



    /*
    @Override
    public void executeAllSteps() throws AppException {
        ProgramState program = repository.getCurrentProgram();

        while(!program.getExeStack().isEmpty()){
            program.getHeap().setContent(safeGarbageCollector(program.getSymTable(), program.getHeap()));
            executeOneStep(program);

            if(dispFlag == true)
            {
                repository.logProgramState(program);
            }
        }

        if(dispFlag == false)
            System.out.println(program.getOut().toString());
    }

    @Override
    public void setProgram(IStmt statement) {
        this.repository.clear();
        this.repository.addProgram(new ProgramState(new ExecutionStack(),
                new SymTable(),
                new Output(),
                statement,
                new FileTable(),
                new Heap()));
    }

    public IRepository getRepository() {
        return repository;
    }

     */

        /*
    HashMap<Integer, IValue> safeGarbageCollector(SymTable symbols, Heap heap) {
        HashMap<Integer, IValue> newHeap = new HashMap<>();

        for (IValue val : symbols.values()) {
            if (val instanceof ReferenceValue) {
                int address = ((ReferenceValue) val).getAddress();
                if (heap.containsKey(address)) {
                    newHeap.put(address, heap.get(address));
                }

                if(val.getType() instanceof ReferenceType) {
                    if(heap.containsKey(address)) {
                        IValue value = heap.get(address);
                        while(value instanceof ReferenceValue) {
                            int address2 = ((ReferenceValue) value).getAddress();
                            if(heap.containsKey(address2)) {
                                newHeap.put(address2, heap.get(address2));
                            }

                            value = heap.get(address2);
                        }
                    }
                }
            }
        }
        return newHeap;
    }

     */

}
