package me.gong.hiddenbot.api.ingame.bans;

public interface IBanTracker {

    void handleChat(String chat);

    ITrackedBan getBanData(String player);
}
