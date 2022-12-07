package com.tomalbrc.chattytabs.pattern.list;

import com.tomalbrc.chattytabs.gui.GuiPatternList;
import com.tomalbrc.chattytabs.helper.Data;
import com.tomalbrc.chattytabs.pattern.ChatBlockPattern;
import fi.dy.masa.malilib.gui.widgets.WidgetListBase;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WidgetListChatBlockPattern extends WidgetListBase<ChatBlockPattern, WidgetChatBlockPattern>
{
    private final GuiPatternList gui;

    public WidgetListChatBlockPattern(int x, int y, int width, int height, GuiPatternList parent) {
        super(x, y, width, height, null);

        this.gui = parent;

        this.browserEntryHeight = 22;

        this.browserEntriesOffsetY = 40 + 3;
    }

    public void removePattern(ChatBlockPattern p) {
        Data.remove(p);
        Data.save();

        refreshEntries();
    }

    protected List<ChatBlockPattern> getAllEntries() {
        return Data.getBlockPatternList();
    }

    @Override
    protected WidgetChatBlockPattern createListEntryWidget(int x, int y, int listIndex, boolean isOdd, ChatBlockPattern entry) {
        return new WidgetChatBlockPattern(x, y, this.browserEntryWidth, this.getBrowserEntryHeightFor(entry), isOdd, Data.getBlockPatternList(), entry, listIndex, this);
    }
}
