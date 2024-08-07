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
    public static final DeferredItem<Item> ZHU_GE = registerItem("zhuge",()-> new ZhuGeItem(new Item.Properties().stacksTo(1).durability(425)));
    public static final DeferredItem<Item> POISON_SWORD = registerItem("poison_sword",
            ()->  new PoisonSwordItem(Tiers.DIAMOND, new Item.Properties().food(ModFoodComponents.POISON_SWORD)));
    public static final DeferredItem<Item> LIGHTNING_BALL = registerItem("lightning_ball",
            ()-> new LightningBallItem(new Item.Properties()));
    public static final DeferredItem<Item> LIGHTNING_ITEM = registerItem("lightning_item",
            ()-> new LightningItem(Tiers.NETHERITE,new Item.Properties().fireResistant().stacksTo(1)));
    public static final DeferredItem<Item> CHEESE_BERGER = registerItem("cheese_berger",
            ()-> new CheeseBergerItem(new Item.Properties().food(ModFoodComponents.CHEESE_BERGER)));
    public static final DeferredItem<Item> FU = registerItem("fu",FuItem::new);
    public static DeferredItem<Item> registerItem(String name, Supplier<Item> itemSupplier){
        return ITEMS.register(name,itemSupplier);
    }

    public static void registerModItems(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
