package me.gong.hiddenbot.cmd.impl;

import me.gong.hiddenbot.cmd.CommandManager;
import me.gong.hiddenbot.cmd.ICmd;

public class HelpCmd extends ICmd {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void execute(String player, String[] args) {
        String[] cmds = super.mod.getCommandManager().getCommands().stream().map(i -> CommandManager.PREFIX + i.getName()).toArray(String[]::new);
        super.messenger.sendPM(player, "Available commands: ");
        super.messenger.sendPM(player, String.join(", ", cmds), false);
    }
}
