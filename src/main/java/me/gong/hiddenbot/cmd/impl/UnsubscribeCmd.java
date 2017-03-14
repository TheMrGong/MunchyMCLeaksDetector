package me.gong.hiddenbot.cmd.impl;

import me.gong.hiddenbot.cmd.ICmd;

public class UnsubscribeCmd extends ICmd {
    @Override
    public String getName() {
        return "unsubscribe";
    }

    @Override
    public void execute(String player, String[] args) {
        if(super.mod.getBroadcaster().unsubscribePlayer(player)) {
            super.messenger.sendPM(player, "You have been unsubscribed from global messages");
        } else super.messenger.sendPM(player, "You are already unsubscribed from global messages");
    }
}
