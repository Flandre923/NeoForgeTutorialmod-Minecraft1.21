package com.example.examplemod.network.packet.s2c;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.block.blockentity.custome.WireBrushingBlockEntity;
import com.example.examplemod.block.blockentity.custome.WireBrushingMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class FluidSyncS2CPacket implements CustomPacketPayload {

    public FluidStack fluid;
    public BlockPos worldPos;

    public static final StreamCodec<RegistryFriendlyByteBuf,FluidSyncS2CPacket> STREAM_CODEC =
            CustomPacketPayload.codec(FluidSyncS2CPacket::encode,FluidSyncS2CPacket::new);

    public static final CustomPacketPayload.Type<FluidSyncS2CPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID,"thirst_data"));

    public FluidSyncS2CPacket(RegistryFriendlyByteBuf buf) {
        this.worldPos = buf.readBlockPos();
        this.fluid = FluidStack.STREAM_CODEC.decode(buf);
    }

    public FluidSyncS2CPacket( FluidStack fluid,BlockPos pos) {
        this.fluid = fluid;
        this.worldPos = pos;
    }

    private void encode(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(this.worldPos);
        FluidStack.STREAM_CODEC.encode(buf,this.fluid);
    }

    public static void handle(final FluidSyncS2CPacket data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(data.worldPos) instanceof WireBrushingBlockEntity blockEntity) {
                blockEntity.setFluid(data.fluid);

                if(Minecraft.getInstance().player.containerMenu instanceof WireBrushingMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(data.worldPos)) {
                    menu.setFluid(data.fluid);
                }
            }
        });
    }
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
