package me.gong.hiddenbot.api.ingame.bans.impl;

import io.netty.buffer.Unpooled;
import me.gong.hiddenbot.api.ingame.bans.IBanTracker;
import me.gong.hiddenbot.api.ingame.bans.ITrackedBan;
import me.gong.hiddenbot.util.ISavable;
import me.gong.hiddenbot.util.PacketBuf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BanTracker implements ISavable, IBanTracker {

    private static final int DATA_VERSION = 1;
    private static final Pattern BAN_PATTERN = Pattern.compile("^([a-zA-Z0-9_-]{3,16}) has been auto-banned for cheating\\.");

    private final List<TrackedBan> trackedBans = new ArrayList<>();
    private final File saveFile;
    private boolean isDirty;
    private BanReader curBanReader;

    public BanTracker(File saveFile) {
        this.saveFile = saveFile;
        try {
            load();
        } catch (IOException e) {
            throw new RuntimeException("Unable to load tracked bans", e);
        }
    }

    @Override
    public void load() throws IOException {
        isDirty = false;
        if (!saveFile.exists())
            saveFile.createNewFile();
        else {
            try (FileInputStream in = new FileInputStream(saveFile)) {
                byte[] dat = new byte[in.available()];
                if (in.read(dat) != -1 && dat.length > 0) {
                    PacketBuf buf = new PacketBuf(Unpooled.wrappedBuffer(dat));
                    loadDataFrom(buf);
                }
            }
        }
    }

    @Override
    public void save() throws IOException {
        isDirty = false;
        try (FileOutputStream out = new FileOutputStream(saveFile)) {
            out.write(saveData());
        }
    }

    @Override
    public void setDirty(boolean dirty) {
        this.isDirty = dirty;
    }

    @Override
    public boolean isDirty() {
        return isDirty;
    }

    @Override
    public void handleChat(String chat) {
        Matcher m = BAN_PATTERN.matcher(chat);
        if(m.find()) {
            this.curBanReader = new BanReader(m.group(1));
        } else if(this.curBanReader != null && this.curBanReader.readFrom(chat)) {
            addData(this.curBanReader);
            this.curBanReader = null;
        }
    }

    @Override
    public ITrackedBan getBanData(String player) {
        return this.trackedBans.stream().filter(t -> t.getName().equalsIgnoreCase(player)).map(m -> (ITrackedBan) m).findFirst().orElse(null);
    }

    private void addData(BanReader reader) {
        TrackedBan trackedBan = (TrackedBan) getBanData(reader.getBannedPlayer());
        if(trackedBan == null)
            addTrackedBan(new TrackedBan(reader.getBannedPlayer(), reader.getBanType(), reader.getBanAmount(), System.currentTimeMillis()));
        else {
            trackedBan.setLastBanAmount(reader.getBanAmount(), reader.getBanType(), System.currentTimeMillis());
            this.isDirty = true;
        }
    }

    private void addTrackedBan(TrackedBan ban) {
        this.trackedBans.add(ban);
        this.isDirty = true;
    }

    private void loadDataFrom(PacketBuf buf) {

        int verison = buf.readVarIntFromBuffer(), amount = buf.readVarIntFromBuffer();
        //backwards compatibility (future)

        for (int i = 0; i < amount; i++)
            this.trackedBans.add(TrackedBan.loadFrom(buf));

    }

    private byte[] saveData() {

        PacketBuf buf = new PacketBuf(Unpooled.buffer());

        buf.writeVarIntToBuffer(DATA_VERSION);
        buf.writeVarIntToBuffer(this.trackedBans.size());

        this.trackedBans.forEach(t -> t.writeTo(buf));

        return buf.totalReadableBytes();
    }
}
