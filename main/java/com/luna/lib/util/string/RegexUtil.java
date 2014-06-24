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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex-related utils.
 *
 * @author godshawk
 */
@SuppressWarnings("all")
public class RegexUtil {
    /**
     * Just a basic {@link java.util.regex.Pattern}
     *
     * @param regex
     * @return
     * @see {@link java.util.regex.Pattern}
     * @see {@link java.util.regex.Pattern#compile(String)}
     */
    public static Pattern genPattern(final String regex) {
        return Pattern.compile(regex);
    }

    /**
     * Generate a {@link java.util.regex.Pattern} with flags.
     *
     * @param regex
     * @param flags
     * @return
     * @see {@link java.util.regex.Pattern}
     * @see {@link java.util.regex.Pattern#compile(String, int)}
     */
    public static Pattern genPatternWithFlags(final String regex, final int flags) {
        return Pattern.compile(regex, flags);
    }

    /**
     * Returns a {@link java.util.regex.Matcher}, using a
     * {@link java.util.regex.Pattern} compiled with the givenregex.
     *
     * @param text
     * @param regex
     * @return
     * @see {@link java.util.regex.Pattern}
     * @see {@link java.util.regex.Matcher}
     */
    public static Matcher getMatcherForRegex(final String text, final String regex) {
        return genPattern(regex).matcher(text);
    }

    /**
     * Returns a {@link java.util.regex.Matcher}, using a
     * {@link java.util.regex.Pattern} compiled with the given regex and flags.
     *
     * @param text
     * @param regex
     * @param flags
     * @return
     * @see {@link java.util.regex.Pattern}
     * @see {@link java.util.regex.Matcher}
     */
    public static Matcher getMatcherForRegexWithFlags(final String text, final String regex, final int flags) {
        return genPatternWithFlags(regex, flags).matcher(text);
    }

    /**
     * Returns <code>true</code> if the regex is found.
     *
     * @param in
     * @param regex
     * @return
     */
    public static boolean isInString(final String in, final String regex) {
        return getMatcherForRegex(in, regex).find();
    }
}
