package com.example.examplemod.mixin.itemmixin.fireworkrocket;

import com.example.examplemod.Config;
import com.example.examplemod.mixinhelper.FireworkRocketEntityMixinHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(FireworkRocketEntity.class)
public class FireworkRocketEntityMixin {
    @Inject(method = "onHitEntity",at = @At("HEAD"))
    public void init(EntityHitResult entityHitResult, CallbackInfo ci){
        boolean fireworkCanHitOnEntity = Config.isFireworkCanHitOnEntity();
        if (fireworkCanHitOnEntity)
        {
            Entity entity = entityHitResult.getEntity();
            FireworkRocketEntityMixinHelper.storeEntityValue(entity.getId(),5);
        }
    }

}
