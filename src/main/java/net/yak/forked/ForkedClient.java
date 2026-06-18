package net.yak.forked;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;

public class ForkedClient implements ClientModInitializer {

    public static boolean tridentResourcePackEnabled = false;

    @Override
    public void onInitializeClient() {

        FabricLoader.getInstance().getModContainer(Forked.MOD_ID).ifPresent(modContainer -> ResourceManagerHelper.registerBuiltinResourcePack(Identifier.fromNamespaceAndPath(Forked.MOD_ID, "trident_remodel"), modContainer, ResourcePackActivationType.DEFAULT_ENABLED));

        /*ClientTickEvents.START_LEVEL_TICK.register(clientLevel -> {
            Minecraft client = Minecraft.getInstance();
            LocalPlayer player = client.player;
            for (LivingEntity livingEntity : clientLevel.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(100), livingEntity -> livingEntity.getAttachedOrElse(Forked.WET_ATTACHMENT, 0) > 0)) {
                if (livingEntity.isInWaterOrRain() && !(client.options.getCameraType().isFirstPerson() && livingEntity.getStringUUID().equals(player.getStringUUID()))) {
                    clientLevel.addParticle(ParticleTypes.FALLING_WATER, livingEntity.getX() + livingEntity.getRandom().nextDouble() / 5 - 0.2f, livingEntity.getY() + livingEntity.getBbHeight() / 2 + ((livingEntity.getRandom().nextDouble() - 0.5) * (livingEntity.getBbHeight() / 4)), livingEntity.getZ() + livingEntity.getRandom().nextDouble() / 5 - 0.2f, 0, 0, 0);
                }
            }
        });*/
    }
}
