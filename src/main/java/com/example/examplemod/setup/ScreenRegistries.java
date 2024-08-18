package com.example.examplemod.setup;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.menu.ModMenuTypes;
import com.example.examplemod.screen.WireBrushingScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = ExampleMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ScreenRegistries
{
    @SubscribeEvent
    public static void onClientSetup(RegisterMenuScreensEvent event)
    {
        // scrren
        event.register(ModMenuTypes.WIRE_BRUSHING_MENU.get(), WireBrushingScreen::new);
    }
}