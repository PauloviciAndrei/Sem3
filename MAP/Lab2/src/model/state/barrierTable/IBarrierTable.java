package model.state.barrierTable;

import exceptions.AppException;
import javafx.util.Pair;
import model.adt.ADTPair;
import model.adt.IMyMap;

import java.util.List;

public interface IBarrierTable extends IMyMap<Integer, Pair<Integer, List<Integer>>> {
    int put(Pair<Integer, List<Integer>> value) throws AppException;
    int getFirstFreeLocation();
}
