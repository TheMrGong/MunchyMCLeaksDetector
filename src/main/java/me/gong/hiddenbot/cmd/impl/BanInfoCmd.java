package me.gong.hiddenbot.cmd.impl;

import me.gong.hiddenbot.api.ingame.bans.ITrackedBan;
import me.gong.hiddenbot.cmd.ICmd;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BanInfoCmd extends ICmd {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM/dd/yyyy zzz");

    @Override
    public String getName() {
        return "baninfo";
    }

    @Override
    public void execute(String player, String[] args) {
        if (args.length < 1) {
            super.messenger.sendPM(player, "Not enough arguments. Need a player name");
        } else {
            final String name = args[0];
            final ITrackedBan banData = super.mod.getBanTracker().getBanData(name);
            if (banData == null) {
                super.messenger.sendPM(player, "No player data available for that player");
                System.out.println("No ban info found for: \"" + name + "\"");
            } else {
                super.messenger.sendPM(player, "Ban info for " + banData.getName() + ": ");
                super.messenger.sendPM(player, "Banned on " + FORMAT.format(new Date(banData.getBanDate())) + " for " + banData.getBanTime() + " " + banData.getBanType());
            }
        }
    }
}
