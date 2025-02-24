package model.adt;

import exceptions.ExpressionException;
import model.state.symTable.SymTable;
import model.values.IValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyMap<K, V> implements IMyMap<K, V> {
    private Map<K, V> map;

    public MyMap() {
        map = new HashMap<K, V>();
    }

    public V get(K key) throws ExpressionException {
        if (!map.containsKey(key)) {
            throw new ExpressionException("Key not found");
        }
        return map.get(key);
    }

    public void insert(K key, V value) {
        map.put(key, value);
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public void remove(K key) throws ExpressionException {
        if (!map.containsKey(key)) {
            throw new ExpressionException("Key not found");
        }
        map.remove(key);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (K key : map.keySet()) {
            str.append(key.toString()).append(" -> ").append(map.get(key));
            str.append("\n");
        }
        return "MyMap contains: " + str.toString();
    }

    public Set<K> keySet() {
        return map.keySet();
    }

    public int getSize()
    {
        return map.size();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    public void setContent(Map<K,V> newMap) {
        map.clear();
        for (K i : newMap.keySet()) {
            map.put(i, newMap.get(i));
        }
    }

    public Map<K,V> getContent()
    {
        return map;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return map.entrySet();
    }


    public MyMap<K, V> copy() {
        MyMap<K, V> newMap = new MyMap<>();
        for (Map.Entry<K, V> entry : this.entrySet()) {
            newMap.insert(entry.getKey(), entry.getValue());
        }
        return newMap;
    }


}