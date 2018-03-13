package com.smy.util.create;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>创建者接口。
 * @author smy
 * @since 2016/8/22
 */
@FunctionalInterface
public interface Creator<T> {

    T create();

    default T createOne() {
        return create();
    }

    default Stream<T> stream(int count) {
        return Stream.generate(this::createOne).limit(count);
    }

    default T[] array(int count) {
        return (T[]) stream(count).toArray();
    }

    default List<T> list(int count) {
        return stream(count).collect(Collectors.toList());
    }


}
