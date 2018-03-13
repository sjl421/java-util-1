package com.smy.util.create;

/**
 * @author smy
 * @since 2018/1/10
 */
public class SeqCreator<T> extends CollectionCreator<T> {

    protected int point = 0;

    public SeqCreator() {
    }

    public SeqCreator(T... ts) {
        super(ts);
    }


    @Override
    public T create() {
        return list.get(point++ % list.size());
    }

}
