package com.example.examplemod.datagen.item.enchantment;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.item.enchantment.ModEnchantmentEffectComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.MultiplyValue;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

// 自定义附魔类，用于定义和注册新的附魔
public class ModEnchantments {
    // 定义一个名为"my_auto_smelt"的附魔资源键
    public static final ResourceKey<Enchantment> MY_AUTO_SMELT = key("my_auto_smelt");
    public static final ResourceKey<Enchantment> MY_BRITTLE_CURSE = key("my_brittle_curse");

    // 引导方法，用于初始化附魔注册
    public static void bootstrap(BootstrapContext<Enchantment> context)
    {
        // 获取各种注册表的持有者获取器
        HolderGetter<DamageType> holdergetter = context.lookup(Registries.DAMAGE_TYPE);
        HolderGetter<Enchantment> holdergetter1 = context.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> holdergetter2 = context.lookup(Registries.ITEM);
        HolderGetter<Block> holdergetter3 = context.lookup(Registries.BLOCK);

        // 注册自定义附魔"my_auto_smelt"
        register(
                context,
                MY_AUTO_SMELT,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        holdergetter2.getOrThrow(ItemTags.MINING_ENCHANTABLE),
                                        2,
                                        1,
                                        Enchantment.constantCost(25),
                                        Enchantment.constantCost(50),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        ).withEffect(
                        ModEnchantmentEffectComponents.MY_AUTO_SMELT_TOOL.get(),
                        new AddValue(LevelBasedValue.constant(1))
                )
        );

        register(
                context,
                MY_BRITTLE_CURSE,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.DURABILITY_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.ANY
                        )
                ).withEffect(
                        EnchantmentEffectComponents.ITEM_DAMAGE,
                        new MultiplyValue(LevelBasedValue.constant(16)),
                        LootItemRandomChanceCondition.randomChance(0.125f)
                )
        );
    }

    // 注册附魔的方法
    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }

    // 创建附魔资源键的方法
    private static ResourceKey<Enchantment> key(String name)
    {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID,name));
    }
}

