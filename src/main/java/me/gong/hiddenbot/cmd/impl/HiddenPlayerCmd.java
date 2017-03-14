package me.gong.hiddenbot.cmd.impl;

import me.gong.hiddenbot.cmd.ICmd;

public class HiddenPlayerCmd extends ICmd {

    @Override
    public String getName() {
        return "amounthidden";
    }

    @Override
    public void execute(String player, String[] args) {
        if("".isEmpty()) {
            super.messenger.sendPM(player, "-- Removed by Callahan [he smells]--");
        } else {
            mod.getFetcher().requestHidden((shownAmount, hiddenAmount) -> {
                if (shownAmount != -1) {
                    if (hiddenAmount - shownAmount == 0)
                        messenger.sendPM(player, "There are no currently hidden players");
                    else messenger.sendPM(player, formattedOnline(shownAmount, hiddenAmount));
                } else messenger.sendPM(player, "There was an error fetching the amount of hidden players");
            });
        }

    }

    private String formattedOnline(int shown, int hidden) {
        StringBuilder b = new StringBuilder("There ");
        int amount = hidden - shown;

        boolean singular = amount == 1;
        b.append(singular ? "is" : "are");

        b.append(" ").append(amount).append(" hidden player");
        if (!singular) b.append("s");
        return b.toString();
    }
}
