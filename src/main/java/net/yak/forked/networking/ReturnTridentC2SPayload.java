package net.yak.forked.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import net.yak.forked.Forked;
import net.yak.forked.injection.ServerPlayerTridentTracker;
import org.jspecify.annotations.NonNull;

import java.util.List;

public record ReturnTridentC2SPayload() implements CustomPacketPayload {

    public static final Identifier PACKET_ID = Identifier.fromNamespaceAndPath(Forked.MOD_ID, "return_trident");
    public static final Type<ReturnTridentC2SPayload> TYPE = new Type<>(PACKET_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, ReturnTridentC2SPayload> CODEC = StreamCodec.unit(new ReturnTridentC2SPayload());

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<ReturnTridentC2SPayload> {

        @Override
        public void receive(ReturnTridentC2SPayload payload, ServerPlayNetworking.Context context) {
            ServerPlayer serverPlayer = context.player();
            if (serverPlayer instanceof ServerPlayerTridentTracker serverPlayerTridentTracker) {
                List<ThrownTrident> list = serverPlayerTridentTracker.forked$getPlayerTridents();
                if (list != null && !list.isEmpty()) {
                    for (ThrownTrident trident : list) {
                        trident.dealtDamage = true;
                    }
                    list.clear();
                }
            }
        }
    }

}

