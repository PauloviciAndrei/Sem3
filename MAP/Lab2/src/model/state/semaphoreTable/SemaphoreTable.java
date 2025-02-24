package model.state.semaphoreTable;

import exceptions.AppException;
import exceptions.ExpressionException;
import javafx.util.Pair;
import model.adt.MyMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreTable implements ISemaphoreTable{

    private MyMap<Integer, Pair<Integer, List<Integer>>> semaphoreTable;
    ReentrantLock lock;
    private int freeLocation = 0;

    public SemaphoreTable() {
        this.semaphoreTable = new MyMap<>();
        this.lock = new ReentrantLock();
    }

    @Override
    public void put(int key, Pair<Integer, List<Integer>> value) throws AppException {
        synchronized (this) {
            if (!semaphoreTable.containsKey(key)) {
                semaphoreTable.insert(key, value);
            } else {
                throw new AppException("Semaphore table already contains the key!");
            }
        }
    }

    @Override
    public Pair<Integer, List<Integer>> get(int key) throws AppException {
        synchronized (this) {
            if (semaphoreTable.containsKey(key)) {
                try
                {
                    return semaphoreTable.get(key);
                } catch (ExpressionException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new AppException(String.format("Semaphore table doesn't contain the key %d!", key));
            }
        }
    }

    @Override
    public boolean containsKey(int key) {
        synchronized (this) {
            return semaphoreTable.containsKey(key);
        }
    }

    @Override
    public int getFreeAddress() {
        synchronized (this) {
            freeLocation++;
            return freeLocation;
        }
    }

    @Override
    public void setFreeAddress(int freeAddress) {
        synchronized (this) {
            this.freeLocation = freeAddress;
        }
    }

    @Override
    public void update(int key, Pair<Integer, List<Integer>> value) throws AppException {
        synchronized (this) {
            if (semaphoreTable.containsKey(key)) {
                semaphoreTable.insert(key, value);
            } else {
                throw new AppException("Semaphore table doesn't contain key");
            }
        }
    }

    @Override
    public MyMap<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable() {
        synchronized (this) {
            return semaphoreTable;
        }
    }

    @Override
    public void setSemaphoreTable(MyMap<Integer, Pair<Integer, List<Integer>>> newSemaphoreTable) {
        synchronized (this) {
            this.semaphoreTable = newSemaphoreTable;
        }
    }

//    MyMap<Integer, Pair<Integer, List<Integer>>> to list

    public List<Pair<Pair<Integer, Integer>, List<Integer>>> getSemaphoreDictionaryAsList() {
        this.lock.lock();
        try {
            List<Pair<Pair<Integer, Integer>, List<Integer>>> answer = new ArrayList<>();

            for (Map.Entry<Integer, Pair<Integer, List<Integer>>> entry : this.semaphoreTable.entrySet()) {
                Integer key = entry.getKey();
                Pair<Integer, List<Integer>> value = entry.getValue();
                answer.add(new Pair<>(new Pair<>(key, value.getKey()), value.getValue()));
            }

            return answer;
        } finally {
            this.lock.unlock();
        }
    }


    @Override
    public String toString() {
        return semaphoreTable.toString();
    }
}
