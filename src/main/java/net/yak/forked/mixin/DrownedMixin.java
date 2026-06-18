package net.yak.forked.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Drowned.class)
public abstract class DrownedMixin extends Zombie {

    public DrownedMixin(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
    }

    @WrapOperation(method = "populateDefaultEquipmentSlots", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/zombie/Drowned;setItemSlot(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)V", ordinal = 0))
    private void fork$tridentDropRateModifier(Drowned instance, EquipmentSlot equipmentSlot, ItemStack stack, Operation<Void> original) {
        original.call(instance, equipmentSlot, stack);
        this.setDropChance(equipmentSlot, 0.4f);
    }

}
