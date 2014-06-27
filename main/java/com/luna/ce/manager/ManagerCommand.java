package com.luna.ce.manager;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.ServerChatEvent;

import com.luna.ce.CheatingEssentials;
import com.luna.ce.commands.ACommand;
import com.luna.ce.commands.classes.CommandBind;
import com.luna.ce.commands.classes.CommandHelp;
import com.luna.ce.commands.classes.CommandPrefix;
import com.luna.ce.module.Module;
import com.luna.lib.interfaces.util.ICommand;

public class ManagerCommand {
    private static ManagerCommand instance;

    private final ArrayList<ICommand> commands;

    public ManagerCommand() {
        commands = new ArrayList<ICommand>();
        addModuleCommands();
        // And now we come to the annoying part!
        addCommands(new CommandHelp(), new CommandBind(), new CommandPrefix());
    }

    public static ManagerCommand getInstance() {
        if (instance == null) {
            instance = new ManagerCommand();
        }
        return instance;
    }

    private void addModuleCommands() {
        addCommands(ManagerModule.getInstance().getModules()
                .toArray(new ICommand[ManagerModule.getInstance().getModules().size()]));
    }

    private void addCommands(final ICommand... e) {
        for (final ICommand c : e) {
            commands.add(c);
        }
    }

    public void parseCommands(final ServerChatEvent ev) {
        if (ev.message.startsWith(CheatingEssentials.getInstance().getCommandPrefix())) {
            ev.setCanceled(true);
        } else {
            return;
        }

        if (ev.message.equals(":")) {
            addChatMessage(String.format("%sYou must type a command!", CheatingEssentials.getInstance()
                    .getChatColor('c')));
            return;
        }

        final String[] args = ev.message.substring(1).split(" ");
        parseCommand(args);
    }

    private void parseCommand(final String[] args) {
        for (final ICommand e : commands) {
            if (e.getName().toLowerCase().replaceAll(" ", "").equals(args[0].toLowerCase())) {
                try {
                    e.onCommand(args);
                } catch (final Exception x) {
                    addChatMessage(String.format("%sSyntax: %s%s", CheatingEssentials.getInstance()
                            .getChatColor('6'), CheatingEssentials.getInstance().getChatColor('7'), e
                            .getSyntax()));
                    x.printStackTrace();
                }
                return;
            }
        }
    }

    private void addChatMessage(final String... message) {
        for (final String e : message) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(String.format(
                    "[CE] %s", e)));
        }
    }

    public List<String> dumpCommands() {
        final List<String> commands = new ArrayList<String>();
        for (final ICommand c : this.commands) {
            String cmd = c.toString();
            // Will change this behavior later
            if (c instanceof Module) {
                final Module e = (Module) c;
                cmd = String.format("%s: %s", e.getName().toLowerCase().replaceAll(" ", ""),
                        findUsage(e));
            }
            if (c instanceof ACommand) {
                cmd = ((ACommand) c).getSyntax();
            }
            commands.add(cmd);
        }
        commands.add("prefix: prefix [new]");
        return commands;
    }

    private String findUsage(final Module e) {
        for (final String s : e.getHelp()) {
            if (s.startsWith("Usage")) {
                return s;
            }
        }
        return e.toString();
    }

    public List<ICommand> getCommands() {
        return commands;
    }
}
