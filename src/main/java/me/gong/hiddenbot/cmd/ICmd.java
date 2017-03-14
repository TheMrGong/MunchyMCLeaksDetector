package me.gong.hiddenbot.cmd;

import me.gong.hiddenbot.HiddenOnlineBotMod;
import me.gong.hiddenbot.api.ingame.message.IMessenger;

public abstract class ICmd {

    protected HiddenOnlineBotMod mod = HiddenOnlineBotMod.INSTANCE;
    protected IMessenger messenger = mod.getMessenger();

    public abstract String getName();

    public abstract void execute(String player, String[] args);

}
