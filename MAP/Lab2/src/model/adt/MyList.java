package model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IMyList<T> {
    private List<T> list;

    public MyList() {
        list = new ArrayList<T>();
    }

    public List<T> getAll() {
        return list;
    }

    public T get(int index) {
        return list.get(index);
    }

    public void add(T element) {
        list.add(element);
    }

    public void clear()
    {
        list.clear();
    }

    void setList(List<T> list) {
        this.list = list;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (T element : list) {
            str.append(element.toString());
            str.append("\n");
        }
        return "MyList contains: " + str.toString();
    }
}