package com.dreamtea;

import com.dreamtea.imixin.IModifyFont;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

public class SpamClicker {
  private int clicks = 0;
  private int time;

  private final InGameHud hud = MinecraftClient.getInstance().inGameHud;

  public int click(ClientPlayerEntity player){
    if(player.age - 10 > time){
      clicks = 0;
    }
    clicks++;
    time = player.age;
    PassiveAggressiveBeds.LOGGER.info(String.valueOf(clicks));
    return clicks;
  }

  public int clicks(){
    return clicks;
  }

  public float sizeScale(){
    return MathHelper.clamp(1 + (clicks / 25f), 1, 8f);
  }

  public float yScale(){
    return 1 + (clicks / 12f);
  }

  public int offsetScale(){
    return -500;
  }

  public int xTranslate(){
    return MathHelper.clamp(clicks / 2, 0, 28);
  }

  public void matricesTranslate(MatrixStack matrices){
    matrices.scale(sizeScale(), yScale(), 0);
    matrices.translate(xTranslate(), -MathHelper.clamp(clicks/25f, 0, 2), 0);
  }

  public int getColor(){
    int scale = MathHelper.clamp(clicks, 0, 255);
    return ColorHelper.Argb.getArgb(255,255 - MathHelper.clamp(clicks - 255, 0, 150), 255 - scale, 255 - scale);
  }
}
