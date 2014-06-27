package com.luna.ce.gui.action.i;

import com.luna.ce.gui.a.AbstractComponent;
import com.luna.ce.gui.action.i.base.IGuiAction;

public interface KeyAction<T extends AbstractComponent> extends IGuiAction<T> {
    void onKey(final T component, final int key, final char c);
}
