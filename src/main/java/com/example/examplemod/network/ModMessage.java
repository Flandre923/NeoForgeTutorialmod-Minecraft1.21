package com.example.examplemod.network;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.network.packet.C2S.GameOptionsC2SPacket;
import com.example.examplemod.network.packet.C2S.SheepBreedingC2SPacket;
import com.example.examplemod.network.packet.C2S.ThrowPowerC2SPacket;
import com.example.examplemod.network.packet.S2C.BellSoundS2CPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
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

        registrar.playBidirectional(
                GameOptionsC2SPacket.TYPE,
                GameOptionsC2SPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<GameOptionsC2SPacket>(
                        null,
                        GameOptionsC2SPacket::handle
                )
        );

        // server

        registrar.playBidirectional(
                BellSoundS2CPacket.TYPE,
                BellSoundS2CPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        BellSoundS2CPacket::handle,
                        null
                )
        );



    }
}
