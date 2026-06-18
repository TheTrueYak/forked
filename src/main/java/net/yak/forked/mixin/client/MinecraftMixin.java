package net.yak.forked.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.yak.forked.networking.ReturnTridentC2SPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Unique private int forked$returnCooldown = 0;

    @Inject(method = "tick", at = @At("TAIL"))
    private void forked$tickReturnCooldown(CallbackInfo ci) {
        if (forked$returnCooldown > 0) {
            forked$returnCooldown--;
        }
    }

    @WrapOperation(method = "startAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;resetAttackStrengthTicker()V"))
    private void forked$attemptTridentReturn(LocalPlayer instance, Operation<Void> original) {
        original.call(instance);
        if (forked$returnCooldown == 0 && instance.getMainHandItem().isEmpty()) {
            ClientPlayNetworking.send(new ReturnTridentC2SPayload());
            forked$returnCooldown = 20; // prevent payload spam
        }
    }

}
