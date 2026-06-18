package net.yak.forked.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Projectile.class)
public abstract class ProjectileMixin {

    @WrapMethod(method = "hurtServer")
    private boolean forked$preventVoidingTridents(ServerLevel level, DamageSource source, float damage, Operation<Boolean> original) {
        if (source == level.damageSources().fellOutOfWorld() && ((Object) this) instanceof ThrownTrident trident) {
            trident.dealtDamage = true;
            return false;
        }
        return original.call(level, source, damage);
    }

}
