package com.luna.ce.util.keybind;

import com.luna.ce.util.keybind.interfaces.IKeybindModifier;

import java.util.ArrayList;
import java.util.List;

public class MultiKeybind extends Keybind implements IKeybindModifier {
    private List<Integer> modifiers;

    public MultiKeybind(int key) {
        super(key);
        modifiers = new ArrayList<Integer>();
    }

    @Override
    public MultiKeybind addModifier(int modifier) {
        modifiers.add(modifier);
        return this;
    }

    @Override
    public MultiKeybind removeModifier(int modifier) {
        modifiers.remove(modifier);
        return this;
    }

    @Override
    public Integer[] getModifiers() {
        return modifiers.toArray(new Integer[modifiers.size() - 1]);
    }
}
