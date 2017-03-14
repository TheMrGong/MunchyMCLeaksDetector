package me.gong.hiddenbot.api.ingame.message;

import me.gong.hiddenbot.HiddenOnlineBotMod;
import me.gong.hiddenbot.api.ingame.global.IBroadcaster;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class Messenger implements IMessenger {

    private static final String MSG_CMD = "/msg %s %s";

    private final IBroadcaster broadcaster;

    public Messenger(IBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @Override
    public void sendPM(String player, String message) {
        sendPM(player, message, false);
    }

    @Override
    public void sendPM(String player, String message, boolean prefix) {
        sayMessage(String.format(MSG_CMD, player, (prefix ? HiddenOnlineBotMod.PREFIX + " " : "") + message));
    }

    @Override
    public void broadcastMessage(String msg) {
        this.broadcastMessage(msg, true);
    }

    @Override
    public void broadcastMessage(String msg, boolean prefix) {
        this.broadcaster.broadcastMessage((prefix ? HiddenOnlineBotMod.PREFIX + " " : "") + msg);
    }

    private void sayMessage(String msg) {
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C01PacketChatMessage(msg));
    }
}
