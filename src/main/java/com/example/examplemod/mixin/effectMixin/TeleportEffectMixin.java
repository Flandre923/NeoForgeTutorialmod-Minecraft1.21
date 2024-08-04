package com.example.examplemod.mixin.effectMixin;

import com.example.examplemod.effect.ModEffects;
import com.example.examplemod.item.custom.ColliableItem;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class TeleportEffectMixin extends Entity implements Attackable {
    @Shadow public abstract boolean hasEffect(Holder<MobEffect> effect);

    @Unique
    int time = 0;

    public TeleportEffectMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }


    @Override
    public boolean canBeCollidedWith() {
        return ColliableItem.isColliable();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void init(CallbackInfo ci) {
        if(this.isAlwaysTicking() && this.hasEffect(ModEffects.TELEPORT_EFFECT)) {//传送药水
            time++;
            if (time > 1) {
                randomTeleport(this.level(), (LivingEntity) (Object) this);
                time=0;
            }
        }
    }


    @Unique
    private void randomTeleport(Level world, LivingEntity user) {
        if (!world.isClientSide) {
            for(int i = 0; i < 16; ++i) {
                double d = user.getX() + (user.getRandom().nextDouble() - 0.5) * 16.0;
                double e = Math.clamp(user.getY() + (double)(user.getRandom().nextInt(16) - 8), (double)world.getMinBuildHeight(), (double)(world.getMinBuildHeight() + ((ServerLevel)world).getLogicalHeight() - 1));
                double f = user.getZ() + (user.getRandom().nextDouble() - 0.5) * 16.0;
                if (user.isPassenger()) {
                    user.stopRiding();
                }

                Vec3 vec3d = user.position();
                if (user.randomTeleport(d, e, f, true)) {
                    world.gameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Context.of(user));
                    SoundSource soundCategory;
                    SoundEvent soundEvent;
                    if (user instanceof Fox) {
                        soundEvent = SoundEvents.FOX_TELEPORT;
                        soundCategory = SoundSource.NEUTRAL;
                    } else {
                        soundEvent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                        soundCategory = SoundSource.PLAYERS;
                    }

                    world.playSound((Player) null, user.getX(), user.getY(), user.getZ(), soundEvent, soundCategory);
                    user.resetFallDistance();
                    break;
                }
            }
        }
    }

}
