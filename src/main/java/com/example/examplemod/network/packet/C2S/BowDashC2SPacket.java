package com.example.examplemod.network.packet.C2S;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.mixinhelper.BowDashMixinHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class BowDashC2SPacket implements CustomPacketPayload {

    public int coldDown;
    public static final CustomPacketPayload.Type<BowDashC2SPacket> TYPE = new CustomPacketPayload.Type<BowDashC2SPacket>(ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID,"bow_dash"));
    public static final StreamCodec<FriendlyByteBuf,BowDashC2SPacket> STREAM_CODEC =
            CustomPacketPayload.codec(BowDashC2SPacket::write,BowDashC2SPacket::new);

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(this.coldDown);
    }

    public BowDashC2SPacket(FriendlyByteBuf buf) {
        this.coldDown = buf.readInt();
    }

    public BowDashC2SPacket(int coldDown){
        this.coldDown = coldDown;
    }

    public static void receive(final BowDashC2SPacket data, final IPayloadContext context) {
        BowDashMixinHelper.storeHitCoolDown(context.player().getId(),data.coldDown);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
