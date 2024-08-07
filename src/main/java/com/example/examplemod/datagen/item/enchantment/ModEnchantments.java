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

    // *********************
    public static final ResourceKey<Enchantment> BAD_LUCK_OF_SEA = key("bad_luck_of_sea");
    public static final ResourceKey<Enchantment> EIGHT_GODS_PASS_SEA = key("eight_gods_pass_sea");
    public static final ResourceKey<Enchantment> KILL_CHICKEN_GET_EGG = key("kill_chicken_get_egg");
    public static final ResourceKey<Enchantment> GO_TO_SKY = key("go_to_sky");
    public static final ResourceKey<Enchantment> GONG_XI_FA_CAI = key("gong_xi_fa_cai");
    public static final ResourceKey<Enchantment> MERCY = key("mercy");
    public static final ResourceKey<Enchantment> KILL_MY_HORSE = key("kill_my_horse");
    public static final ResourceKey<Enchantment> KILL_MY_HORSE_PLUS = key("kill_my_horse_plus");
    public static final ResourceKey<Enchantment> HOT_POTATO = key("hot_potato");
    public static final ResourceKey<Enchantment> VERY_EASY = key("very_easy");
    public static final ResourceKey<Enchantment> REVERSE = key("reverse");
    public static final ResourceKey<Enchantment> PAY_TO_PLAY = key("pay_to_play");
    public static final ResourceKey<Enchantment> FEAR = key("fear");
    public static final ResourceKey<Enchantment> MUTE = key("mute");
    public static final ResourceKey<Enchantment> SLIPPERY = key("slippery");
    public static final ResourceKey<Enchantment> NEVER_GONNA = key("never_gonna");
    public static final ResourceKey<Enchantment> RESONANCE = key("resonance");
    public static final ResourceKey<Enchantment> NO_BLAST_PROTECTION = key("no_blast_protection");
    public static final ResourceKey<Enchantment> A_LEAF = key("a_leaf");
    public static final ResourceKey<Enchantment> BUTTERFLY = key("butterfly");
    public static final ResourceKey<Enchantment> FANGSHENG = key("fangsheng");
    public static final ResourceKey<Enchantment> NEVER_STOP = key("never_stop");
    public static final ResourceKey<Enchantment> BOW_LEFT = key("bow_left");
    public static final ResourceKey<Enchantment> SUPER_PROJECTILE_PROTECTION = key("super_projectile_protection");
    public static final ResourceKey<Enchantment> STICKY = key("sticky");

    // *********************

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

        // ******************
        register(
                context,
                BAD_LUCK_OF_SEA,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                                2,
                                3,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.FEET
                        )
                )
        );

        register(
                context,
                EIGHT_GODS_PASS_SEA,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.FEET
                        )
                )
        );



        register(
                context,
                KILL_CHICKEN_GET_EGG,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.MAINHAND
                        )
                )
        );


        register(
                context,
                GO_TO_SKY,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.MAINHAND
                        )
                )
        );

        register(
                context,
                GONG_XI_FA_CAI,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.MAINHAND
                        )
                )
        );


        register(
                context,
                MERCY,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.MAINHAND
                        )
                )
        );



        register(
                context,
                KILL_MY_HORSE,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.ANY
                        )
                )
        );

        register(
                context,
                KILL_MY_HORSE_PLUS,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.ANY
                        )
                )
        );

        register(
                context,
                HOT_POTATO,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.MAINHAND
                        )
                )
        );


        register(
                context,
                VERY_EASY,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                2,
                                5,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.MAINHAND
                        )
                )
        );


        register(
                context,
                REVERSE,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.MAINHAND
                        )
                )
        );


        register(
                context,
                PAY_TO_PLAY,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                2,
                                5,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.MAINHAND
                        )
                )
        );
        register(
                context,
                FEAR,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                2,
                                5,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.MAINHAND
                        )
                )
        );

        register(
                context,
                MUTE,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.HEAD
                        )
                )
        );


        register(
                context,
                SLIPPERY,
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
                )
        );


        register(
                context,
                NEVER_GONNA,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.MINING_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.ANY
                        )
                )
        );



        register(
                context,
                NO_BLAST_PROTECTION,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.ANY
                        )
                )
        );

        register(
                context,
                A_LEAF,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.ANY
                        )
                )
        );

        register(
                context,
                BUTTERFLY,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.ANY
                        )
                )
        );
        register(
                context,
                FANGSHENG,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.MINING_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.ANY
                        )
                )
        );

        register(
                context,
                NEVER_STOP,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.ANY
                        )
                )
        );

        register(
                context,
                BOW_LEFT,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.ANY
                        )
                )
        );

        register(
                context,
                SUPER_PROJECTILE_PROTECTION,
                Enchantment.enchantment(
                        Enchantment.definition(
                                holdergetter2.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(50),
                                8,
                                EquipmentSlotGroup.ANY
                        )
                )
        );

        register(
                context,
                STICKY,
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
                )
        );


        register(
                context,
                RESONANCE,
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
                )
        );
        // ******************
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

