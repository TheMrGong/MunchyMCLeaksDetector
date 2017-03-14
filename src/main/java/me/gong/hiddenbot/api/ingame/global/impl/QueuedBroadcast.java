package me.gong.hiddenbot.api.ingame.global.impl;

import java.util.Queue;

class QueuedBroadcast {

    private final GlobalBroadcaster broadcaster;
    private final String message;
    private final Queue<String> players;

    QueuedBroadcast(String message, Queue<String> players, GlobalBroadcaster broadcaster) {
        this.message = message;
        this.players = players;
        this.broadcaster = broadcaster;
    }

    String getMessage() {
        return this.message;
    }

    boolean hasQueued() {
        return !this.players.isEmpty();
    }

    void runQueue() {
        if(this.hasQueued()) this.broadcaster.messagePlayer(this.players.poll(), this.message);
    }
}
