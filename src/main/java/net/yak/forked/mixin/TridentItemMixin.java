package net.yak.forked.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.yak.forked.Forked;
import net.yak.forked.injection.ProjectileHotbarSlotHolder;
import net.yak.forked.injection.ServerPlayerTridentTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TridentItem.class)
public abstract class TridentItemMixin<T extends Projectile> {

    @WrapOperation(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/Projectile;spawnProjectileFromRotation(Lnet/minecraft/world/entity/projectile/Projectile$ProjectileFactory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;FFF)Lnet/minecraft/world/entity/projectile/Projectile;"))
    private T forked$thrownTridentData(Projectile.ProjectileFactory<T> creator, ServerLevel serverLevel, ItemStack itemStack, LivingEntity source, float yOffset, float pow, float uncertainty, Operation<T> original) {
        T projectile = original.call(creator, serverLevel, itemStack, source, yOffset, pow, uncertainty);
        ThrownTrident trident = (ThrownTrident) projectile;
        int loyalty = Mth.clamp(EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverLevel, itemStack, trident), 0, 127);
        if (trident instanceof ProjectileHotbarSlotHolder slotHolder && source instanceof Player player) {
            slotHolder.forked$setHotbarSlot(player.getUsedItemHand() == InteractionHand.MAIN_HAND ? player.getInventory().getSelectedSlot() : 40);
        }
        if (source instanceof ServerPlayer && source instanceof ServerPlayerTridentTracker serverPlayer) {
            if (loyalty > 1) {
                serverPlayer.forked$addTrident(trident);
            }
        }
        return projectile;
    }

    @WrapOperation(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWaterOrRain()Z"))
    private boolean forked$wetRiptide(Player instance, Operation<Boolean> original) {
        return original.call(instance) || instance.getAttachedOrElse(Forked.WET_ATTACHMENT, 0) > 0;
    }

    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWaterOrRain()Z"))
    private boolean forked$wetRiptideUse(Player instance, Operation<Boolean> original) {
        return original.call(instance) || instance.getAttachedOrElse(Forked.WET_ATTACHMENT, 0) > 0;
    }

}
