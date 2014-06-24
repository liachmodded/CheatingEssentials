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

package com.luna.lib.util.string;

import java.util.Random;

/**
 * String-related utilities
 *
 * @author godshawk
 */
@SuppressWarnings("all")
public class StringUtil {

    /**
     * All the standard ASCII alphanumeric characters
     */
    private static final char[] ALPHANUM = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', '0'

    };

    /**
     * All the consonants, both cases.
     */
    private static final char[] CONSONANTS = new char[]{
            'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x',
            'z', 'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W',
            'X', 'Z'

    };

    /**
     * RNG
     */
    private static final Random rand = new Random();

    /**
     * Generates a random <code>String</code> of up to 10 characters
     *
     * @return {@link StringUtil#genRandomString(int)}
     * @see {@link StringUtil#genRandomString(int)}
     */
    public static String genRandomString() {
        return genRandomString(rand.nextInt(10));
    }

    /**
     * Generates a random <code>String</code> of the specified length
     *
     * @param len Length of the String
     * @return Random String
     */
    public static String genRandomString(final int len) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            final char c = ALPHANUM[rand.nextInt(ALPHANUM.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Returns true if <code>in</code> contains <code>out</code>.
     *
     * @param in
     * @param check
     * @return
     */
    public static boolean doesStringContain(final String in, final String check) {
        final boolean out = in.toLowerCase().contains(check.toLowerCase());
        return out;
    }

    /**
     * Like Python's String.join()
     *
     * @param strings
     * @return
     */
    public static String join(final String[] strings) {
        return join(", ", strings);
    }

    /**
     * Like Python's String.join()
     *
     * @param joiner
     * @param strings
     * @return
     */
    public static String join(final String joiner, final String[] strings) {
        String res = "";
        for (final String e : strings) {
            if (e.length() > 0) {
                res += e + joiner;
            }
        }

        return res.substring(0, res.length() - joiner.length());
    }

    public static String addED(final String in) {
        final char end = in.charAt(in.length() - 1);

        String out = in;

        if (charArrayContains(end, CONSONANTS)) {
            out += ((new Character(end)).toString());
            out += "ed";
        } else if (new Character(end).equals('y')) {
            out = in.substring(0, in.length() - 1) + "ied";
        } else if (new Character(end).equals('e')) {
            out += "d";
        } else {
            out += "ed";
        }

        return out;
    }

    /**
     * Returns true if the char[], <code>f</code>, contains the input char,
     * <code>e</code>.
     *
     * @param e
     * @param f
     * @return
     */
    private static boolean charArrayContains(final char e, final char[] f) {
        for (final char z : f) {
            if (e == z) {
                return true;
            }
        }
        return false;
    }

    public static String capitalize(final String in) {
        return in.substring(0, 1).toUpperCase().concat(in.substring(1).toLowerCase());
    }

    public static String truncate(final String string, final int len) {
        if (string.length() < len) {
            return string;
        }
        return string.substring(0, len);
    }

    public static final String[] removeFirstItem(String[] array) {
        String[] result = new String[array.length - 1];
        System.arraycopy(array, 1, result, 0, result.length);
        return result;
    }
}
