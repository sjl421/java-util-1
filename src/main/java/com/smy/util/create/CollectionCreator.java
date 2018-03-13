package com.smy.util.create;

import java.util.ArrayList;
import java.util.List;

/**
 * @author smy
 * @since 2018/1/31
 */
public abstract class CollectionCreator<T> implements Creator<T> {

    protected List<T> list = new ArrayList<>();

    public CollectionCreator() {
    }

    public CollectionCreator(T... ts) {
        add(ts);
    }

    public CollectionCreator<T> add(T... ts) {
        return add(1, ts);
    }

    public CollectionCreator<T> add(int multiple, T... ts) {
        for (T t : ts) {
            for (int i = 0; i < multiple; i++) {
                list.add(t);
            }
        }
        return this;
    }
}
