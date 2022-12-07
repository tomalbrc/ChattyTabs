package com.tomalbrc.chattytabs.interfaces;

import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.text.Text;

public interface IChatHud {
    abstract void logChatMessage(Text message, MessageIndicator indicator);

    abstract boolean isChatFocused();

    abstract void scroll(int scroll);

    abstract int getWidth();

    abstract double getChatScale();
}
