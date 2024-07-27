package com.example.examplemod.datagen;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.datagen.item.enchantment.ModEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
// 扩展自DatapackBuiltinEntriesProvider的类，用于提供自定义的数据包内置条目
public class ModDatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider {
    // 定义一个RegistrySetBuilder实例，用于注册附魔相关的条目
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap);

    // 构造函数，初始化ModDatapackBuiltinEntriesProvider实例
    public ModDatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(ExampleMod.MODID));
    }
}

