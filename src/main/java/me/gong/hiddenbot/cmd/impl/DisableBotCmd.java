package me.gong.hiddenbot.cmd.impl;

import me.gong.hiddenbot.cmd.ICmd;
import me.gong.hiddenbot.util.BoolParser;
import me.gong.hiddenbot.util.SessionUtils;

public class DisableBotCmd extends ICmd {

    private static final String BOT_OWNER = "15lbans";


    @Override
    public String getName() {
        return "setenabled";
    }

    @Override
    public void execute(String player, String[] args) {
        if (args.length < 1) {
            super.messenger.sendPM(player, "Must supply an argument");
            return;
        }
        Boolean type = BoolParser.parseString(args[0]);
        if (type == null) {
            super.messenger.sendPM(player, "Invalid argument, must be true or false");
            return;
        }

        if (!player.equalsIgnoreCase(BOT_OWNER) && !player.equalsIgnoreCase(SessionUtils.getSession().getProfile().getName()))
            super.messenger.sendPM(player, "OI! You're not the bot owner!");
        else {
            super.mod.setEnabled(type);
            super.messenger.sendPM(player, "The bot has been " + (type ? "enabled" : "disabled") + ".");
        }
    }
}
