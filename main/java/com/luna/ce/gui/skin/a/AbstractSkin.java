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

package com.luna.ce.gui.skin.a;

import com.luna.ce.gui.a.AbstractComponent;
import com.luna.ce.gui.a.AbstractContainer;
import com.luna.ce.gui.font.StringCache;
import com.luna.ce.gui.renderers.ComponentRenderer;
import com.luna.ce.gui.skin.manager.SkinManager;
import com.luna.ce.util.gl.GuiUtils;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map.Entry;

public abstract class AbstractSkin {
    private final String name;

    private final HashMap<String, ComponentRenderer> rendererRegistry;

    public AbstractSkin(final String name) {
        this.name = name;
        rendererRegistry = new HashMap<String, ComponentRenderer>();
    }

    protected final <T extends ComponentRenderer> void registerRenderer(final String type, final T renderer) {
        synchronized (rendererRegistry) {
            rendererRegistry.put(type, renderer);
        }
    }

    public final HashMap<String, ComponentRenderer> getRenderers() {
        synchronized (rendererRegistry) {
            return rendererRegistry;
        }
    }

    @SuppressWarnings("unchecked")
    public final void renderComponentByRenderType(final AbstractComponent e) {
        for (final Entry<String, ComponentRenderer> en : getRenderers().entrySet()) {
            if (en.getKey().equals(e.getTagValue("type"))) {
                en.getValue().render(e);
                return;
            }
        }
        SkinManager.getInstance().getBackupSkin().renderComponentByRenderType(e);
    }

    public final void renderContainer(final AbstractContainer container) {
        try {
            renderComponentByRenderType(container);
            renderComponentByRenderType(container.getTitle());

            final Rectangle2D.Double scissorArea = container.getScrollComponents();

            final Rectangle2D.Double s = container.getScrollArea();
            final Rectangle2D.Double t = container.getScrollButton();

            if (!container.isMinimized() && s.getWidth() > 0 && t.getWidth() > 0 && s.getHeight() > 0 &&
                    t.getHeight() > 0) {
                GuiUtils.enableScissoring();
                GuiUtils.scissor(scissorArea.getX(), scissorArea.getY(), scissorArea.getWidth(),
                        scissorArea.getHeight());
                for (final AbstractComponent e : container.getChildren()) {
                    if (container.getScrollComponentsContains(e.getArea())) {
                        if (e instanceof AbstractContainer) {
                            renderContainer((AbstractContainer) e);
                        } else {
                            renderComponentByRenderType(e);
                        }
                    }
                }
            }
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public StringCache getFont() {
        return GuiUtils.stringCache;
    }
}
