/*
 * Isis modified client for Minecraft.
 * Copyright (C) 2014-2015  godshawk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * ===========================================================================
 *
 * ALL SOURCE CODE WITHOUT THIS COPYRIGHT IS THE PROPERTY OF ITS RESPECTIVE
 * OWNER(S). I CLAIM NO RIGHT TO OR OWNERSHIP OF ANY OF IT.
 *
 * Minecraft is owned by Mojang AB.
 * Java itself is owned by Oracle.
 * All other code is not owned by me.
 * Thank you, and have a good day!
 */

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
