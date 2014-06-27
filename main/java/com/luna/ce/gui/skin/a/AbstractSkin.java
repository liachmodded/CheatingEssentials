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

import org.lwjgl.opengl.GL11;

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
            if (en.getKey().equalsIgnoreCase(e.getTagValue("type"))) {
                en.getValue().render(e);
                return;
            }
        }
        SkinManager.getInstance().getBackupSkin().renderComponentByRenderType(e);
    }

    public final void renderContainer(final AbstractContainer container) {
    	GL11.glPushMatrix();
        try {
            renderComponentByRenderType(container);
            renderComponentByRenderType(container.getTitle());

            final Rectangle2D.Double scissorArea = container.getScrollComponents();

        	if(container.isNeedsScrolling()) {
                GuiUtils.enableScissoring();
                GuiUtils.scissor(scissorArea.getX(), scissorArea.getY(), scissorArea.getWidth(),
	                        scissorArea.getHeight());
        	}
            for (final AbstractComponent e : container.getChildren()) {
                if (container.getScrollComponentsContains(e.getArea())) {
                    if (e instanceof AbstractContainer) {
                        renderContainer((AbstractContainer) e);
                    } else {
                        renderComponentByRenderType(e);
                    }
                }
            }
            if(container.isNeedsScrolling()) {
                GuiUtils.scissor(0, 0, GuiUtils.getWidth(), GuiUtils.getHeight());
                GuiUtils.disableScissoring();
            }
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
        GL11.glPopMatrix();
    }

    public String getName() {
        return name;
    }

    public StringCache getFont() {
        return GuiUtils.stringCache;
    }
}
