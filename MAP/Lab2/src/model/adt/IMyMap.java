package model.adt;

import exceptions.ExpressionException;
import model.values.IValue;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IMyMap<K, V> {
    V get(K key) throws ExpressionException;
    void insert(K key, V value);
    boolean containsKey(K key);
    void remove(K key) throws ExpressionException;
    public Set<K> keySet();
    public int getSize();
    Collection<V> values();
    public Set<Map.Entry<K, V>> entrySet();
}