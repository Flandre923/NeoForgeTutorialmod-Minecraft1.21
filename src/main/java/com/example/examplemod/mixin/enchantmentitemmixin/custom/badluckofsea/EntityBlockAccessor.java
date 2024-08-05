package com.example.examplemod.mixin.enchantmentitemmixin.custom.badluckofsea;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(BlockEntity.class)
public interface EntityBlockAccessor {

    @Invoker("saveAdditional")
    void saveAdditional(CompoundTag tag, HolderLookup.Provider registries);

}
