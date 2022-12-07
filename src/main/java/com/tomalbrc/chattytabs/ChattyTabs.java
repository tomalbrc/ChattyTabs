package com.tomalbrc.chattytabs;

import net.fabricmc.api.ModInitializer;
import com.tomalbrc.chattytabs.helper.Data;

public class ChattyTabs implements ModInitializer {
    @Override
    public void onInitialize() {
        Data.load();
    }
}
