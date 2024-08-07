package com.example.examplemod;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config
{
    public static boolean isFuThrowable = true;
    public static boolean isAlwaysEnchantable = true;

    public static boolean isFireworkCanUseOnEntity = true;
    public static  boolean isFireworkCanHitOnEntity = true;

    public static boolean isFireworkCanUseOnEntity() {
        return isFireworkCanUseOnEntity;
    }
    public static boolean isFireworkCanHitOnEntity() {
        return isFireworkCanHitOnEntity;
    }


    public static boolean isAlwaysEnchantable() {
        return isAlwaysEnchantable;
    }
}
