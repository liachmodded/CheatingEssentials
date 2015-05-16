package com.luna.ce.gui.screen.base;

import com.luna.ce.gui.a.AbstractComponent;
import com.luna.ce.gui.a.AbstractContainer;
import com.luna.ce.gui.skin.manager.SkinManager;
import com.luna.ce.util.gl.GuiUtils;
import com.luna.lib.exception.InitializationException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

import org.lwjgl.input.Keyboard;

import java.io.IOException;
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
    protected void keyTyped(final char ch, final int key) throws IOException {
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
    protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
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
    protected void mouseReleased(final int par1, final int par2, final int par3) {
        super.mouseReleased(par1, par2, par3);
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
