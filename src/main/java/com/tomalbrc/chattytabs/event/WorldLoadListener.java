package com.tomalbrc.chattytabs.event;

import com.tomalbrc.chattytabs.helper.Data;
import fi.dy.masa.malilib.interfaces.IWorldLoadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;

public class WorldLoadListener implements IWorldLoadListener
{
    @Override
    public void onWorldLoadPre(ClientWorld worldBefore, ClientWorld worldAfter, MinecraftClient mc) {
        // Save the settings before the integrated server gets shut down
        if (worldBefore != null) {
            Data.save();
        }
    }

    @Override
    public void onWorldLoadPost(ClientWorld worldBefore, ClientWorld worldAfter, MinecraftClient mc) {
        Data.load();
    }
}
