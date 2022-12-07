package com.tomalbrc.chattytabs.mixins;

import com.tomalbrc.chattytabs.gui.GuiMainMenu;
import com.tomalbrc.chattytabs.helper.ChatController;
import com.tomalbrc.chattytabs.helper.Data;
import com.tomalbrc.chattytabs.pattern.ChatBlockPattern;
import com.tomalbrc.chattytabs.util.ProxyButtonWidget;
import fi.dy.masa.malilib.gui.GuiBase;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ChatScreen.class)
public class ChatScreenMixin extends Screen implements ButtonWidget.PressAction {

    private List<ButtonWidget> tabButtons = new ArrayList<>();

    @Shadow protected TextFieldWidget chatField;
    private static MinecraftClient MC = MinecraftClient.getInstance();

    private int maxLines = 100;

    protected ChatScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "mouseClicked(DDI)Z", cancellable = true)
    private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {
        System.out.println("hello from chat screen, (not pressed) 444444");

        ChatHud ch = this.client.inGameHud.getChatHud();

        int y_pos = this.client.getWindow().getScaledHeight()-ch.getHeight()-40-20 -2;
        int x_pos = 4;

        x_pos = addButton(x_pos, y_pos, "all");

        for (ChatBlockPattern pattern: Data.getBlockPatternList()) {
            x_pos = addTabButton(x_pos, y_pos, pattern.getTitle());
        }

        x_pos = addButton(x_pos, y_pos, "menu");
    }

    private int addButton(int x, int y, String title) {
        int width = MinecraftClient.getInstance().textRenderer.getWidth(title) + 8;

        ButtonWidget btn = new ProxyButtonWidget(x, y, width, 20, Text.of(title), (ButtonWidget.PressAction)this, null);
        this.addDrawableChild(btn);
        return x+width+2;
    }

    private int addTabButton(int x, int y, String title) {
        int width = MinecraftClient.getInstance().textRenderer.getWidth(title) + 8;

        ButtonWidget btn = new ProxyButtonWidget(x, y, width, 20, Text.of(title), (ButtonWidget.PressAction)this, null);
        this.addDrawableChild(btn);
        tabButtons.add(btn);
        return x+width+2;
    }

    @Override
    public void onPress(ButtonWidget button) {
        if (tabButtons.contains(button)) {
            int idx = tabButtons.indexOf(button);
            if (idx >= Data.getBlockPatternList().size()) {
                GuiBase.openGui(new GuiMainMenu());
            }
            else if (idx < Data.getBlockPatternList().size()) {
                ChatBlockPattern pattern = Data.getBlockPatternList().get(idx);

                ChatController.setPattern(pattern);
                ChatController.reloadChat();
            }
        }
        else {
            switch (button.getMessage().getString()) {
                case "all": {
                    ChatController.setPattern(null);
                    ChatController.reloadChat();
                    break;
                }
                case "menu": {
                    GuiBase.openGui(new GuiMainMenu());
                    break;
                }
            }
        }
    }
}
