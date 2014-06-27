package com.luna.ce.util.keybind;

import com.luna.ce.util.keybind.interfaces.IKeybind;

public class Keybind implements IKeybind {
    private int key;

    public Keybind(int key) {
        this.key = key;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public void setKey(int newKey) {
        key = newKey;
    }
}
