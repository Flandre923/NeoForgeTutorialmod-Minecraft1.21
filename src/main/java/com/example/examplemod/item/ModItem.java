package com.example.examplemod.item;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.item.custom.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.PublishCommand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItem {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExampleMod.MODID);
    public static final DeferredItem<Item> RUBY = registerItem("ruby",()-> new Item(new Item.Properties().fireResistant()));

    public static final DeferredItem<Item> BREAD_SWORD = registerItem("bread_sword", BreadSwordItem::new);
    public static final DeferredItem<Item> BREAD_SWORD_HOT = registerItem("bread_sword_hot", BreadSwordHotItem::new);
    public static final DeferredItem<Item> BREAD_SWORD_VERY_HOT = registerItem("bread_sword_very_hot", BreadSwordVeryHotItem::new);
    public static final DeferredItem<Item>  TNT_BALL = registerItem("tnt_ball", ()-> new TNTBallItem(new Item.Properties()));
    public static final DeferredItem<Item>  STONE_BALL = registerItem("stone_ball", ()-> new StoneBallItem(new Item.Properties()));
    public static DeferredItem<Item> registerItem(String name, Supplier<Item> itemSupplier){
        return ITEMS.register(name,itemSupplier);
    }

    public static void registerModItems(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
