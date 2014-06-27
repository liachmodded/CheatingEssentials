package com.luna.ce.util;

public final class MathUtil {
    public static int wrapTo(int number, int lower, int upper) {
        if (number > upper) {
            return upper;
        } else if (number < lower) {
            return lower;
        } else {
            return number;
        }
    }
}
