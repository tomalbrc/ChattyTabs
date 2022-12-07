package com.tomalbrc.chattytabs.gui;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;

public class GuiMainMenu extends GuiBase {
    public GuiMainMenu()
    {
        this.title = "Config";
    }

    @Override
    public void initGui() {
        super.initGui();

        int x = 12;
        int y = 30;
        int width = 250;

        String label = "Manage Tabs";
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, label);
        this.addButton(button, new IButtonActionListener() {
            @Override
            public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
                GuiBase.openGui(new GuiPatternList());
            }
        });
        y += 22;

        label = "Configuration";
        ButtonGeneric button2 = new ButtonGeneric(x, y, width, 20, label);
        this.addButton(button2, new IButtonActionListener() {
            @Override
            public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
                GuiBase.openGui(new GuiConfig());
            }
        });
        y += 22;
    }
}
