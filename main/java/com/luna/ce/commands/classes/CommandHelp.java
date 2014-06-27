package com.luna.ce.commands.classes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.StringUtils;

import com.luna.ce.CheatingEssentials;
import com.luna.ce.commands.ACommand;
import com.luna.ce.manager.ManagerCommand;
import com.luna.ce.manager.ManagerModule;
import com.luna.ce.module.Module;
import com.luna.lib.interfaces.util.ICommand;

public class CommandHelp extends ACommand {
    /**
     * Max commands/page. Each page is 10 lines, formatted as
     * <p/>
     * <pre>
     * --------HEADER--------
     * Command - Help
     * Command - Help
     * Command - Help
     * Command - Help
     * Command - Halp
     * Command - Help
     * Command - Help
     * Command - Help
     * --------FOOTER--------
     * </pre>
     */
    private final int HELP_MAX_SIZE = 8;

    public CommandHelp() {
        super("help");
    }

    @Override
    public void onCommand(final String[] args) {
        // Check if we have too many arguments. Subject to change. Maybe
        if (args.length >= 3) {
            throw new IllegalArgumentException();
        }

        try {
            // Default help page (1)
            if (args.length == 1) {
                for (final String e : createHelpPage(1)) {
                    addChatMessage(e);
                }
                return;
            }
            // Generate a help page
            for (final String e : createHelpPage(Integer.parseInt(args[1]))) {
                addChatMessage(e);
            }
            return;
        } catch (final Exception e) {
            try {
                // Well, $USER wasn't looking for a help page, so we try modules
                final Module m = ManagerModule.getInstance().getModuleByName(args[1]);
                final String[] halp = m.getHelp();
                // @formatter:off
                /*for( int i = 0; i < halp.length; i++ ) {
					halp[ i ] = String.format( "[%s%s%s] %s",
							CheatingEssentials.getInstance( ).getChatColor( 'a' ), m.getName( ),
							CheatingEssentials.getInstance( ).getChatColor( 'r' ), halp[ i ] );
				}*/
                // @formatter:on
                addChatMessage(getHeader(m.getName()));
                addChatMessage(halp);
                addChatMessage(getFooter(m.getName()));
                return;
            } catch (final Exception f) {
                try {
                    // Everything else's failed, so just blindly try EVERY
                    // command we've got
                    for (final ICommand c : ManagerCommand.getInstance().getCommands()) {
                        if (c.getName().toLowerCase().replaceAll(" ", "")
                                .equals(args[1].toLowerCase())) {
                            String cmd = c.toString();
                            // I'll find a better way of doing this later.
                            if (c instanceof Module) {
                                final Module q = (Module) c;
                                cmd = String.format("%s: %s", q.getName(), findUsage(q));
                            }
                            if (c instanceof ACommand) {
                                cmd = ((ACommand) c).getSyntax();
                            }
                            addChatMessage(getHeader(cmd.split(" ")[0]));
                            addChatMessage(cmd);
                            addChatMessage(getFooter(cmd.split(" ")[0]));
                            break;
                        }
                    }
                    return;
                } catch (final Exception g) {
                    // It's all over! We couldn't find what you're looking for!
                    // g.printStackTrace( );
                    addChatMessage(String.format("Invalid command: %s%s%s", CheatingEssentials
                                    .getInstance().getChatColor('c'), args.length > 1 ? args[1] : "NULL",
                            CheatingEssentials.getInstance().getChatColor('r')));
                }
            }
        }
    }

    private List<String> createHelpPage(int p) {
        final List<String> dump = ManagerCommand.getInstance().dumpCommands();
        final int pageCount = Math.round((dump.size() - 1) / HELP_MAX_SIZE);

        if (p > pageCount) {
            p = pageCount;
        }
        if (p <= 0) {
            p = 1;
        }

        final List<String> page = new ArrayList<String>();

        final String headerVal = String.format("%d/%d", p, pageCount);

        page.add(getHeader(headerVal));
        for (int i = p * HELP_MAX_SIZE; i < ((p * HELP_MAX_SIZE) + HELP_MAX_SIZE); i++) {
            if (i >= dump.size()) {
                break;
            }
            page.add(dump.get(i).replaceAll("Usage: ", ""));
        }
        page.add(getFooter(headerVal));

        return page;
    }

    private String getHeader(final Object p) {
        return String.format("%s--------%sHELP %s%s%s--------%s", CheatingEssentials.getInstance()
                        .getChatColor('6'), CheatingEssentials.getInstance().getChatColor('c'),
                CheatingEssentials.getInstance().getChatColor('a'), p, CheatingEssentials.getInstance()
                        .getChatColor('6'), CheatingEssentials.getInstance().getChatColor('r'));
    }

    private String getFooter(final Object p) {
        String footer = CheatingEssentials.getInstance().getChatColor('6');
        for (int i = 0; i < StringUtils.stripControlCodes(getHeader(p)).length(); i++) {
            footer += "-";
        }
        footer += CheatingEssentials.getInstance().getChatColor('r');
        return footer;
    }

    @Override
    public String getSyntax() {
        return "help [commmand/page]";
    }

    private String findUsage(final Module e) {
        for (final String s : e.getHelp()) {
            if (s.startsWith("")) {
                return s;
            }
        }
        return e.toString();
    }
}
