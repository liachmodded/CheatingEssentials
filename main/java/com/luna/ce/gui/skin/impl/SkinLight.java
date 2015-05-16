package com.luna.ce.gui.skin.impl;

import com.luna.ce.gui.a.AbstractContainer;
import com.luna.ce.gui.components.BasicSlider;
import com.luna.ce.gui.components.BasicWidget;
import com.luna.ce.gui.font.FontHandler;
import com.luna.ce.gui.font.StringCache;
import com.luna.ce.gui.renderers.ComponentRenderer;
import com.luna.ce.gui.skin.a.AbstractSkin;
import com.luna.ce.util.gl.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

import java.awt.geom.Rectangle2D;

public class SkinLight extends AbstractSkin {
    private final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    private final StringCache droidSansFont;

    {
        final int[] colorCode = new int[32];
        for (int var5 = 0; var5 < 32; ++var5) {
            int var6 = (var5 >> 3 & 1) * 85;
            int var7 = (var5 >> 2 & 1) * 170 + var6;
            int var8 = (var5 >> 1 & 1) * 170 + var6;
            int var9 = (var5 & 1) * 170 + var6;

            if (var5 == 6) {
                var7 += 85;
            }

            if (var5 >= 16) {
                var7 /= 4;
                var8 /= 4;
                var9 /= 4;
            }

            colorCode[var5] = (var7 & 255) << 16 | (var8 & 255) << 8 | var9 & 255;
        }
        droidSansFont = new StringCache(colorCode);
        droidSansFont.setDefaultFont("Droid Sans", FontHandler.getInstance().getFontSize(), true);
    }

    // Gradient - E5E5E5 -> BDBDBD
    // Solid - DEDEDE
    // Border - 6F6F6F
    @SuppressWarnings("FieldCanBeLocal")
    private final ComponentRenderer windowRenderer = new ComponentRenderer<AbstractContainer>() {
        @Override
        public void render(final AbstractContainer component) {
            // Main box
            GuiUtils.drawBorderedRect(component.getX(), component.getY(), component.getWidth(),
                    component.getHeight(), 0xFFDEDEDE, 0xFF6F6F6F);
            // Title bar thingie - Looks moar liek eOS
            GuiUtils.drawBorderedGradientRect(component.getX(), component.getY(), component.getWidth(),
                    component.isMinimized() ? 16 : 14, 0xFFE5E5E5, 0xFFBDBDBD, 0xFF6F6F6F);

            // Minimize area
            final Rectangle2D.Double q = component.getMinimizeArea();
            /*GuiUtils.drawBorderedRect(q.getX(), q.getY(), q.getWidth(), q.getHeight(),
                    component.mouseOverMinimize() || component.getMinimized() ? 0xFFAAAAAA : 0xFFBBBBBB,
                    0xFF888888);*/
            // Minimize area text
            if (q.getX() != 0 && q.getWidth() != 0) {
                fontRenderer.drawString("-", (int) (q.getX() + q.getWidth() / 2) - fontRenderer.FONT_HEIGHT / 4,
                        (int) (q.getY() + q.getHeight() / 2) - fontRenderer.FONT_HEIGHT / 2 + 1 -
                                (component.isMinimized() ? 0 : 1),
                        component.mouseOverMinimize() || component.isMinimized() ? 0xFF424242 : 0xFF858585);
            }

            // Pin area
            final Rectangle2D.Double r = component.getPinArea();
            /*GuiUtils.drawBorderedRect(r.getX(), r.getY(), r.getWidth(), r.getHeight(),
                    component.mouseOverPin() || component.getPinned() ? 0xFFAAAAAA : 0xFFBBBBBB, 0xFF888888);*/
            // Pin area text
            if (r.getX() != 0 && r.getWidth() != 0) {
                fontRenderer.drawString("#", (int) (r.getX() + r.getWidth() / 2) - fontRenderer.FONT_HEIGHT / 4,
                        (int) (r.getY() + r.getHeight() / 2) - fontRenderer.FONT_HEIGHT / 2 + 1 -
                                (component.isMinimized() ? 0 : 1),
                        component.mouseOverPin() || component.isPinned() ? 0xFF424242 : 0xFF858585);
            }

            final Rectangle2D.Double s = component.getScrollArea();
            final Rectangle2D.Double t = component.getScrollButton();
            final Rectangle2D.Double a = component.getScrollButtons().getKey();
            final Rectangle2D.Double b = component.getScrollButtons().getValue();
            if (s.getWidth() > 0 && t.getWidth() > 0 && s.getHeight() > 0 && t.getHeight() > 0) {
                // Scroll area
                GuiUtils.drawBorderedRect(s.getX(), s.getY(), s.getWidth(), s.getHeight(), 0x77AAAAAA, 0xFF000000);
                // Scrollbar
                GuiUtils.drawRect(t.getX() + 1, t.getY() + 1, t.getWidth() - 2,
                        t.getHeight() - 2, 0x77000000);

                // Top scroll button
                GuiUtils.drawBorderedRect(a.getX(), a.getY(), a.getWidth(),
                        a.getHeight(), 0x77AAAAAA, 0xFF000000);
                GL11.glTranslated(a.getX() + 3, a.getY() + 3, 0);
                GL11.glRotated(180, 0, 0, 1);
                GuiUtils.drawTriangle(0, 0, 4, 0xAA666666);
                GL11.glRotated(180, 0, 0, 1);
                GL11.glTranslated(-(a.getX() + 3), -(a.getY() + 3), 0);
                // Bottom scroll button
                GuiUtils.drawBorderedRect(b.getX(), b.getY(), b.getWidth(),
                        b.getHeight(), 0x77AAAAAA, 0xFF000000);
                GuiUtils.drawTriangle(b.getX() + 3, b.getY() + 3, 4, 0xAA666666);
            }
        }
    };

