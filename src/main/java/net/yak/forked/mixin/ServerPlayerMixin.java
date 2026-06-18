package net.yak.forked.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import net.minecraft.world.level.Level;
import net.yak.forked.injection.ServerPlayerTridentTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player implements ServerPlayerTridentTracker {

    @Unique private List<ThrownTrident> forked$tridentList = null;

    public ServerPlayerMixin(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @Override
    public List<ThrownTrident> forked$getPlayerTridents() {
        return this.forked$tridentList;
    }

    @Override
    public void forked$addTrident(ThrownTrident trident) {
        if (this.forked$tridentList == null) {
            this.forked$tridentList = new ArrayList<>();
        }
        this.forked$tridentList.add(trident);
    }
}
