package com.example.examplemod.network;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.network.packet.C2S.SheepBreedingC2SPacket;
import com.example.examplemod.network.packet.C2S.ThrowPowerC2SPacket;
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
                ThrowPowerC2SPacket.TYPE,
                ThrowPowerC2SPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        null,
                        ThrowPowerC2SPacket::handle
                )
        );


        registrar.playBidirectional(
                SheepBreedingC2SPacket.TYPE,
                SheepBreedingC2SPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        null,
                        SheepBreedingC2SPacket::handle
                )
        );

    }
}
