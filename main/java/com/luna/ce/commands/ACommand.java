package com.luna.ce.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import com.luna.lib.interfaces.util.ICommand;

public abstract class ACommand implements ICommand {
    private final String name;

    public ACommand(final String args) {
        name = args;
    }

    @Override
    public String getName() {
        return name;
    }

    public abstract String getSyntax();

    protected void addChatMessage(final String... message) {
        for (final String e : message) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(e));
        }
    }
}
