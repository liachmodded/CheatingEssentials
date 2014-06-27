package com.luna.ce.gui.action.i;

import com.luna.ce.gui.a.AbstractComponent;
import com.luna.ce.gui.action.i.base.IGuiAction;

public interface MouseDownAction<T extends AbstractComponent> extends IGuiAction<T> {
    void onClick(T component, int button);
}
