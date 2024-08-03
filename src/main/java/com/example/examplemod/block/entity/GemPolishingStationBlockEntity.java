//package com.example.examplemod.block.entity;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.core.NonNullList;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.Container;
//import net.minecraft.world.MenuProvider;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.inventory.AbstractContainerMenu;
//import net.minecraft.world.inventory.ContainerData;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.TooltipFlag;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.world.level.block.state.BlockState;
//import net.neoforged.neoforge.items.ItemStackHandler;
//import org.jetbrains.annotations.Nullable;
//
//import java.beans.PropertyDescriptor;
//
//public class GemPolishingStationBlockEntity extends BlockEntity implements MenuProvider {
//    private final ItemStackHandler itemHandler = new ItemStackHandler(3){
//        @Override
//        protected void onContentsChanged(int slot) {
//            setChanged();
//        }
//    };
//
//
//    public static final int INPUT_SLOT = 0;
//    public static final int OUTPUT_SLOT = 1;
//
//    protected final ContainerData containerData;
//
//    private int progress = 0;
//    private int maxProgress = 72;
//
//    public GemPolishingStationBlockEntity(BlockPos pos, BlockState blockState) {
//        super(ModBlockEntities.GEM_POLISHING_STATION.get(), pos, blockState);
//
//        this.containerData = new ContainerData() {
//            @Override
//            public int get(int index) {
//                return switch (index) {
//                    case 0 -> GemPolishingStationBlockEntity.this.progress;
//                    case 1 -> GemPolishingStationBlockEntity.this.maxProgress;
//                    default -> 0;
//                };
//            }
//
//            @Override
//            public void set(int index, int value) {
//                switch (index) {
//                    case 0 -> GemPolishingStationBlockEntity.this.progress = value;
//                    case 1 -> GemPolishingStationBlockEntity.this.maxProgress = value;
//                }
//            }
//
//            @Override
//            public int getCount() {
//                return 2;
//            }
//        };
//    }
//
//    @Override
//    public Component getDisplayName() {
//        return Component.literal("宝石抛光站");
//    }
//
//
//    @Nullable
//    @Override
//    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
//        return new GemPolishingStationMenu(containerId, playerInventory, this,this.containerData);
//    }
//
//    @Override
//    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
//        super.saveAdditional(tag, registries);
//        tag.put("item_handler", this.itemHandler.serializeNBT(registries));
//        tag.putInt("progress", this.progress);
//    }
//
//    @Override
//    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
//        super.loadAdditional(tag, registries);
//        this.itemHandler.deserializeNBT(registries,tag.getCompound("item_handler"));
//        this.progress = tag.getInt("progress");
//    }
//
//    public void tick(Level level, BlockPos pos, BlockState state){
//        if(level.isClientSide)
//            return;
//    }
//
//
//}
