package com.example.examplemod.item.enchantment;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface ModEnchantmentEffectComponents {

    DeferredRegister<DataComponentType<?>> ENCHANTMENT_COMPONENTS = DeferredRegister.create(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, ExampleMod.MODID);

    DeferredHolder<DataComponentType<?>,DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> MY_AUTO_SMELT_TOOL = register(
            "my_auto_smelt_tool",
            listBuilder -> listBuilder.persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ITEM).listOf())
    );


//    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> operator) {
//        return Registry.register(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID,name), operator.apply(DataComponentType.builder()).build());
//    }

    private static <T> DeferredHolder<DataComponentType<?>,DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> operator) {
        return ENCHANTMENT_COMPONENTS.register(name,()-> operator.apply(DataComponentType.builder()).build());
    }


    public static void register(IEventBus eventBus) {
        ENCHANTMENT_COMPONENTS.register(eventBus);
    }

}
