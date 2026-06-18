package net.yak.forked.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.yak.forked.Forked;
import net.yak.forked.ForkedClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(PackRepository.class)
public abstract class PackRepositoryMixin {

    @ModifyReturnValue(method = "rebuildSelected", at = @At("RETURN"))
    private List<Pack> forked$checkTridentRemodelActive(List<Pack> original) {
        ForkedClient.tridentResourcePackEnabled = false;
        original.forEach(pack -> {
            if (pack.getId().equals(Forked.MOD_ID + ":trident_remodel")) {
                ForkedClient.tridentResourcePackEnabled = true;
            }
        });
        return original;
    }

}
