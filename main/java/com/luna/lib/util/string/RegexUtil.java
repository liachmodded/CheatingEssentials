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
