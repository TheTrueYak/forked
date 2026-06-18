package net.yak.forked;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.yak.forked.networking.ReturnTridentC2SPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Forked implements ModInitializer {
	public static final String MOD_ID = "forked";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final AttachmentType<Integer> WET_ATTACHMENT = AttachmentRegistry.create(Identifier.fromNamespaceAndPath(MOD_ID, "wet_attachment"),
			builder -> builder.initializer(() -> 0).syncWith(ByteBufCodecs.INT, AttachmentSyncPredicate.targetOnly()));

	@Override
	public void onInitialize() {

		PayloadTypeRegistry.serverboundPlay().register(ReturnTridentC2SPayload.TYPE, ReturnTridentC2SPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(ReturnTridentC2SPayload.TYPE, new ReturnTridentC2SPayload.Receiver());

		DefaultItemComponentEvents.MODIFY.register(modifyContext -> {
			modifyContext.modify(item -> item instanceof TridentItem, (builder, item) -> {
				float swingAnimationSeconds = 1.05F;
				builder.set(DataComponents.PIERCING_WEAPON, new PiercingWeapon(true, false,
						Optional.of(SoundEvents.SPEAR_ATTACK),
						Optional.of(SoundEvents.SPEAR_HIT)));
				builder.set(DataComponents.ATTACK_RANGE, new AttackRange(0.0F, 4.5F, 0.0F, 6.5F, 0.125F, 0.5F));
				builder.set(DataComponents.MINIMUM_ATTACK_CHARGE, 1.0F);
				builder.set(DataComponents.SWING_ANIMATION, new SwingAnimation(SwingAnimationType.STAB, (int) (swingAnimationSeconds * 20.0F)));
					/*.attributeModifiers(AttributeModifiersComponent.builder()
							.add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, (double)(0.0F + material.attackDamageBonus()), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
							.add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, (double)(1.0F / swingAnimationSeconds) - (double)4.0F, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build())*/
				builder.set(DataComponents.USE_EFFECTS, new UseEffects(true, false, 1.0F));
				builder.set(DataComponents.WEAPON, new Weapon(1));
			});
		});
	}
}