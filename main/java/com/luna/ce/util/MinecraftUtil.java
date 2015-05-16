package com.luna.ce.util;

import com.luna.lib.reflection.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Timer;

public class MinecraftUtil {
    public final Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public final WorldClient getWorld() {
        return getMinecraft().theWorld;
    }

    public final AbstractClientPlayer getPlayer() {
        return getMinecraft().thePlayer;
    }

    public final Timer getTimer() {
        try {
            return (Timer) ReflectionHelper.findObfuscatedFieldByName(Minecraft.class, "timer").get(Minecraft.getMinecraft());
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public final void addChatMessage(final String... args) {
        for (final String e : args) {
            getPlayer().addChatMessage(new ChatComponentText(FormatUtil.formatMessage(e)));
        }
    }

    public final void displayGuiScreen(GuiScreen guiScreen) {
        getMinecraft().displayGuiScreen(guiScreen);
    }
}
