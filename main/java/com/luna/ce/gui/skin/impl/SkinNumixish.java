/*
 * Isis modified client for Minecraft.
 * Copyright (C) 2014-2015  godshawk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * ===========================================================================
 *
 * ALL SOURCE CODE WITHOUT THIS COPYRIGHT IS THE PROPERTY OF ITS RESPECTIVE
 * OWNER(S). I CLAIM NO RIGHT TO OR OWNERSHIP OF ANY OF IT.
 *
 * Minecraft is owned by Mojang AB.
 * Java itself is owned by Oracle.
 * All other code is not owned by me.
 * Thank you, and have a good day!
 */

package com.luna.ce.gui.skin.impl;

import com.luna.ce.gui.a.AbstractContainer;
import com.luna.ce.gui.components.BasicSlider;
import com.luna.ce.gui.components.BasicWidget;
import com.luna.ce.gui.renderers.ComponentRenderer;
import com.luna.ce.gui.skin.a.AbstractSkin;
import com.luna.ce.util.gl.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

import java.awt.geom.Rectangle2D;

public class SkinNumixish extends AbstractSkin {
    private final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

    @SuppressWarnings("FieldCanBeLocal")
    private final ComponentRenderer windowRenderer = new ComponentRenderer<AbstractContainer>() {
        @Override
        public void render(final AbstractContainer component) {
            GuiUtils.drawBorderedRect(component.getX(), component.getY(), component.getWidth(),
                    component.getHeight(), 0xFF111111, 0xFF000000);

            final Rectangle2D.Double q = component.getMinimizeArea();
            GuiUtils.drawBorderedRect(q.getX(), q.getY(), q.getWidth(), q.getHeight(),
                    component.mouseOverMinimize() || component.isMinimized() ? 0xFF222222 : 0xFF777777,
                    0xFF000000);
            if (q.getX() != 0 && q.getWidth() != 0) {
                fontRenderer.drawString(component.isMinimized() ? "+" : "-", (int) (q.getX() + q.getWidth() / 2) - fontRenderer.FONT_HEIGHT / 4,
                        (int) (q.getY() + q.getHeight() / 2) - fontRenderer.FONT_HEIGHT / 2, 0xFFFFFFFF, false);
            }

            final Rectangle2D.Double r = component.getPinArea();
            GuiUtils.drawBorderedRect(r.getX(), r.getY(), r.getWidth(), r.getHeight(),
                    component.mouseOverPin() || component.isPinned() ? 0xFF222222 : 0xFF777777, 0xFF000000);
            if (r.getX() != 0 && r.getWidth() != 0) {
                fontRenderer.drawString(component.isPinned() ? "#" : "O", (int) (r.getX() + r.getWidth() / 2) - fontRenderer.FONT_HEIGHT / 4,
                        (int) (r.getY() + r.getHeight() / 2) - fontRenderer.FONT_HEIGHT / 2, 0xFFFFFFFF, false);
            }

            final Rectangle2D.Double s = component.getScrollArea();
            final Rectangle2D.Double t = component.getScrollButton();
            final Rectangle2D.Double a = component.getScrollButtons().getKey();
            final Rectangle2D.Double b = component.getScrollButtons().getValue();
            if (s.getWidth() > 0 && t.getWidth() > 0 && s.getHeight() > 0 && t.getHeight() > 0) {
                GuiUtils.drawBorderedRect(s.getX(), s.getY(), s.getWidth(), s.getHeight(), 0xAA222222, 0xFF000000);
                GuiUtils.drawBorderedRect(t.getX() + 1, t.getY() + 1, t.getWidth() - 2,
                        t.getHeight() - 2, 0x77666666, 0x77000000);
                GuiUtils.drawBorderedRect(a.getX(), a.getY(), a.getWidth(),
                        a.getHeight(), 0xFF666666, 0xFF000000);
                GL11.glTranslated(a.getX() + 3, a.getY() + 3, 0);
                GL11.glRotated(180, 0, 0, 1);
                GuiUtils.drawTriangle(0, 0, 4, 0x77FFFFFF);
                GL11.glRotated(180, 0, 0, 1);
                GL11.glTranslated(-(a.getX() + 3), -(a.getY() + 3), 0);
                GuiUtils.drawBorderedRect(b.getX(), b.getY(), b.getWidth(),
                        b.getHeight(), 0xFF666666, 0xFF000000);
                GuiUtils.drawTriangle(b.getX() + 3, b.getY() + 3, 4, 0x77FFFFFF);
            }
        }
    };

    public SkinNumixish() {
        super("Numixish");

        this.registerRenderer("title", new ComponentRenderer<BasicWidget>() {
            @Override
            public void render(final BasicWidget component) {
                if (component.getParent().isDraggable()) {
                    GuiUtils.drawString(component.getText(), (int) component.getX() + (int) component.getParent().getPadding() * 3,
                            (int) component.getY() + (int) component.getParent().getPadding() * 3, 0xFFFFFFFF, true);
                } else {
                    GuiUtils.drawCenteredString(component.getText(), component.getX(), component.getWidth(),
                            component.getY(), component.getHeight(), 0xFFFFFFFF, true);
                }
            }
        });

        this.registerRenderer("label", new ComponentRenderer<BasicWidget>() {
            @Override
            public void render(final BasicWidget component) {
                GuiUtils.drawCenteredString(component.getText(), component.getX(), component.getWidth(),
                        component.getY(), component.getHeight(), component.isFocused() ? 0xFFFF0000 : 0xFFFFFFFF, true);
            }
        });

        this.registerRenderer("button", new ComponentRenderer<BasicWidget>() {
            @Override
            public void render(final BasicWidget component) {
                GuiUtils.drawBorderedRect(component.getX() + 1, component.getY() + 1,
                        component.getWidth() - 2, component.getHeight() - 2, component.isMouseOver()
                                || component.isFocused() ? 0xFFB42715 : 0xFF666666, 0xFF000000
                );

                GuiUtils.drawCenteredString(component.getText(), component.getX() + 1,
                        component.getWidth() - 2, component.getY() - 1, component.getHeight(), 0xFFFFFFFF,
                        true);
            }
        });

        this.registerRenderer("slider", new ComponentRenderer<BasicSlider>() {
            @Override
            public void render(final BasicSlider component) {
                GuiUtils.drawBorderedRect(component.getX() + 1, component.getY() + 1,
                        component.getWidth() - 2, component.getHeight() - 2, 0xFF666666, 0xFF000000);
                GuiUtils.drawBorderedRect(component.getX() + 1, component.getY() + 1,
                        (component.getWidth() - 2) * component.getAmountScrolled(), component.getHeight() - 2,
                        0xFFB42715, 0xFF000000);

                GuiUtils.drawCenteredString(String.format("%s: %s", component.getValue().getName(), component.getValue().getValue()),
                        component.getX() + 1, component.getWidth() - 2, component.getY() - 1, component.getHeight(), // - 2,
                        0xFFFFFFFF, true);
            }
        });

        this.registerRenderer("container", windowRenderer);
        this.registerRenderer("frame", windowRenderer);
    }
}
