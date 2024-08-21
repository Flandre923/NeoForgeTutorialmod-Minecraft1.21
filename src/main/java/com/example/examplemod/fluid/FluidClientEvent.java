package com.example.examplemod.fluid;

import com.example.examplemod.ExampleMod;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = ExampleMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FluidClientEvent {

    @SubscribeEvent
    public static void fluidClient(RegisterClientExtensionsEvent event){
        FluidResources.fluidList.forEach(fluid -> {
            if (fluid.TYPE.get() instanceof ModBaseFluidType modBaseFluidType)
                event.registerFluidType(modBaseFluidType.getClientExtensions(), modBaseFluidType);
        });
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        FluidResources.fluidList.stream()
                .filter(fluid -> fluid.isTranslucent)
                .forEach(fluid -> {
                    ItemBlockRenderTypes.setRenderLayer(fluid.FLUID.get(), RenderType.translucent());
                    ItemBlockRenderTypes.setRenderLayer(fluid.FLUID_FLOW.get(), RenderType.translucent());
                });
    }
}
