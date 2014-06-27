package com.luna.ce.gui.screen.base;

import com.luna.ce.gui.a.AbstractContainer;

public abstract class ContainerGuiScreen extends ClickableGuiScreen<AbstractContainer> {
    public ContainerGuiScreen() {
        super();
        initialWindowPositioning();
    }

    private void initialWindowPositioning() {
        int offset = 0;
        for (AbstractContainer e : getThings()) {
            e.setX(2);
            e.setY(16 * offset + 2);
            e.setMinimized(true);
            offset++;
        }
    }
}
