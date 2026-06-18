package net.yak.forked.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Inject(method = "postHurtEnemy", at = @At("TAIL"))
    private void forked$regainBreath(ItemStack itemStack, LivingEntity mob, LivingEntity attacker, CallbackInfo ci) {
        if (itemStack.getItem() instanceof TridentItem) {
            int airSupply = attacker.getAirSupply();
            int maxAirSupply = attacker.getMaxAirSupply();
            if (airSupply < maxAirSupply) {
                attacker.setAirSupply(Math.min(maxAirSupply, airSupply + maxAirSupply / 5));
            }
        }
    }

}
