package com.example.examplemod.capability;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.block.blockentity.ModBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.apache.http.impl.conn.Wire;

@EventBusSubscriber(modid = ExampleMod.MODID,bus = EventBusSubscriber.Bus.MOD)
public class ModCapabilities {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                ModBlockEntity.WIRE_BRUSHING.get(),
                (blockEntity,side)->{
                    return blockEntity.getItemHandler();
                });
    }
}
