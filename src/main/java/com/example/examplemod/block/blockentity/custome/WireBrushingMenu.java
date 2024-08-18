package com.example.examplemod.block.blockentity.custome;

import com.example.examplemod.menu.ModMenuTypes;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class WireBrushingMenu extends AbstractContainerMenu {
    public final WireBrushingBlockEntity blockEntity;
    private final Level level;
    private FluidStack fluidStack;

    public WireBrushingMenu(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(containerId,playerInv,playerInv.player.level().getBlockEntity(extraData.readBlockPos()));
    }


    public WireBrushingMenu(int id, Inventory inv, BlockEntity entity) {
        super(ModMenuTypes.WIRE_BRUSHING_MENU.get(), id);
        checkContainerSize(inv, 3);
        blockEntity = (WireBrushingBlockEntity) entity;
        this.level = inv.player.level();
        this.fluidStack = blockEntity.getFluidStack();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        ItemStackHandler itemHandler = this.blockEntity.getItemHandler();

        this.addSlot(new SlotItemHandler(itemHandler, 0, 12, 15));
        this.addSlot(new SlotItemHandler(itemHandler, 1, 86, 15));
        this.addSlot(new SlotItemHandler(itemHandler, 2, 86, 60));

//        addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }



    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }

    public FluidStack getFluidStack() {
        return blockEntity.getFluidStack();
    }


    public BlockEntity getBlockEntity() {
        return blockEntity;
    }

    public void setFluid(FluidStack fluid) {
        this.fluidStack = fluid;
    }
}
