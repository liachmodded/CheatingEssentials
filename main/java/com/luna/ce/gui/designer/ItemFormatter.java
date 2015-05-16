package com.luna.ce.gui.designer;

import com.luna.lib.reflection.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
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
                .replaceAll("(?i)\\{e\\}", EnumFacing.VALUES[heading].getName2())
                .replaceAll("(?i)\\{s\\}", ReflectionHelper.getObfuscatedFieldValue(Minecraft.class, getMinecraft(), "debugFPS").toString());
    }
}
