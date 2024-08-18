package com.example.examplemod.block.blockentity.custome;

import com.example.examplemod.block.blockentity.ModBlockEntity;
import com.example.examplemod.network.packet.s2c.FluidSyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketDecoder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.http.impl.conn.Wire;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WireBrushingBlockEntity extends BlockEntity implements MenuProvider {

    protected static final int SLOT_INPUT_WATER = 0;
    protected static final int SLOT_INPUT_BRUSHING= 1;
    protected static final int SLOT_INPUT_WIRE_HOOK = 2;
    protected final RandomSource random = RandomSource.create();

    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();

//            if(!level.isClientSide){
//                PacketDistributor.sendToAllPlayers(new ItemStackSyncS2CPacket(this, worldPosition));
//            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0-> stack.is(Items.WATER_BUCKET);
                case 1 -> stack.is(Items.TRIPWIRE_HOOK);
                case 2 -> stack.is(Items.STRING);
                default -> super.isItemValid(slot, stack);
            };
        }
    };



    private final FluidTank FLUID_TANK = new FluidTank(64000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(!level.isClientSide()) {
                PacketDistributor.sendToAllPlayers(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.WATER;
        }
    };

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }



    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }

    public ItemStackHandler getItemHandler(){
        return itemHandler;
    }


    public WireBrushingBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntity.WIRE_BRUSHING.get(), pos, blockState);
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("examplemod.container.wire");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        if (!this.getFluidStack().isEmpty()){
            PacketDistributor.sendToAllPlayers(new FluidSyncS2CPacket(this.getFluidStack(), worldPosition));
        }
        return new WireBrushingMenu(containerId, playerInventory, this);
    }

    public void drops() {
    }

    public static void tick(Level level, BlockPos pos, BlockState state, WireBrushingBlockEntity wireBrushingBlockEntity) {

        if(level.isClientSide()) {
            return;
        }


        if(hasRecipe(wireBrushingBlockEntity) && wireBrushingBlockEntity.random.nextFloat() < 0.1) {
            //
            BlockPos above = pos.above();

            final double x = Math.floor(above.getX()) + .25d + wireBrushingBlockEntity.random.nextDouble() * .5;
            final double y = Math.floor(above.getY()) + .25d + wireBrushingBlockEntity.random.nextDouble() * .5;
            final double z = Math.floor(above.getZ()) + .25d + wireBrushingBlockEntity.random.nextDouble() * .5;
            final double xSpeed = wireBrushingBlockEntity.random.nextDouble() * .25 - 0.125;
            final double ySpeed = wireBrushingBlockEntity.random.nextDouble() * .25 - 0.125;
            final double zSpeed = wireBrushingBlockEntity.random.nextDouble() * .25 - 0.125;
            final ItemEntity newEntity = new ItemEntity(level, x, y, z, Items.STRING.getDefaultInstance());
            newEntity.setDeltaMovement(xSpeed, ySpeed, zSpeed);
            level.addFreshEntity(newEntity);

        }

        if(hasFluidItemInSourceSlot(wireBrushingBlockEntity)) {
            transferItemFluidToFluidTank(wireBrushingBlockEntity);
        }

    }

    private static void transferItemFluidToFluidTank(WireBrushingBlockEntity pEntity) {
        Optional.ofNullable(pEntity.itemHandler.getStackInSlot(0).getCapability(Capabilities.FluidHandler.ITEM)).ifPresent(handler -> {
            int drainAmount = Math.min(pEntity.FLUID_TANK.getSpace(), 1000);

            FluidStack stack = handler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if(pEntity.FLUID_TANK.isFluidValid(stack)) {
                stack = handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                fillTankWithFluid(pEntity, stack, handler.getContainer());
            }
        });
    }


    private static void fillTankWithFluid(WireBrushingBlockEntity pEntity, FluidStack stack, ItemStack container) {
        pEntity.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);
        pEntity.itemHandler.extractItem(0, 1, false);
        pEntity.itemHandler.insertItem(0, container, false);
    }


    private static boolean hasFluidItemInSourceSlot(WireBrushingBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(0).getCount() > 0;
    }


    private static boolean hasRecipe(WireBrushingBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }


        if (inventory.getContainerSize() < 3){
            return false;
        }

        if (inventory.getItem(2).is(Items.STRING) && inventory.getItem(2).getCount() > 2 && inventory.getItem(1).is(Items.TRIPWIRE_HOOK)
        && inventory.getItem(1).getCount() > 2 && hasCorrectFluidAmountInTank(entity)){
            return true;
        }
         return false;
    }

    private static boolean hasCorrectFluidAmountInTank(WireBrushingBlockEntity entity) {
        return entity.FLUID_TANK.getFluidAmount() >= 2000;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag = FLUID_TANK.writeToNBT(registries,tag);
        super.saveAdditional(tag, registries);
    }


    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        itemHandler.deserializeNBT(registries,tag.getCompound("inventory"));
        FLUID_TANK.readFromNBT(registries,tag);
        super.loadAdditional(tag, registries);
    }
}
