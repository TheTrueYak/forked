package net.yak.forked.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownTrident.class)
public abstract class ThrownTridentMixin extends AbstractArrow {

    @Shadow
    public boolean dealtDamage;

    protected ThrownTridentMixin(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
    }

    @WrapOperation(method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/arrow/ThrownTrident;getLoyaltyFromItem(Lnet/minecraft/world/item/ItemStack;)B"))
    private byte forked$innateLoyalty(ThrownTrident instance, ItemStack stack, Operation<Byte> original, final Level level, final LivingEntity owner, final ItemStack tridentItem) {
        byte value = original.call(instance, stack);
        if (owner != null && owner.isAlwaysTicking()) {
            return (byte) Mth.clamp(value + 1, 0, 127);
        }
        return value;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void forked$preventVoidingTridents(CallbackInfo ci) {
        if (this.getY() < -65) {
            dealtDamage = true;
        }
    }
}
