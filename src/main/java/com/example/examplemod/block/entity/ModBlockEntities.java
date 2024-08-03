package com.example.examplemod.block.entity;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.block.ModBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {

    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ExampleMod.MODID);

//    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GemPolishingStationBlockEntity>> GEM_POLISHING_STATION =
//            BLOCK_ENTITY_TYPES.register("gem_polishing_station",
//                    () -> BlockEntityType.Builder.of(GemPolishingStationBlockEntity::new, ModBlock.GEM_POLISHING_STATION.get()).build(null));

    public static void register(IEventBus bus){
        BLOCK_ENTITY_TYPES.register(bus);
    }
}
