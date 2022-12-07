package com.tomalbrc.stm.helper;

import com.tomalbrc.stm.pattern.ChatBlockPattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;

import java.util.ArrayList;
import java.util.List;

public class ChatController {

    private List<ChatEntry> chatEntries = new ArrayList<>();

    private ChatBlockPattern currentPattern;

    private static final ChatController INSTANCE = new ChatController();

    static public void setPattern(ChatBlockPattern pattern) {
        ChatController.INSTANCE.currentPattern = pattern;
    }
    static public ChatBlockPattern getPattern() {
        return ChatController.INSTANCE.currentPattern;
    }

    static public void addMessage(ChatEntry entry) {
        if (ChatController.INSTANCE.chatEntries.contains(entry)) {
            return;
        }

        ChatController.INSTANCE.chatEntries.add(entry);
        if (ChatController.INSTANCE.chatEntries.size() > 1000) {
            ChatController.INSTANCE.chatEntries.remove(0);
        }
    }

    static public void reloadChat() {

        List<ChatEntry> list = new ArrayList<>();

        if (ChatController.INSTANCE.currentPattern == null) { // All entries!
            list = ChatController.INSTANCE.chatEntries;
        }
        else {
            for (ChatEntry item: ChatController.INSTANCE.chatEntries) {
                if (ChatController.getPattern().matches(item.message.getString())) {
                    list.add(item);
                }
            }
        }

        // remove chat messages that match auto exclusive patterns
        for (ChatBlockPattern pattern: Data.getBlockPatternList()) {
            if (pattern.isAutoExclusive() && pattern != ChatController.getPattern())  {
                list.removeIf(x -> pattern.matches(x.message.getString()));
            }
        }

        ChatHud chatHud = MinecraftClient.getInstance().inGameHud.getChatHud();

        chatHud.clear(true);
        for (ChatEntry chatItem: list) {
            chatHud.addMessage(chatItem.message, chatItem.signature, chatItem.indicator);
        }
    }

    public static ChatController getInstance() {
        return INSTANCE;
    }
}
