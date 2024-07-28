package com.example.examplemod.setup;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.entity.ModEntities;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ExampleMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetUp {

    @SubscribeEvent
    public static void rendererRegister(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(ModEntities.TNT_PROJECTILE.get(),ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.STONE_PROJECTILE.get(),ThrownItemRenderer::new);
    }
}
