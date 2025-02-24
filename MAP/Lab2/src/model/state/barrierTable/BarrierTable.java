package model.state.barrierTable;

import exceptions.AppException;
import javafx.util.Pair;
import model.adt.ADTPair;
import model.adt.IMyMap;
import model.adt.MyMap;

import java.util.List;

public class BarrierTable extends MyMap<Integer, Pair<Integer, List<Integer>>> implements IBarrierTable {

    private int nextFreeLocation;

    public BarrierTable() {
        super();
        this.nextFreeLocation = 1;
    }


    public void insert(Integer key, Pair<Integer, List<Integer>> value) {
        super.insert(key, value);
        synchronized (this) {
            nextFreeLocation++;
        }
    }

    @Override
    public int put(Pair<Integer, List<Integer>> value) throws AppException {
        super.insert(nextFreeLocation, value);
        synchronized (this) {
            nextFreeLocation++;
        }
        return nextFreeLocation - 1;
    }

    @Override
    public int getFirstFreeLocation() {
        int locationAddress = 1;
        while (this.getContent().get(locationAddress) != null)
            locationAddress++;
        return locationAddress;

    }public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BarrierTable:");

        for (Integer key : this.getContent().keySet()) {
            Pair<Integer, List<Integer>> value = this.getContent().get(key);
            sb.append("\n").append(key).append(" -> ")
                    .append("(").append(value.getKey()).append(", ")
                    .append(value.getValue()).append(")");
        }

        return sb.toString();
    }


}
