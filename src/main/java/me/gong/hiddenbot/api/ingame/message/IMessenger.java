package me.gong.hiddenbot.api.ingame.message;

public interface IMessenger {

    /**
     * Broadcasts a message using PM's, using prefix
     *
     * @param msg The message to broadcast
     */
    void broadcastMessage(String msg);

    /**
     * Broadcasts a message using PM's, prefix optional
     *
     * @param msg The message to broadcast
     * @param prefix Whether to include the prefix
     */
    void broadcastMessage(String msg, boolean prefix);

    /**
     * Sends a private message to the player, using prefix
     *
     * @param player The player to send the private message to
     * @param msg The message to send
     */
    void sendPM(String player, String msg);

    /**
     * Sends a private message to the player, prefix optional
     *
     * @param player The player to send the private message to
     * @param msg The message to send
     * @param prefix Whether to include the prefix
     */
    void sendPM(String player, String msg, boolean prefix);
}
