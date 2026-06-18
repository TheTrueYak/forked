package net.yak.forked.injection;

import net.minecraft.world.entity.projectile.arrow.ThrownTrident;

import java.util.List;

public interface ServerPlayerTridentTracker {

    List<ThrownTrident> forked$getPlayerTridents();

    void forked$addTrident(ThrownTrident trident);

}
