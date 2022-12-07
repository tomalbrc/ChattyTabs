package com.tomalbrc.chattytabs.gui;

import com.tomalbrc.chattytabs.helper.ChatController;
import com.tomalbrc.chattytabs.pattern.ChatBlockPattern;
import com.tomalbrc.chattytabs.pattern.list.WidgetListChatBlockPattern;
import com.tomalbrc.chattytabs.pattern.list.WidgetChatBlockPattern;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiListBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;

public class GuiPatternList extends GuiListBase<ChatBlockPattern, WidgetChatBlockPattern, WidgetListChatBlockPattern> implements IButtonActionListener {

    protected GuiPatternList() {
        super(10, 15);
        this.title = "Patterns y";

    }

    @Override
    public void initGui() {
        super.initGui();

        ButtonGeneric button = new ButtonGeneric(10, 24, 60, 20, "New");
        this.addButton(button, this);
    }

    @Override
    protected void closeGui(boolean showParent) {
        super.closeGui(showParent);

        ChatController.reloadChat();
    }


    @Override
    protected WidgetListChatBlockPattern createListWidget(int listX, int listY) {
        return new WidgetListChatBlockPattern(listX, listY, this.getBrowserWidth(), this.getBrowserHeight(), this);
    }

    @Override
    protected int getBrowserWidth() {
        return this.width - 20;
    }

    @Override
    protected int getBrowserHeight() {
        return this.height - 80;
    }

    @Override
    public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
        GuiBase.openGui(new GuiEditPattern());
    }
}
