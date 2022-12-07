package com.tomalbrc.stm.helper;

import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;

public class ChatEntry {
    public Text message;
    public MessageSignatureData signature;
    public MessageIndicator indicator;

    public ChatEntry(Text message, MessageSignatureData signature, MessageIndicator indicator) {
        this.message = message;
        this.signature = signature;
        this.indicator = indicator;
    }

    @Override
    public boolean equals(Object o){
        return o instanceof ChatEntry && ((ChatEntry)o).message == this.message;
    }
}
