package com.luna.ce.commands.classes;

import com.luna.ce.CheatingEssentials;
import com.luna.ce.commands.ACommand;

public class CommandPrefix extends ACommand {

    public CommandPrefix() {
        super("prefix");
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCommand(final String[] args) {
        if (args.length > 2) {
            throw new IllegalArgumentException();
        }

        if (args.length == 1) {
            addChatMessage(String.format("Current prefix: %s%s%s", CheatingEssentials.getInstance()
                            .getChatColor('c'), CheatingEssentials.getInstance().getCommandPrefix(),
                    CheatingEssentials.getInstance().getChatColor('r')));
            return;
        } else {
            // Must be two arguments
            CheatingEssentials.getInstance().setCommandPrefix(args[1]);
            addChatMessage(String.format("Prefix changed to %s%s%s!", CheatingEssentials.getInstance()
                            .getChatColor('c'), CheatingEssentials.getInstance().getCommandPrefix(),
                    CheatingEssentials.getInstance().getChatColor('r')));
        }
    }

    @Override
    public String getSyntax() {
        // TODO Auto-generated method stub
        return String.format("%s [new prefix]", getName());
    }

}
