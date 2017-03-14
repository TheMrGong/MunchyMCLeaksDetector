package me.gong.hiddenbot.api.ingame.bans.impl;

import me.gong.hiddenbot.api.ingame.bans.ITrackedBan;
import me.gong.hiddenbot.util.PacketBuf;

class TrackedBan implements ITrackedBan {

    private final String name;
    private long lastBanDate;
    private int lastBanAmount;
    private String banType;

    TrackedBan(String name, String banType, int lastBanAmount, long lastBanData) {
        this.name = name;
        this.banType = banType;
        this.lastBanAmount = lastBanAmount;
        this.lastBanDate = lastBanData;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getBanType() {
        return banType;
    }

    @Override
    public int getBanTime() {
        return lastBanAmount;
    }

    @Override
    public long getBanDate() {
        return lastBanDate;
    }

    void setLastBanAmount(int lastBanAmount, String banType, long lastBanDate) {
        this.lastBanAmount = lastBanAmount;
        this.banType = banType;
        this.lastBanDate = lastBanDate;
    }

    void writeTo(PacketBuf buf) {
        buf.writeString(name);
        buf.writeString(banType);
        buf.writeVarIntToBuffer(lastBanAmount);
        buf.writeLong(lastBanDate);
    }

    static TrackedBan loadFrom(PacketBuf buf) {
        return new TrackedBan(buf.readString(), buf.readString(), buf.readVarIntFromBuffer(), buf.readLong());
    }
}
