package com.luna.ce.util.keybind.interfaces;

public interface IKeybindable {
    void bind(IKeybind keybinding);

    void unbind();

    IKeybind getBinding();
}
