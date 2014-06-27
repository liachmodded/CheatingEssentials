package com.luna.ce.gui.action.defaults;

import com.luna.ce.gui.a.AbstractComponent;
import com.luna.ce.gui.action.i.KeyAction;
import org.lwjgl.input.Keyboard;

public class DefaultKeyGuiAction<T extends AbstractComponent> implements KeyAction<T> {
    @Override
    public void onKey(final T component, final int key, final char c) {
        if (key == Keyboard.KEY_SPACE) {
            component.clickMouse(0);
        }
    }
}
