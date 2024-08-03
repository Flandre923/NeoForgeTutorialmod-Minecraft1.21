//package com.example.examplemod.block.custome;
//
//import com.example.examplemod.block.entity.GemPolishingStationBlockEntity;
//import com.mojang.serialization.MapCodec;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.Containers;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.ItemInteractionResult;
//import net.minecraft.world.MenuProvider;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.BlockGetter;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.BaseEntityBlock;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.BlockHitResult;
//import net.minecraft.world.phys.shapes.CollisionContext;
//import net.minecraft.world.phys.shapes.VoxelShape;
//import org.jetbrains.annotations.Nullable;
//
//public class GemPolishingStationBlock extends BaseEntityBlock {
//    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 12, 16);
//    public GemPolishingStationBlock(Properties properties) {
//        super(properties);
//    }
//
//    @Override
//    protected MapCodec<? extends BaseEntityBlock> codec() {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
//        return new GemPolishingStationBlockEntity(pos,state);
//    }
//
//    @Override
//    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
//        if (state.getBlock()!=newState.getBlock()){
//            BlockEntity blockEntity = level.getBlockEntity(pos);
//            if (blockEntity instanceof GemPolishingStationBlockEntity gemPolishingStationBlockEntity){
//                Containers.dropContents(level,pos,gemPolishingStationBlockEntity);
//                level.updateNeighbourForOutputSignal(pos,this);
//            }
//        }
//        super.onRemove(state, level, pos, newState, movedByPiston);
//    }
//
//    @Override
//    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
//        return SHAPE;
//    }
//
//    @Override
//    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
//        if (!level.isClientSide){
//            MenuProvider menuProvider = ((GemPolishingStationBlockEntity) level.getBlockEntity(pos));
//            if (menuProvider != null){
//                player.openMenu(menuProvider);
//            }
//
//        }
//        return ItemInteractionResult.SUCCESS;
//    }
//}
