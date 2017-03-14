package me.gong.hiddenbot.request;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.function.BiConsumer;

public class HiddenStaffFetcher {

    private HiddenRequest r;

    public void requestHidden(BiConsumer<Integer, Integer> callback) {
        if (r == null) {
            r = new HiddenRequest((i, i2) -> {
                r = null;
                callback.accept(i, i2);
            });
            r.beginRequest();
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent ev) {
        if(r != null) r.handleChat(ev);
    }
}
