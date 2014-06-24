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

package com.luna.ce.gui.registry;

import java.util.*;

@SuppressWarnings({"unchecked", "unused"})
public class TagRegistry {
    private static final Map<String, List<String>> legalTags = new HashMap<String, List<String>>();

    static {
        registerTag("type", new String[]{
                "title", "button", "label", "container", "frame", "slider"
        });
    }

    public static void registerTag(final String tag, final String[] values) {
        synchronized (legalTags) {
            if (!legalTags.containsKey(tag)) {
                ArrayList<String> vals = new ArrayList<String>();
                vals.addAll(Arrays.asList(values));
                legalTags.put(tag, vals);
            }
        }
    }

    public static void unregisterTag(final String tag) {
        synchronized (legalTags) {
            if (legalTags.containsKey(tag)) {
                legalTags.remove(tag);
            }
        }
    }

    public static void editTag(final String tag, final String[] newValues) {
        synchronized (legalTags) {
            if (legalTags.containsKey(tag)) {
                legalTags.remove(tag);
                ArrayList<String> vals = new ArrayList<String>();
                vals.addAll(Arrays.asList(newValues));
                legalTags.put(tag, vals);
            }
        }
    }

    public static boolean validateTag(final String tag) {
        synchronized (legalTags) {
            if (legalTags.containsKey(tag)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateTagValue(final String tag, final String value) {
        synchronized (legalTags) {
            if (legalTags.containsKey(tag)) {
                if (legalTags.get(tag).contains(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void addValueToTag(String tag, String value) {
        synchronized (legalTags) {
            if (legalTags.containsKey(tag)) {
                legalTags.get(tag).add(value);
            }
        }
    }
}
