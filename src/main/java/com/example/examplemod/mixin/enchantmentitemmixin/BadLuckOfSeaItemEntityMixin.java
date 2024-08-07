package com.example.examplemod.mixin.enchantmentitemmixin;

import com.example.examplemod.datagen.item.enchantment.ModEnchantments;
import com.example.examplemod.mixinhelper.InjectHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class BadLuckOfSeaItemEntityMixin extends Entity implements TraceableEntity {

	public BadLuckOfSeaItemEntityMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Shadow
	public abstract ItemStack getItem();


	@Inject(at = @At("HEAD"), method = "tick")
	private void init(CallbackInfo ci) {//实现丢出去的实体被水排斥
		ItemStack itemStack = this.getItem();
		Item item = itemStack.getItem();
		Level world = this.level();
		BlockPos blockPos = this.blockPosition();
		FluidState fluidState = world.getFluidState(blockPos);
		if (fluidState.is(FluidTags.WATER) & InjectHelper.getEnchantmentLevel(itemStack, ModEnchantments.BAD_LUCK_OF_SEA) > 0) {

			BlockPos closestNonLiquidBlockPos = null;
			double closestDistanceSq = Double.MAX_VALUE; // 初始设置为最大值

			for (int xOffset = -20; xOffset <= 19; xOffset++) {
				for (int zOffset = -20; zOffset <= 19; zOffset++) {
					BlockPos currentPos = blockPos.offset(xOffset, 0, zOffset);
					FluidState fluidState1 = world.getFluidState(currentPos);

					// 检查当前方块是否不是液体方块
					if (!fluidState1.is(FluidTags.WATER)) {
						// 计算当前方块与玩家的距离的平方
						double distanceSq = this.distanceToSqr(Vec3.atCenterOf(currentPos));

						// 如果当前方块更近，则更新最近的非液体方块信息
						if (distanceSq < closestDistanceSq) {
							closestDistanceSq = distanceSq;
							closestNonLiquidBlockPos = currentPos;
						}
					}
				}
			}

			if (closestNonLiquidBlockPos != null) {
				// 发送信息给玩家
				//this.sendMessage(Text.literal("检测到最近的固体方块可弹射"));

				// 计算方向向量
				Vec3 direction = Vec3.atCenterOf(closestNonLiquidBlockPos).subtract(this.position()).normalize();

				double speed = 0.5; // 设定速度大小（可以根据需要调整）

				// 计算最终的速度向量
				Vec3 velocity = direction.scale(speed);

				this.push(0, 1, 0);
				this.addDeltaMovement(velocity); // 应用速度
			}
		}
	}
}