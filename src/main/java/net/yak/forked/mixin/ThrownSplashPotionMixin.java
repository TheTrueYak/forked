package net.yak.forked.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownSplashPotion;
import net.yak.forked.Forked;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ThrownSplashPotion.class)
public abstract class ThrownSplashPotionMixin {

    @WrapOperation(method = "onHitAsPotion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isAffectedByPotions()Z"))
    private boolean forked$splashPotionWetness(LivingEntity instance, Operation<Boolean> original) {
        boolean value = original.call(instance);
        if (value) {
            instance.setAttached(Forked.WET_ATTACHMENT, 60);
        }
        return value;
    }

}
