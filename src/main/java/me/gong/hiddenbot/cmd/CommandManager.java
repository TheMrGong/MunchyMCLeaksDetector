package me.gong.hiddenbot.cmd;

import me.gong.hiddenbot.cmd.impl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CommandManager {

    public static final char PREFIX = '.';
    private List<ICmd> cmds = new ArrayList<>();

    public CommandManager() {
        registerCmds(new HelpCmd(), new HiddenPlayerCmd(), new MCLeaksListCmd(), new SubscribeCmd(), new UnsubscribeCmd(), new BanInfoCmd(), new DisableBotCmd());
    }

    private void registerCmds(ICmd... cmd) {
        Stream.of(cmd).forEach(this::registerCmd);
    }

    private void registerCmd(ICmd cmd) {
        this.cmds.add(cmd);
    }

    public boolean processInput(String player, String input) {
        for (ICmd cmd : cmds) {
            int cmdIndex = input.toLowerCase().indexOf(PREFIX + cmd.getName());
            if(cmdIndex != -1) {
                String[] args = new String[0];
                String rest = input.substring(cmdIndex + cmd.getName().length() + 1).trim(); //+1 because prefix
                if(!rest.isEmpty()) {
                    if(rest.contains(" ")) args = rest.split(" ");
                    else args = new String[] {rest};
                }
                cmd.execute(player, args);
                return true;
            }
        }
        return false;
    }

    public List<ICmd> getCommands() {
        return cmds;
    }
}
