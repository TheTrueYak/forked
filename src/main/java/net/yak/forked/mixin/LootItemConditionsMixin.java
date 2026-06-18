package net.yak.forked.mixin;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.yak.forked.Forked;
import net.yak.forked.world.loot.predicates.WetCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LootItemConditions.class)
public abstract class LootItemConditionsMixin {

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void forked$registerWetCondition(CallbackInfo ci) {
        Forked.WET = Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, Identifier.withDefaultNamespace("wet"), new LootItemConditionType(WetCondition.MAP_CODEC));
    }
}
