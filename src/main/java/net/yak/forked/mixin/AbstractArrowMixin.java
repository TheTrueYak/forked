package net.yak.forked.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.yak.forked.injection.ProjectileHotbarSlotHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin implements ProjectileHotbarSlotHolder {

    @Unique private int forked$hotbarSlot = -1;

    @WrapOperation(method = "tryPickup", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;add(Lnet/minecraft/world/item/ItemStack;)Z"))
    private boolean forked$slotInsertion(Inventory instance, ItemStack stack, Operation<Boolean> original) {
        int ownedSlot = this.forked$hotbarSlot;
        if (ownedSlot == 40) { // offhand handling
            if (instance.player.getOffhandItem().isEmpty()) {
                instance.setItem(40, stack);
                return true;
            }
            ownedSlot = -1; // no sound, no memory
        }
        ownedSlot = Math.clamp(ownedSlot, -1, instance.INVENTORY_SIZE - 1); // clamps between -1 and max size of main (which is where items are inserted) to (ideally) prevent crashes
        if (ownedSlot != -1) {
            if (instance.add(ownedSlot, stack)) {
                return true;
            }
        }
        return original.call(instance, stack);
    }

    @Override
    public int forked$getHotbarSlot() {
        return this.forked$hotbarSlot;
    }

    @Override
    public void forked$setHotbarSlot(int slot) {
        this.forked$hotbarSlot = slot;
    }
}
