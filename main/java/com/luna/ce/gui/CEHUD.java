package com.luna.ce.gui;

import com.luna.ce.util.gl.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;

public class CEHUD {
    public static void drawHUDStuff() {
        final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        final AbstractClientPlayer thePlayer = Minecraft.getMinecraft().thePlayer;
        final String coords = String.format("<%s, %s, %s>", (int) thePlayer.posX, (int) thePlayer.posY,
                (int) thePlayer.posZ);
        final String fps = String.format("%s FPS", Minecraft.getMinecraft().debug.split(" ")[0]);
        fontRenderer.drawString(coords, GuiUtils.getWidth() - fontRenderer.getStringWidth(coords) - 2,
                GuiUtils.getHeight() - 11, 0xFFFFFFFF);
        fontRenderer.drawString(fps, GuiUtils.getWidth() - fontRenderer.getStringWidth(fps) - 2,
                GuiUtils.getHeight() - 21, 0xFFFFFFFF);
    }
}
