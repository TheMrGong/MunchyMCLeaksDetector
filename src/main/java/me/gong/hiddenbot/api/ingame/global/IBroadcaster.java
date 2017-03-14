package me.gong.hiddenbot.api.ingame.global;

/**
 * An object able to keep track of subscriptions and broadcast to those subscribed
 */
public interface IBroadcaster {

    /**
     * Removes the player from the unsubscribed list
     *
     * @param player The player to subscribe
     * @return Whether the player was already subscribed
     */
    boolean subscribePlayer(String player);

    /**
     * Adds a player to the unsubscribed list
     *
     * @param player The player to unsubscribe
     * @return Whether the player was already unsubscribed
     */
    boolean unsubscribePlayer(String player);

    /**
     * Checks whether the player is already subscribed
     *
     * @param player The player to check
     * @return True if already subscribed
     */
    boolean isSubscribed(String player);

    /**
     * Queues a message to be broadcast
     *
     * @param message The message to broadcast
     */
    void broadcastMessage(String message);

    /**
     * Causes messages in the queue to be sent if a certain delay has passed
     */
    void runTick();
}
