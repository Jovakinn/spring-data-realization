package com.spark.jovakinn.unsafe_starter.realizations;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class OrderedBag<T> {
    private final List<T> list;

    public OrderedBag(Object[] args) {
        this.list = new ArrayList(asList(args));
    }

    public T takeAndRemove() {
        return list.remove(0);
    }

    public int size() {
        return list.size();
    }
}
