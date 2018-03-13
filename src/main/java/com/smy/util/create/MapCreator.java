package com.smy.util.create;

import java.util.HashMap;
import java.util.Map;

/**
 * @author smy
 * @since 2018/1/10
 */
public class MapCreator<K, V> implements Creator<Map<K, V>> {

    protected Map<K, Creator<V>> creatorMap = new HashMap<>();

    public MapCreator<K, V> addCreator(K key, Creator<V> creator) {
        creatorMap.put(key, creator);
        return this;
    }


    @Override
    public Map<K, V> create() {
        Map<K, V> map = new HashMap();
        creatorMap.forEach((k, v) -> {
            map.put(k, v.create());
        });
        return map;
    }
}
