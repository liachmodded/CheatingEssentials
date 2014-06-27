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
