package net.yak.forked.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.object.projectile.TridentModel;
import net.yak.forked.ForkedClient;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TridentModel.class)
public abstract class TridentModelMixin {

    @WrapMethod(method = "createLayer")
    private static LayerDefinition forked$replaceTridentModel(Operation<LayerDefinition> original) {
        if (ForkedClient.tridentResourcePackEnabled) {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();
            partdefinition.addOrReplaceChild("pole", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 2.0F, -0.5F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.0F))
                    .texOffs(4, 0).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                    .texOffs(4, 5).addBox(-0.5F, -4.0F, -0.005F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                    .texOffs(4, 3).addBox(-1.5F, 4.0F, -0.005F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                    .texOffs(6, 5).addBox(-2.5F, -3.0F, -0.005F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                    .texOffs(8, 5).addBox(1.5F, -3.0F, -0.005F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                    .texOffs(4, 9).addBox(-2.5F, -3.0F, 0.005F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                    .texOffs(6, 9).addBox(-0.5F, -4.0F, 0.005F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                    .texOffs(8, 9).addBox(1.5F, -3.0F, 0.005F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                    .texOffs(4, 4).addBox(-1.5F, 4.0F, 0.005F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));
            return LayerDefinition.create(meshdefinition, 32, 32);
        }
        return original.call();
    }

}
