package com.example.examplemod.entity;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ExampleMod.MODID);

    public static final DeferredHolder<EntityType<?>,EntityType<TNTProjectileEntity>> TNT_PROJECTILE = ENTITY_TYPES.register("tnt_projectile",
            ()-> EntityType.Builder.<TNTProjectileEntity>of(TNTProjectileEntity::new, MobCategory.MISC).sized(0.25f,0.25f).build("tnt_projectile"));

    public static final DeferredHolder<EntityType<?>,EntityType<StoneBallProjectileEntity>> STONE_PROJECTILE = ENTITY_TYPES.register("stone_projectile",
            ()-> EntityType.Builder.<StoneBallProjectileEntity>of(StoneBallProjectileEntity::new, MobCategory.MISC).sized(0.25f,0.25f).build("stone_projectile"));


    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }

}
