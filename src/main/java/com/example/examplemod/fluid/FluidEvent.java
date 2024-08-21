package com.example.examplemod.fluid;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.item.ModCreativeTab;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(modid = ExampleMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class FluidEvent {

    public static CreativeTab CREATIVE_TAB;


    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (CREATIVE_TAB == null)
            return;
        if (event.getTabKey() == CREATIVE_TAB.creativeModeTabResourceKey()) {
            for (FluidResources.FluidStuff fluidStuff : FluidResources.fluidList) {
                event.accept(fluidStuff.FLUID_BUCKET.get());
            }
        }
    }

    public static void init(ResourceKey<CreativeModeTab> creativeModeTabResourceKey){
        CREATIVE_TAB = new CreativeTab(creativeModeTabResourceKey);
    }

    public  record CreativeTab(ResourceKey<CreativeModeTab> creativeModeTabResourceKey){

    }
}
