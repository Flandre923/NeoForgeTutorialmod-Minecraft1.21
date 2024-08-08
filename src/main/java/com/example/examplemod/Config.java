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
    public static boolean isShieldDashable = true;

    public static boolean isFireworkCanUseOnEntity() {
        return isFireworkCanUseOnEntity;
    }
    public static boolean isFireworkCanHitOnEntity() {
        return isFireworkCanHitOnEntity;
    }

    public static boolean isShieldDashable() {
        return isShieldDashable;
    }

    public static boolean isAlwaysEnchantable() {
        return isAlwaysEnchantable;
    }

    public static boolean isLeadCanLinkTogether() {
        return true;
    }

    public static Float breakDistance() {
        return 10f;
    }

    public static boolean isLeadCanLinkEveryMob() {
        return true;
    }

    public static boolean isSpyglassCanPin() {
        return true;
    }

    public static boolean isSwimTripwire() {
        return true;
    }

    public static boolean isBowDashable() {
        return true;
    }

    public static boolean isNestedBoxInfinite() {
        return true;
    }

    public static boolean isGoatDashForever() {
        return true;
    }

    public static boolean isGoatDashTogether() {
        return true;
    }

    public static boolean isLlamaSpitForever() {
        return true;
    }

    public static boolean isBeeRideable() {
        return true;
    }

    public static boolean isQinNa() {
        return true;
    }
}
