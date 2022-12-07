package com.tomalbrc.stm.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.InGameHud;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fi.dy.masa.malilib.gui.button.ButtonGeneric;

import java.util.List;

import static net.minecraft.client.gui.DrawableHelper.fill;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    List<ButtonWidget> tabs;

    @Shadow private int scaledHeight;

    @Shadow public abstract ChatHud getChatHud();

    @Shadow @Final private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V")
    private void render(MatrixStack matrices, float tick, CallbackInfo ci) {

        boolean isChatScreen = this.client.currentScreen instanceof ChatScreen;

        if (isChatScreen) {
            ChatHud ch = this.getChatHud();

            matrices.push();

            matrices.translate(0, 8, 0);

            matrices.push();

            matrices.translate(0, scaledHeight - ch.getHeight() - 48, 0);

            matrices.scale((float)ch.getChatScale(), (float)ch.getChatScale(), 1.0F);

            fill(matrices, 0, 0, ch.getWidth()+12, ch.getHeight(), 0x90151515);

            matrices.pop();

            matrices.pop();
        }
    }
}