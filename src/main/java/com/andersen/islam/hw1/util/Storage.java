package com.andersen.islam.hw1.util;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Storage<T> implements Iterable<T> {
    private List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add((item));
    }

    public List<T> getAll() {
        return items;
    }

    public boolean remove(T item) {
        return items.remove(item);
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }


    public int size() {
        return items.size();
    }

    public T get(int i) {
        return items.get(i);
    }
}
