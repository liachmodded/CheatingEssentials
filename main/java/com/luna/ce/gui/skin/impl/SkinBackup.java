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
