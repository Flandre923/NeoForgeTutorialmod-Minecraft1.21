package com.example.examplemod.block.blockentity;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.block.ModBlocks;
import com.example.examplemod.block.blockentity.custome.WireBrushingBlockEntity;
import com.mojang.datafixers.types.Type;
import com.sun.jna.platform.win32.WinError;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntity {

    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ExampleMod.MODID);

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<WireBrushingBlockEntity>> WIRE_BRUSHING = register(
            "wire_brushing_block_entity",  () -> BlockEntityType.Builder.of(WireBrushingBlockEntity::new, ModBlocks.WIRE_BRUSHING_BLOCK.get())
    );
    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String name, Supplier<BlockEntityType.Builder<T>> builder) {
        return BLOCK_ENTITIES.register(name,()-> builder.get().build(null));
    }


    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
