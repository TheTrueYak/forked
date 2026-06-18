package net.yak.forked.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
import net.minecraft.world.phys.AABB;
import net.yak.forked.Forked;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractThrownPotion.class)
public abstract class AbstractThrownPotionMixin {

    @WrapOperation(method = "onHitAsWater", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;inflate(DDD)Lnet/minecraft/world/phys/AABB;"))
    private AABB forked$splashPotionWetness(AABB instance, double xAdd, double yAdd, double zAdd, Operation<AABB> original, final ServerLevel level) {
        AABB box = original.call(instance, xAdd, yAdd, zAdd);
        for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box)) {
            living.setAttached(Forked.WET_ATTACHMENT, 60);
        }
        return box;
    }

}
