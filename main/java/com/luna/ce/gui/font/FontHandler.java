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