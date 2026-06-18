package net.yak.forked.world.loot.predicates;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.yak.forked.Forked;
import org.jspecify.annotations.NonNull;

public class WetCondition implements LootItemCondition {
    public static final WetCondition INSTANCE = new WetCondition();
    public static final MapCodec<WetCondition> MAP_CODEC = MapCodec.unit(INSTANCE);

    private WetCondition() {

    }

    @Override
    public @NonNull LootItemConditionType getType() {
        return Forked.WET;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY);
        if (entity instanceof LivingEntity livingEntity) {
            return livingEntity.getAttachedOrElse(Forked.WET_ATTACHMENT, 0) > 0;
        }
        return false;
    }

}
