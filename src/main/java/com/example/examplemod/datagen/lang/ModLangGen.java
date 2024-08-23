package com.example.examplemod.datagen.lang;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.datagen.item.enchantment.ModEnchantments;
import com.mojang.datafixers.kinds.IdF;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLangGen extends LanguageProvider {

    public static final String EnchantmentNamePrefix = "enchantment.";
    public ModLangGen(PackOutput output, String locale) {
        super(output, ExampleMod.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        // 液体渲染
        add("tutorialmod.tooltip.liquid.amount.with.capacity","%s / %s mB");
        add("tutorialmod.tooltip.liquid.amount","%s mB");
        enchantmentAdd(ModEnchantments.MY_AUTO_SMELT,"Auto Smelt");
    }

    public void enchantmentAdd(ResourceKey<Enchantment> key, String value){
        String registry = key.registry().getPath();
        ResourceLocation location = key.location();
        add(registry + "." +location.getNamespace() +"."+location.getPath(),value);
    }

}
