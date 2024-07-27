package com.example.examplemod.item.enchantment;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.datagen.item.enchantment.ModEnchantments;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.LootContext;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModEnchantmentHelper {

    public static boolean hasAutoSmeltEnchantment(ServerLevel level, ItemStack stack) {
        ItemEnchantments itemenchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        var isHaveEnchantment=  false;
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : itemenchantments.entrySet()) {
            Holder<Enchantment> enchantmentHolder = entry.getKey();
            Enchantment value = enchantmentHolder.value();
            List<ConditionalEffect<EnchantmentValueEffect>> effects = value.getEffects(ModEnchantmentEffectComponents.MY_AUTO_SMELT_TOOL.value());
//            var returnValue = 0;
//            for (ConditionalEffect<EnchantmentValueEffect> effect : effects) {
//                EnchantmentValueEffect effect1 = effect.effect();
//                var applyValue = effect1.process(entry.getIntValue(),level.random,returnValue);
//                returnValue += applyValue;
//            }
//            return  returnValue;
            if (!effects.isEmpty()){
                isHaveEnchantment =true;
                break;
            }
        }
        // more easy way
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : itemenchantments.entrySet()) {
            @Nullable ResourceKey<Enchantment> value = entry.getKey().getKey();
            if(value.isFor(ModEnchantments.MY_AUTO_SMELT.registryKey()))
                ExampleMod.LOGGER.info("this itemstack " + stack + "has auto smelt enchantment" + entry.getKey().value());
        }

        return isHaveEnchantment;
    }


}
