package com.luna.ce.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;

public class CEHUD {
    public static void drawHUDStuff() {
        final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        final EntityClientPlayerMP thePlayer = Minecraft.getMinecraft().thePlayer;
        final String coords = String.format("<%s, %s, %s>", (int) thePlayer.posX, (int) thePlayer.posY,
                (int) thePlayer.posZ);
        final String fps = String.format("%s FPS", Minecraft.getMinecraft().debug.split(" ")[0]);
        fontRenderer.drawString(coords, GuiUtils.getWidth() - fontRenderer.getStringWidth(coords) - 2,
                GuiUtils.getHeight() - 11, 0xFFFFFFFF, true);
        fontRenderer.drawString(fps, GuiUtils.getWidth() - fontRenderer.getStringWidth(fps) - 2,
                GuiUtils.getHeight() - 21, 0xFFFFFFFF, true);
    }
}
