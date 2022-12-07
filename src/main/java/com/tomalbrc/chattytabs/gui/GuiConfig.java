package com.tomalbrc.chattytabs.gui;

import com.tomalbrc.chattytabs.config.Config;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GuiConfig extends GuiConfigsBase
{
    private ConfigGuiTab currentTab;

    public GuiConfig() {
        super(10, 50, "StopTheMadness", null, "Configuration");

        currentTab = ConfigGuiTab.GENERIC;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.clearOptions();

        int x = 10;
        int y = 26;

        x += this.createButton(x, y, -1, ConfigGuiTab.GENERIC, new IButtonActionListener() {
            @Override
            public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
                reCreateListWidget(); // apply the new config width
                getListWidget().resetScrollbarPosition();

                currentTab = ConfigGuiTab.GENERIC;
                initGui();
            }
        });
        x += this.createButton(x, y, -1, ConfigGuiTab.BLOCKLIST, new IButtonActionListener() {
            @Override
            public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
                reCreateListWidget(); // apply the new config width
                getListWidget().resetScrollbarPosition();

                currentTab = ConfigGuiTab.BLOCKLIST;
                initGui();
            }
        });
    }


    private int createButton(int x, int y, int width, ConfigGuiTab tab, IButtonActionListener listener)
    {
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.getDisplayName());
        button.setEnabled(currentTab != tab);
        this.addButton(button, listener);

        return button.getWidth() + 2;
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        List<ConfigOptionWrapper> list;

        switch (currentTab) {
            case GENERIC -> list = ConfigOptionWrapper.createFor(Config.Generic.OPTIONS);
            case BLOCKLIST -> list = new ArrayList();
            default -> list = new ArrayList();
        }

        return list;
    }


    public enum ConfigGuiTab
    {
        GENERIC         ("Generic"),
        BLOCKLIST   ("Blocklist");

        private final String translationKey;

        private ConfigGuiTab(String translationKey)
        {
            this.translationKey = translationKey;
        }

        public String getDisplayName()
        {
            return StringUtils.translate(this.translationKey);
        }
    }
}
