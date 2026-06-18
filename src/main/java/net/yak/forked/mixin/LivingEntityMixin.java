package net.yak.forked.mixin;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.yak.forked.Forked;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void forked$wetParticles(CallbackInfo ci) {
        int wetTicks = this.getAttachedOrElse(Forked.WET_ATTACHMENT, 0);
        if (this.isInWaterOrRain()) {
            if (wetTicks < 60) {
                this.setAttached(Forked.WET_ATTACHMENT, 60);
            }
        }
        else {
            if (wetTicks > 0 && this.isAlive()) {
                this.modifyAttached(Forked.WET_ATTACHMENT, ticks -> ticks - 1);
                if (this.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.FALLING_WATER, this.getX(), this.getY() + this.getBbHeight() / 2, this.getZ(), 1, 0.2, this.getBbHeight() / 4, 0.2, 1);
                }
            }
        }
    }
}
