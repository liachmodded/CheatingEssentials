package com.luna.ce.gui.font;

import net.minecraft.client.Minecraft;

@SuppressWarnings("unused")
public class FontHandler {
    private static volatile FontHandler instance;
    private boolean globalTTF = true;
    private String fontName = "Ubuntu Bold";
    private int fontSize = 16;

    public static FontHandler getInstance() {
        if (instance == null) {
            instance = new FontHandler();
        }
        return instance;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(final String fontName) {
        this.fontName = fontName;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(final int fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isGlobalTTF() {
        return globalTTF;
    }

    public void setGlobalTTF(final boolean globalTTF) {
        this.globalTTF = globalTTF;
    }

    public void initializeFontRenderer() {
        Minecraft mc = Minecraft.getMinecraft();

        /*if(this.isGlobalTTF()) {
            mc.fontRenderer = new HookFontRenderer(mc.gameSettings, new ResourceLocation(
                    "textures/font/ascii.png"), mc.renderEngine, false);
            mc.standardGalacticFontRenderer = new HookFontRenderer(mc.gameSettings, new ResourceLocation(
                    "textures/font/ascii_sga.png"), mc.renderEngine, false);
        } else {
            mc.fontRenderer = mc.fontRenderer_OLD;
            mc.standardGalacticFontRenderer = mc.standardGalacticFontRenderer_OLD;
        }   */
    }
}