package net.yak.forked.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.yak.forked.world.loot.predicates.WetCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LootItemConditions.class)
public abstract class LootItemConditionsMixin {

    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void forked$registerWetCondition(Registry<MapCodec<? extends LootItemCondition>> registry, CallbackInfoReturnable<MapCodec<? extends LootItemCondition>> cir) {
        Registry.register(registry, "wet", WetCondition.MAP_CODEC);
    }
}
