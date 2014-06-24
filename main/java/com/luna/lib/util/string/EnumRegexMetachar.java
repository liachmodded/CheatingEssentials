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

/**
 * Metacharacters for regexes. No more having to remember them! (Yes, I'm aware
 * that this is pointless.)
 *
 * @author godshawk
 */
public enum EnumRegexMetachar {
    /**
     * Can be any one character. Like ? in *nix.
     */
    ANY_CHARACTER("."),

    /**
     * Any digit; ie [0-9]
     */
    DIGIT("\\d"),

    /**
     * Any NON-digit; ie [^0-9]
     */
    NON_DIGIT("\\D"),

    /**
     * Any whitespace character; ie [ \t\n\x0B\f\r]
     */
    WHITESPACE("\\s"),

    /**
     * Any NON-whitespace; ie [^\s]
     */
    NON_WHITESPACE("\\S"),

    /**
     * Any character that can be part of a word; ie [a-zA-Z_0-9]
     */
    WORD("\\w"),

    /**
     * Any NON-word character; ie [^\w]
     */
    NON_WORD("\\W");

    final String metacharacter;

    EnumRegexMetachar(final String meta) {
        metacharacter = meta;
    }

    public final String getMetachar() {
        return metacharacter;
    }
}
