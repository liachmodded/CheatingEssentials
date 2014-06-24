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

package com.luna.ce.gui.screen.base;

import com.luna.ce.gui.a.AbstractComponent;
import com.luna.ce.gui.a.AbstractContainer;
import com.luna.ce.gui.skin.manager.SkinManager;
import com.luna.lib.exception.InitializationException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import org.lwjgl.input.Keyboard;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * TODO Come up with a better name
 */
public abstract class ClickableGuiScreen<C extends AbstractComponent> extends IsisGuiScreen {
    private final List<C> things;

    public ClickableGuiScreen() {
        things = new CopyOnWriteArrayList<C>();
        try {
            initialize();
        } catch (InitializationException exception) {
            Minecraft.getMinecraft().displayGuiScreen(
                    Minecraft.getMinecraft().theWorld != null ? null : new GuiMainMenu()
            );
            exception.printStackTrace();
        }
    }

    @Override
    public final boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        drawDefaultBackground();
        super.drawScreen(par1, par2, par3);

        for (int i = things.size() - 1; i >= 0; i--) {
            things.get(i).tick();
            if (things.get(i) instanceof AbstractContainer) {
                SkinManager.getInstance().getCurrentSkin().renderContainer((AbstractContainer) things.get(i));
            } else {
                SkinManager.getInstance().getCurrentSkin().renderComponentByRenderType(things.get(i));
            }
        }
    }

    @Override
    protected void keyTyped(final char ch, final int key) {
        super.keyTyped(ch, key);
        if ((key == Keyboard.KEY_ESCAPE) && (Minecraft.getMinecraft().theWorld != null)) {
            Minecraft.getMinecraft().displayGuiScreen(null);
            return;
        }
        for (C e : things) {
            if (e.isFocused()) {
                e.keyPressed(key, ch);
                break;
            }
        }
    }

    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        super.mouseClicked(par1, par2, par3);
        for (C e : things) {
            if (e.isMouseOver()) {
                for (C f : things) {
                    if (f.equals(e)) {
                        continue;
                    }
                    f.unfocus();
                }
                e.focus();
                e.clickMouse(par3);
                if (!isInFrontOfList(e)) {
                    moveToFrontOfList(e);
                }
                break;
            }
        }
    }

    @Override
    protected void mouseMovedOrUp(final int par1, final int par2, final int par3) {
        super.mouseMovedOrUp(par1, par2, par3);
        for (C e : things) {
            if (e.isFocused()) {
                e.mouseUp(par3);
                break;
            }
        }
    }

    @Override
    protected void mouseClickMove(final int par1, final int par2, final int par3, final long par4) {
        super.mouseClickMove(par1, par2, par3, par4);
        for (C e : things) {
            if (e.isFocused()) {
                e.mouseDrag(par3);
                break;
            }
        }
    }

    private void moveToFrontOfList(C thing) {
        things.remove(thing);
        things.add(0, thing);
    }

    @SuppressWarnings("all")
    public final boolean isInFrontOfList(C thing) { // Can be used for rendering stuffs
        return (thing instanceof AbstractContainer ? ((AbstractContainer) thing).isDraggable() : true) && things.get(0).equals(thing);
    }

    public final List<C> getThings() {
        synchronized (things) {
            return things;
        }
    }

    public final void addThing(C thing) {
        synchronized (things) {
            things.add(thing);
        }
    }
}