    public SkinLight() {
        super("Light");

        this.registerRenderer("title", new ComponentRenderer<BasicWidget>() {
            @Override
            public void render(final BasicWidget component) {
                if (component.getParent().isDraggable()) {
                    GuiUtils.drawString(droidSansFont, component.getText(), component.getX() + component.getParent().getPadding() * 3,
                            component.getY() + component.getParent().getPadding() * 3, 0xFF646464, true);
                } else {
                    GuiUtils.drawCenteredString(droidSansFont, component.getText(), component.getX(), component.getWidth(),
                            component.getY(), component.getHeight(), 0xFF646464, true);
                }
            }
        });

        this.registerRenderer("label", new ComponentRenderer<BasicWidget>() {
            @Override
            public void render(final BasicWidget component) {
                GuiUtils.drawCenteredString(droidSansFont, component.getText(), component.getX(), component.getWidth(),
                        component.getY(), component.getHeight(), 0xFF6F6F6F, true);
            }
        });

        this.registerRenderer("button", new ComponentRenderer<BasicWidget>() { // 4A90D9
            @Override
            public void render(final BasicWidget component) {
                GuiUtils.drawBorderedGradientRect(component.getX() + 1, component.getY() + 1,
                        component.getWidth() - 2, component.getHeight() - 2,
                        component.isMouseOver() || component.isFocused() ? 0xFF4A90D9 : 0xFF999999,
                        component.isMouseOver() || component.isFocused() ? 0xFF377FC9 : 0xFF777777, // 377FC9
                        component.isFocused() ? 0xFF131313 : 0xFF000000
                );

                GuiUtils.drawCenteredString(droidSansFont, component.getText(), component.getX() + 1,
                        component.getWidth() - 2, component.getY() - 1, component.getHeight(), 0xFFFFFFFF,
                        true);
            }
        });

        this.registerRenderer("slider", new ComponentRenderer<BasicSlider>() {
            @Override
            public void render(final BasicSlider component) {
                GuiUtils.drawBorderedGradientRect(component.getX() + 1, component.getY() + 1,
                        component.getWidth() - 2, component.getHeight() - 2,
                        component.isMouseOver() || component.isFocused() ? 0xFFAAAAAA : 0xFF999999,
                        component.isMouseOver() || component.isFocused() ? 0xFF888888 : 0xFF777777,
                        0xFF000000
                );

                GuiUtils.drawBorderedGradientRect(component.getX() + 1, component.getY() + 1,
                        (component.getWidth() - 2) * component.getAmountScrolled(), component.getHeight() - 2,
                        0xFF4A90D9, 0xFF377FC9,
                        component.isMouseOver() ? 0xFF175FA9 : 0xFF000000
                );

                GuiUtils.drawCenteredString(droidSansFont, String.format("%s: %s", component.getValue().getName(), component.getValue().getValue()),
                        component.getX() + 1, component.getWidth() - 2, component.getY() - 1, component.getHeight(), // - 2,
                        0xFFFFFFFF, true);
            }
        });

        this.registerRenderer("container", windowRenderer);
        this.registerRenderer("frame", windowRenderer);
    }

    @Override
    public StringCache getFont() {
        return droidSansFont;
    }
}
