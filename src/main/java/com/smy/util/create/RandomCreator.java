package com.smy.util.create;

import java.util.Random;

/**
 * @author smy
 * @since 2018/1/10
 */
public class RandomCreator<T> extends CollectionCreator<T> {


    protected Random random = new Random();

    public RandomCreator() {
    }

    public RandomCreator(T... ts) {
        super(ts);
    }

    @Override
    public T create() {
        return list.get(random.nextInt(list.size()));
    }

}
