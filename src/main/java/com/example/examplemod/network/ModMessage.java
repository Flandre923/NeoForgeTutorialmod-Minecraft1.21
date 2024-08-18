package com.example.examplemod.network;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.network.packet.s2c.FluidSyncS2CPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = ExampleMod.MODID,bus = EventBusSubscriber.Bus.MOD)
public class ModMessage {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {

        final PayloadRegistrar registrar = event.registrar(ExampleMod.MODID);
        registrar.playBidirectional(
                FluidSyncS2CPacket.TYPE,
                FluidSyncS2CPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        FluidSyncS2CPacket::handle,
                        null
                )
        );

    }
}
