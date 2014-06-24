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

package com.luna.ce.util;

import com.luna.lib.reflection.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
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

    public final EntityClientPlayerMP getPlayer() {
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
