package com.luna.ce.gui.skin.impl;

import com.luna.ce.gui.designer.item.GuiItem;
import com.luna.ce.gui.renderers.ComponentRenderer;
import com.luna.ce.gui.skin.a.AbstractSkin;
import com.luna.ce.util.gl.GuiUtils;

/**
 * Has renderers for components that are the most likely to get missed
 */
public class SkinBackup extends AbstractSkin {
    public SkinBackup() {
        super("Default");

        registerRenderer("item", new ComponentRenderer<GuiItem>() {
            @Override
            public void render(GuiItem component) {
                GuiUtils.drawRect(component.getX(), component.getY(), component.getWidth(), component.getHeight(),
                        0x77000000);
                GuiUtils.drawCenteredString(component.getText(), component.getX(), component.getWidth(),
                        component.getY(), component.getHeight(), 0xFFFFFFFF, true);
            }
        });
    }
}
