package com.example.examplemod.item;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.block.ModBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.spongepowered.asm.util.ObfuscationUtil;

public class ModItemGroups {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExampleMod.MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.examplemod")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItem.RUBY.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItem.RUBY.get());
                output.accept(ModItem.BREAD_SWORD_VERY_HOT.get());
                output.accept(ModItem.BREAD_SWORD.get());
                output.accept(ModItem.BREAD_SWORD_HOT.get());
                output.accept(ModItem.TNT_BALL.get());
                output.accept(ModItem.STONE_BALL.get());
                output.accept(ModItem.FU.get());
                output.accept(ModItem.ZHU_GE.get());
                output.accept(ModItem.POISON_SWORD.get());
                output.accept(ModItem.LIGHTNING_BALL.get());
                output.accept(ModItem.LIGHTNING_ITEM.get());
                output.accept(ModItem.CHEESE_BERGER.get());
                output.accept(ModItem.COLLIABLE.get());
                output.accept(ModItem.MATH_SWORD.get());
                // BLOCK
                output.accept(ModBlock.GOLD_MELON.asItem());
                output.accept(ModBlock.POTATO_TNT.get());
                output.accept(ModBlock.POTATO_TNT_PREPARE.get());
                output.accept(ModBlock.WHATE_CAT_BLOCK.get());
            }).build());

    public static void registerModItemGroups(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
