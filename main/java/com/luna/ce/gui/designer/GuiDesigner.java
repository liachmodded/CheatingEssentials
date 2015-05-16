package com.luna.ce.gui.designer;

import com.luna.ce.gui.a.AbstractContainer;
import com.luna.ce.gui.components.BasicContainer;
import com.luna.ce.gui.components.BasicWidget;
import com.luna.ce.gui.designer.item.GuiItem;
import com.luna.ce.gui.screen.base.ClickableGuiScreen;
import com.luna.ce.gui.skin.manager.SkinManager;
import com.luna.ce.util.gl.GuiUtils;
import com.luna.lib.exception.InitializationException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class GuiDesigner extends ClickableGuiScreen<GuiItem> {
    private GuiTextField textbox;
    private Rectangle2D.Double trash;
    private AbstractContainer container;

    @Override
    @SuppressWarnings("unchecked")
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        textbox = new GuiTextField(0, this.fontRendererObj, 4, 4, 100, 12);
        buttonList.add(new GuiButton(0, 4, GuiUtils.getHeight() - GuiUtils.stringCache.getStringHeight() - 32, 100, 20, "Create"));
        trash = new Rectangle2D.Double(4, GuiUtils.getHeight() - GuiUtils.stringCache.getStringHeight() - 10, 100,
                GuiUtils.stringCache.getStringHeight() + 8);
        container = new BasicContainer("Format Codes");
        container.addChild(new BasicWidget("{p} : username", "label"));
        container.addChild(new BasicWidget("{x} : x coord", "label"));
        container.addChild(new BasicWidget("{y} : y coord", "label"));
        container.addChild(new BasicWidget("{z} : z coord", "label"));
        container.addChild(new BasicWidget("{h} : health", "label"));
        container.addChild(new BasicWidget("{f} : food", "label"));
        container.addChild(new BasicWidget("{a} : armor", "label"));
        container.addChild(new BasicWidget("{b} : breath", "label"));
        container.addChild(new BasicWidget("{i} : item name", "label"));
        container.addChild(new BasicWidget("{d} : item id", "label"));
        container.addChild(new BasicWidget("{e} : heading", "label"));
        container.addChild(new BasicWidget("{s} : fps", "label"));
        container.setX(4);
        container.setY(28);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        textbox.updateCursorCounter();
    }

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        GuiUtils.drawRect(0, 0, 108, GuiUtils.getHeight(), 0x77000000);
        super.drawScreen(par1, par2, par3);

        String text = "Gui Designer";
        int w = GuiUtils.stringCache.getStringWidth(text) / 2;
        GuiUtils.drawString(text, GuiUtils.getWidth() / 2 - w, 2, 0xFFFFFFFF, true);

        textbox.drawTextBox();
        container.tick();
        SkinManager.getInstance().getCurrentSkin().renderContainer(container);
        String trashStr = "Trash";
        int w2 = GuiUtils.stringCache.getStringWidth(trashStr) / 2;
        GuiUtils.drawRect(trash.getX(), trash.getY(), trash.getWidth(), trash.getHeight(), 0x77000000);
        GuiUtils.drawString(trashStr, 54 - w2, GuiUtils.getHeight() - GuiUtils.stringCache.getStringHeight() - 7,
                0xFFFFFFFF, true);
        for (GuiItem e : getThings()) {
            if (trash.intersects(e.getArea()) && !e.isDragging()) {
                getThings().remove(e);
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
        if ((key == Keyboard.KEY_RETURN) && (textbox.isFocused())) {
            if (textbox.getText().equals("")) {
                return;
            }
            GuiItem item = new GuiItem(textbox.getText());
            item.setX(114);
            item.setY(4);
            addThing(item);
            textbox.setText("");
            return;
        }
        if (textbox.isFocused()) {
            textbox.textboxKeyTyped(ch, key);
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        try {
            super.mouseClicked(par1, par2, par3);
        } catch (IOException ex) {

        }
        textbox.mouseClicked(par1, par2, par3);
        if (container.isMouseOver()) {
            container.focus();
            container.clickMouse(par3);
        } else {
            container.unfocus();
        }
    }

    @Override
    protected void mouseReleased(final int par1, final int par2, final int par3) {
        super.mouseReleased(par1, par2, par3);
        if (container.isFocused()) {
            container.mouseUp(par3);
        }
    }

    @Override
    protected void mouseClickMove(final int par1, final int par2, final int par3, final long par4) {
        super.mouseClickMove(par1, par2, par3, par4);
        if (container.isFocused()) {
            container.mouseDrag(par3);
        }
    }

    @Override
    public void initialize() throws InitializationException {
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            switch (p_146284_1_.id) {
                case 0:
                    if (textbox.getText().equals("")) {
                        break;
                    }
                    GuiItem item = new GuiItem(textbox.getText());
                    item.setX(114);
                    item.setY(4);
                    addThing(item);
                    textbox.setText("");
                    break;
                default:
                    break;
            }
        }
    }
}
