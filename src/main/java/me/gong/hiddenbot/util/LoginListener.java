package me.gong.hiddenbot.util;

import me.gong.hiddenbot.HiddenOnlineBotMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoginListener {

    private Set<String> currentlyOnline = new HashSet<>();

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent ev) {
        Minecraft mc = Minecraft.getMinecraft();
        List<String> onlinePlayers = new ArrayList<>(); //keep track of online players for easy access
        //add players not already added
        mc.getNetHandler().getPlayerInfoMap().forEach(i ->
        {
            onlinePlayers.add(i.getGameProfile().getName());
            if(currentlyOnline.add(i.getGameProfile().getName())) { //adding new players, not restoring players after joining server
                HiddenOnlineBotMod.INSTANCE.onPlayerJoin(i.getGameProfile().getName());
            }
        });
        //cleanup players that have left
        currentlyOnline.removeIf(trackedPlayer -> {
            boolean removing = !onlinePlayers.contains(trackedPlayer);
            if(removing) HiddenOnlineBotMod.INSTANCE.onPlayerQuit(trackedPlayer);
            return removing;
        });
    }
}
