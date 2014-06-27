package com.luna.lib.datastructures;

/**
 * Stores 2 things, like a Tuple in Python.
 * <p>
 * And for what I use this for, it'd be a waste to make some kind of Map.
 *
 * @param <K>
 * @param <V>
 * @author godshawk
 */
public final class Tuple<K, V> {

    private K thing1;
    private V thing2;

    public Tuple(final K thing1, final V thing2) {
        this.thing1 = thing1;
        this.thing2 = thing2;
    }

    public K getKey() {
        return thing1;
    }

    public void setKey(final V v) {
        this.thing2 = v;
    }

    public V getValue() {
        return thing2;
    }

    public void setEntry(final K k) {
        this.thing1 = k;
    }
}
