package me.gong.hiddenbot.cmd.impl;

import me.gong.hiddenbot.cmd.ICmd;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.stream.Collectors;

public class MCLeaksListCmd extends ICmd {

    @Override
    public String getName() {
        return "listleak";
    }

    @Override
    public void execute(String player, String[] args) {
        List<String> collect = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap().stream().map(i -> i.getGameProfile().getName())
                .filter(mod.getMcleaksCache()::containsAccount)
                .collect(Collectors.toList());
        if (collect.isEmpty()) super.messenger.sendPM(player, "No players found using MCLeaks");
        else {
            super.messenger.sendPM(player, "Found the following player(s) using MCLeaks: ");
            super.messenger.sendPM(player, String.join(", ", collect), false);
        }
    }
}
