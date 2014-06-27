package com.luna.ce.util.keybind.interfaces;

public interface IKeybindModifier {
    IKeybindModifier addModifier(int modifier);

    IKeybindModifier removeModifier(int modifier);

    Integer[] getModifiers();
}
