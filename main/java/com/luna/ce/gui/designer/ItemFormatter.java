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

package com.luna.ce.gui.designer;

import com.luna.lib.reflection.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;

import static net.minecraft.client.Minecraft.getMinecraft;

public class ItemFormatter {
    public static String format(String text) {
        int heading = MathHelper.floor_double((double) (getMinecraft().thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        String itemDisplayName = getMinecraft().thePlayer.getCurrentEquippedItem() == null ? "None" :
                getMinecraft().thePlayer.getCurrentEquippedItem().getDisplayName();
        String unlocalizedName = getMinecraft().thePlayer.getCurrentEquippedItem() == null ? "None" :
                getMinecraft().thePlayer.getCurrentEquippedItem().getUnlocalizedName();

        return text
                .replaceAll("(?i)\\{p\\}", getMinecraft().thePlayer.getGameProfile().getName())
                .replaceAll("(?i)\\{x\\}", Integer.toString((int) getMinecraft().thePlayer.posX))
                .replaceAll("(?i)\\{y\\}", Integer.toString((int) getMinecraft().thePlayer.posY))
                .replaceAll("(?i)\\{z\\}", Integer.toString((int) getMinecraft().thePlayer.posZ))
                .replaceAll("(?i)\\{h\\}", Integer.toString((int) getMinecraft().thePlayer.getHealth()))
                .replaceAll("(?i)\\{f\\}", Integer.toString(getMinecraft().thePlayer.getFoodStats().getFoodLevel()))
                .replaceAll("(?i)\\{a\\}", Integer.toString(getMinecraft().thePlayer.getTotalArmorValue()))
                .replaceAll("(?i)\\{b\\}", Integer.toString((int) (20 * (getMinecraft().thePlayer.getAir() / 300D))))
                        // .replaceAll("(?i)\\{c\\}", DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().getTime().getTime())) // TODO Come back to this one - Real time
                        // .replaceAll("(?i)\\{t\\}", "") // TODO Come back to this one - World time
                .replaceAll("(?i)\\{i\\}", itemDisplayName)
                .replaceAll("(?i)\\{d\\}", unlocalizedName)
                .replaceAll("(?i)\\{e\\}", Direction.directions[heading])
                .replaceAll("(?i)\\{s\\}", ReflectionHelper.getObfuscatedFieldValue(Minecraft.class, getMinecraft(), "debugFPS").toString());
    }
}
