package me.gong.hiddenbot.api.ingame.global.impl;

import java.util.LinkedList;
import java.util.Queue;

class QueueBroadcastBuilder {

    private String message;
    private Queue<String> players = new LinkedList<>();

    private QueueBroadcastBuilder(String message) {
        this.message = message;
    }

    QueueBroadcastBuilder addPlayer(String player) {
        this.players.add(player);
        return this;
    }

    QueuedBroadcast build(GlobalBroadcaster broadcaster) {
        return new QueuedBroadcast(message, players, broadcaster);
    }

    static QueueBroadcastBuilder begin(String message) {
        return new QueueBroadcastBuilder(message);
    }

    static QueuedBroadcast of(GlobalBroadcaster broadcaster, QueueBroadcastBuilder builder, String msg) {
        QueueBroadcastBuilder b = new QueueBroadcastBuilder(msg);
        b.players = new LinkedList<>(builder.players);
        return b.build(broadcaster);
    }
}
