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
 * Usage example: Sliders in a GUI
 *
 * @author godshawk
 */
public class Value {

    private final String name;
    private final float defaultValue;
    private final float incrementValue;
    private final boolean round;
    private float value, minimalValue, maxValue;

    public Value(final String valName, final float defaultVal, final float minVal, final float maxVal) {
        this(valName, defaultVal, minVal, maxVal, -1);
    }

    public Value(final String valName, final float defaultVal, final float minVal, final float maxVal,
                 final float incrementVal) {
        this(valName, defaultVal, minVal, maxVal, incrementVal, true);
    }

    public Value(final String valName, final float defaultVal, final float minVal, final float maxVal,
                 final float incrementVal, final boolean round) {
        name = valName;
        value = defaultVal;
        defaultValue = defaultVal;
        minimalValue = minVal;
        maxValue = maxVal;
        incrementValue = incrementVal;
        this.round = round;
    }

    public float getValue() {
        return value;
    }

    public void setValue(final float value) {
        this.value = round ? Math.round(value) : value;
    }

    public float getMin() {
        return minimalValue;
    }

    public void setMin(final float min) {
        minimalValue = min;
    }

    public float getMax() {
        return maxValue;
    }

    public void setMax(final float max) {
        maxValue = max;
    }

    public float getDef() {
        return defaultValue;
    }

    public float getIncrementValue() {
        return incrementValue;
    }

    public String getName() {
        return name;
    }

}
