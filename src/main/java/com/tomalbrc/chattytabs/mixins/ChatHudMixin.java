package com.tomalbrc.stm.mixins;

import com.tomalbrc.stm.helper.ChatController;
import com.tomalbrc.stm.helper.ChatEntry;
import com.tomalbrc.stm.interfaces.IChatHud;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;

import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.util.ChatMessages;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.RegEx;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Environment(EnvType.CLIENT)
@Mixin(ChatHud.class)
public abstract class ChatHudMixin implements IChatHud {
    @Shadow int scrolledLines;

    @Shadow
    boolean hasUnreadNewMessages;

    @Shadow
    List<ChatHudLine.Visible> visibleMessages;

    @Shadow
    List<ChatHudLine> messages;

    @Shadow MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", cancellable = true)
    private void addMessage(Text message, @Nullable MessageSignatureData signature, int ticks, @Nullable MessageIndicator indicator, boolean refresh, CallbackInfo ci) {
        ChatController.addMessage(new ChatEntry(message, signature, indicator));

        if (ChatController.getPattern() != null && !ChatController.getPattern().matches(message.getString())) {
            ci.cancel();
        }
    }
}
