package me.gong.hiddenbot.api.ingame.bans;

public interface ITrackedBan {

    String getName();

    String getBanType();

    int getBanTime();

    long getBanDate();
}
