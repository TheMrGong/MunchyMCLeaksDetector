package me.gong.hiddenbot.api.ingame.global.impl;

import io.netty.buffer.Unpooled;
import me.gong.hiddenbot.HiddenOnlineBotMod;
import me.gong.hiddenbot.util.ISavable;
import me.gong.hiddenbot.util.PacketBuf;
import me.gong.hiddenbot.api.ingame.global.IBroadcaster;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class GlobalBroadcaster implements IBroadcaster, ISavable {

    private static final int MESSAGES_PER = 6;
    private static final long MESSAGE_DELAY = TimeUnit.MILLISECONDS.convert(2, TimeUnit.SECONDS);

    private static final String MSG_CMD = "/msg %s %s";
    private static final int DATA_VERSION = 1;

    private final Set<String> unsubscribed = new HashSet<>();
    private final Queue<QueuedBroadcast> broadcasts = new LinkedList<>();

    private final File saveFile;
    private boolean isDirty;
    private int amountMessages = MESSAGES_PER;
    private long lastSetOfMessages;

    public GlobalBroadcaster(File file) {
        this.saveFile = file;
        try {
            load();
        } catch (IOException e) {
            throw new RuntimeException("Unable to load data file", e);
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
    public boolean subscribePlayer(String player) {
        if (unsubscribed.remove(player.toLowerCase())) {
            isDirty = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unsubscribePlayer(String player) {
        if (unsubscribed.add(player.toLowerCase())) {
            isDirty = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean isSubscribed(String player) {
        return !unsubscribed.contains(player.toLowerCase());
    }

    @Override
    public void broadcastMessage(String message) {
        QueueBroadcastBuilder builder = QueueBroadcastBuilder.begin(message);
        Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap().stream().map(i -> i.getGameProfile().getName().toLowerCase())
                .filter(this::isSubscribed).forEach(builder::addPlayer);

        this.broadcasts.add(builder.build(this));
        this.broadcasts.add(QueueBroadcastBuilder.of(this, builder, "[HiddenBot] You can reply .unsubscribe to no longer receive these messages"));
    }

    @Override
    public void runTick() {

        if(!HiddenOnlineBotMod.INSTANCE.isEnabled())
            broadcasts.clear();

        if (this.broadcasts.isEmpty()) return;
        if (this.amountMessages == 0) {
            if (System.currentTimeMillis() - lastSetOfMessages > MESSAGE_DELAY)
                amountMessages = MESSAGES_PER;
            else return;
        }

        QueuedBroadcast broadcast = this.broadcasts.peek();
        if (broadcast.hasQueued()) {
            broadcast.runQueue();
            if (--amountMessages == 0) lastSetOfMessages = System.currentTimeMillis();
        } else this.broadcasts.remove(broadcast);
    }

    @Override
    public void setDirty(boolean dirty) {
        this.isDirty = dirty;
    }

    @Override
    public boolean isDirty() {
        return isDirty;
    }

    void messagePlayer(String player, String message) {
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C01PacketChatMessage(String.format(MSG_CMD, player, message)));
    }

    private void loadDataFrom(PacketBuf buf) {
        int version = buf.readVarIntFromBuffer(), amount = buf.readVarIntFromBuffer();
        //version for backwards compatibility
        unsubscribed.clear();
        for (int i = 0; i < amount; i++)
            unsubscribed.add(buf.readString());

    }

    private byte[] saveData() {
        PacketBuf buf = new PacketBuf(Unpooled.buffer());

        buf.writeVarIntToBuffer(DATA_VERSION);
        buf.writeVarIntToBuffer(unsubscribed.size());
        unsubscribed.forEach(buf::writeString);

        return buf.totalReadableBytes();
    }
}
