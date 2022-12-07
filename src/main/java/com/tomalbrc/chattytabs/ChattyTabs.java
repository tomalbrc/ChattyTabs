package com.tomalbrc.chattytabs;

import net.fabricmc.api.ModInitializer;
import com.tomalbrc.stm.helper.Data;

public class ChattyTabs implements ModInitializer {
    @Override
    public void onInitialize() {
        Data.load();
    }
}
