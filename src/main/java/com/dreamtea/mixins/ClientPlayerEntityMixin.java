package com.dreamtea.mixins;

import com.dreamtea.SpamClicker;
import com.dreamtea.imixin.ISpamClick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.stat.StatHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements ISpamClick {

  SpamClicker clicker;

  @Override
  public int spamClicker() {
    if(clicker == null) return 0;
    return clicker.click((ClientPlayerEntity) (Object) this);
  }

  @Override
  public SpamClicker getClicker(){
    if(clicker == null) return new SpamClicker();
    return clicker;
  }

  @Inject(method = "<init>", at= @At("TAIL"))
  public void addSpamClicker(MinecraftClient client, ClientWorld world, ClientPlayNetworkHandler networkHandler, StatHandler stats, ClientRecipeBook recipeBook, boolean lastSneaking, boolean lastSprinting, CallbackInfo ci){
    clicker = new SpamClicker();
  }

}
