package com.example.examplemod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodComponents {

    public static final FoodProperties BREAD_SWORD=
            new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).alwaysEdible().build();
    public static final FoodProperties BREAD_SWORD_HOT=
            new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f)
                    .effect(()-> new MobEffectInstance(MobEffects.HEALTH_BOOST,200),0.25f).alwaysEdible().build();
    public static final FoodProperties BREAD_SWORD_VERY_HOT=
            new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).alwaysEdible().build();
    public static final FoodProperties POISON_SWORD =
            new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).alwaysEdible().build();
}
