package me.gong.hiddenbot.api.ingame.bans.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BanReader {

    private static final Pattern DURATION = Pattern.compile("^Duration: (\\d{1,3}) (hours?|days?|weeks?)");
    private String bannedPlayer, banType;
    private int banAmount;

    public BanReader(String bannedPlayer) {
        this.bannedPlayer = bannedPlayer;
    }

    public boolean readFrom(String text) {
        Matcher m = DURATION.matcher(text);
        if(m.find()) {
            banAmount = Integer.parseInt(m.group(1));
            banType = m.group(2);
            return true;
        }
        return false;
    }

    public String getBannedPlayer() {
        return bannedPlayer;
    }

    public String getBanType() {
        return banType;
    }

    public int getBanAmount() {
        return banAmount;
    }
}
