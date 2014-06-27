package com.luna.ce.gui.action.i;

import com.luna.ce.gui.a.AbstractComponent;
import com.luna.ce.gui.action.i.base.IGuiAction;

public interface MouseUpAction<T extends AbstractComponent> extends IGuiAction<T> {
    void onUp(T component, int button);
}
