package com.example.examplemod.mixin.enchantmentitemmixin;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract  class InfiniteUndyingMixin extends Entity implements Attackable {

    @Shadow
    public abstract void setHealth(float health);
    @Shadow
    public abstract boolean removeEffectsCuredBy(net.neoforged.neoforge.common.EffectCure cure);
    @Shadow
    public abstract boolean addEffect(MobEffectInstance effectInstance);
    @Shadow
    public abstract ItemStack getItemInHand(InteractionHand hand) ;
    public InfiniteUndyingMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }


    /**
     * @author
     * Mafuyu33
     * @reason
     * Change the totem of undying code
     */
    @Overwrite
    private boolean checkTotemDeathProtection(DamageSource damageSource) {
        if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        } else {
            ItemStack itemstack = null;

            for (InteractionHand interactionhand : InteractionHand.values()) {
                ItemStack itemstack1 = this.getItemInHand(interactionhand);
                if (itemstack1.is(Items.TOTEM_OF_UNDYING) && net.neoforged.neoforge.common.CommonHooks.onLivingUseTotem((LivingEntity)(Object)this, damageSource, itemstack1, interactionhand)) {
                    itemstack = itemstack1.copy();
//                    itemstack1.shrink(1);
                    break;
                }
            }

            if (itemstack != null) {
                if ((LivingEntity)(Object)this  instanceof ServerPlayer serverplayer) {
                    serverplayer.awardStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING), 1);
                    CriteriaTriggers.USED_TOTEM.trigger(serverplayer, itemstack);
                    this.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                }

                this.setHealth(1.0F);
                this.removeEffectsCuredBy(net.neoforged.neoforge.common.EffectCures.PROTECTED_BY_TOTEM);
                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                this.level().broadcastEntityEvent(this, (byte)35);
            }

            return itemstack != null;
        }
    }

}
