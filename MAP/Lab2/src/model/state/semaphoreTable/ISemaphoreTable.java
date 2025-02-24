package model.state.semaphoreTable;

import exceptions.AppException;
import javafx.util.Pair;
import model.adt.MyMap;

import java.util.List;

public interface ISemaphoreTable {

    void put(int key, Pair<Integer, List<Integer>> value) throws AppException;
    Pair<Integer, List<Integer>> get(int key) throws AppException;
    boolean containsKey(int key);
    int getFreeAddress();
    void setFreeAddress(int freeAddress);
    void update(int key, Pair<Integer, List<Integer>> value) throws AppException;
    MyMap<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable();
    List<Pair<Pair<Integer, Integer>, List<Integer>>> getSemaphoreDictionaryAsList();
    void setSemaphoreTable(MyMap<Integer, Pair<Integer, List<Integer>>> newSemaphoreTable);
}

