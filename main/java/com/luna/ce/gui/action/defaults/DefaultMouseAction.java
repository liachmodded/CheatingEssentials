package com.luna.ce.gui.action.defaults;

import com.luna.ce.gui.a.AbstractComponent;
import com.luna.ce.gui.action.i.MouseDownAction;

public class DefaultMouseAction<T extends AbstractComponent> implements MouseDownAction<T> {
    @Override
    public void onClick(final AbstractComponent component, final int button) {
        if (!component.getTagValue("type").equals("title")
                && !component.getTagValue("type").equals("label")) {
            component.setFocused(true);
            if (component.getParent() != null) {
                // component.getParent().unfocusAllChildrenExcept( component );
            }
        }
    }
}
