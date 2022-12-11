package com.tomalbrc.chattytabs;

import com.tomalbrc.chattytabs.event.WorldLoadListener;
import com.tomalbrc.chattytabs.helper.Data;
import fi.dy.masa.malilib.event.WorldLoadHandler;
import net.fabricmc.api.ModInitializer;

public class ChattyTabs implements ModInitializer {
    @Override
    public void onInitialize() {
        WorldLoadListener listener = new WorldLoadListener();
        WorldLoadHandler.getInstance().registerWorldLoadPreHandler(listener);
        WorldLoadHandler.getInstance().registerWorldLoadPostHandler(listener);
    }
}
