package com.example.examplemod.datagen.lang;

import com.example.examplemod.ExampleMod;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLangGen extends LanguageProvider {
    public ModLangGen(PackOutput output, String locale) {
        super(output, ExampleMod.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        // 液体渲染
        add("tutorialmod.tooltip.liquid.amount.with.capacity","%s / %s mB");
        add("tutorialmod.tooltip.liquid.amount","%s mB");

    }

}
