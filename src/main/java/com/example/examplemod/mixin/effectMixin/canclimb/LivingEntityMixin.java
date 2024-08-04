package com.example.examplemod.mixin.effectMixin.canclimb;

import com.example.examplemod.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract boolean hasEffect(Holder<MobEffect> effect);

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at=@At("HEAD"),method = "onClimbable",cancellable = true)
    private void init(CallbackInfoReturnable<Boolean> cir) {
        if(this.hasEffect(ModEffects.SPIDER_EFFECT)) {
            if (this.horizontalCollision) {
                cir.setReturnValue(true);
            }
        }
    }
}
