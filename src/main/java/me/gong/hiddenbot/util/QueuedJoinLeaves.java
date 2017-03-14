package me.gong.hiddenbot.util;

import java.util.ArrayList;
import java.util.List;

public class QueuedJoinLeaves {

    private long queueTime = System.currentTimeMillis();
    private List<String> joined = new ArrayList<>(), left = new ArrayList<>();

    public long getTimeSinceQueue() {
        return System.currentTimeMillis() - queueTime;
    }

    public List<String> getJoined() {
        return joined;
    }

    public List<String> getLeft() {
        return left;
    }
}
