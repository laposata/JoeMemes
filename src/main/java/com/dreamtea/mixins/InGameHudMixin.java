package com.dreamtea.mixins;

import com.dreamtea.SpamClicker;
import com.dreamtea.imixin.ISpamClick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

  @Shadow @Final private MinecraftClient client;

  @Shadow protected abstract void drawTextBackground(MatrixStack matrices, TextRenderer textRenderer, int yOffset, int width, int color);

  @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTextBackground(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;III)V", ordinal = 0))
  public void adjustFont(InGameHud instance, MatrixStack matrices, TextRenderer textRenderer, int yOffset, int width, int color){
    if(this.client.player instanceof ISpamClick isc){
      SpamClicker spam = isc.getClicker();
      int offsetScale = spam.offsetScale();
      spam.matricesTranslate(matrices);
      drawTextBackground(matrices, textRenderer, yOffset + offsetScale, width, color);
    } else {
      drawTextBackground(matrices, textRenderer, yOffset, width, color);
    }
  }

  @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I"))
  public int recolor(TextRenderer instance, MatrixStack matrices, Text text, float x, float y, int color){
    if(this.client.player instanceof ISpamClick isc){
      return instance.drawWithShadow(matrices, text, x,y, isc.getClicker().getColor());
    }
    return instance.drawWithShadow(matrices, text, x, y, color);
  }
}
