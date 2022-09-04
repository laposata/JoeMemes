package com.dreamtea.mixins;

import com.dreamtea.imixin.IModifyFont;
import net.minecraft.client.font.TextRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(TextRenderer.class)
public class TextRendererMixin implements IModifyFont {

  @Shadow @Final @Mutable
  public int fontHeight;

  @Override
  public void setFont(int fontHeight) {
    this.fontHeight = fontHeight;
  }
}
