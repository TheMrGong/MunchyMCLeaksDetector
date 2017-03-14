package me.gong.hiddenbot.request;

import net.minecraftforge.client.event.ClientChatReceivedEvent;

import java.util.function.BiConsumer;

//removed as to not leak exploits
public class HiddenRequest {


    private final BiConsumer<Integer, Integer> callback;

    HiddenRequest(BiConsumer<Integer, Integer> callback) {
        this.callback = callback;
    }

    void beginRequest() {

    }

    void handleChat(ClientChatReceivedEvent ev) {

    }
}
