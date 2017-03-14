package me.gong.hiddenbot.cmd.impl;

import me.gong.hiddenbot.cmd.ICmd;

public class SubscribeCmd extends ICmd {
    @Override
    public String getName() {
        return "subscribe";
    }

    @Override
    public void execute(String player, String[] args) {
        if(super.mod.getBroadcaster().subscribePlayer(player)) {
            super.messenger.sendPM(player, "You have been subscribed to global messages");
        } else super.messenger.sendPM(player, "You are already subscribed to global messages");
    }
}
