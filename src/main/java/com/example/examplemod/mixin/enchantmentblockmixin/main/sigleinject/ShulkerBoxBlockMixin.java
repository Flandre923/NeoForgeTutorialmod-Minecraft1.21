//package com.example.examplemod.mixin.enchantmentblockmixin.main.sigleinject;
//
//import com.example.examplemod.mixinhelper.InjectHelper;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.ShulkerBoxBlock;
//import net.minecraft.world.level.block.state.BlockState;
//import org.jetbrains.annotations.Nullable;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(ShulkerBoxBlock.class)
//public abstract class ShulkerBoxBlockMixin {
//    @Inject(at = @At("HEAD"), method = "setPlacedBy")//存储方块的附魔
//    private void init1(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo info) {
//        InjectHelper.onPlacedInject(world,itemStack,pos);
//    }
//}
