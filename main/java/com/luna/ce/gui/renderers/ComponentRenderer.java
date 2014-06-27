package com.luna.ce.gui.renderers;

import com.luna.ce.gui.a.AbstractComponent;

public abstract class ComponentRenderer<T extends AbstractComponent> {
    public abstract void render(T component);
}
