package com.andersen.islam.hw1.util;

import com.andersen.islam.hw1.Reservation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Storage<T> implements Iterable<T> {
    private List<T> items = new ArrayList<>();

    public void add(Optional<T> item) {
        items.add((T) item);
    }

    public List<T> getAll() {
        return items;
    }

    public boolean remove(T item) {
        return items.remove(item);
    }

    public void clear() {
        items.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }


}
