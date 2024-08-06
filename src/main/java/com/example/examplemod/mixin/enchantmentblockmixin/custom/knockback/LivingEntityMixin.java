package com.example.examplemod.mixin.enchantmentblockmixin.custom.knockback;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Inject(at = @At("HEAD"), method = "tick")
	private void init(CallbackInfo ci) {
//
//		if(getDirectionVectorIfTouching(this,))
	}
	@Unique
	private static Vec3 nEOFORGE1_21$getDirectionVectorIfTouching(Entity entity, BlockPos blockPos) {
		// 获取实体的碰撞箱
		AABB entityBox = entity.getBoundingBox();
		// 创建代表方块的碰撞箱
		AABB blockBox = new AABB(blockPos.getX(), blockPos.getY(), blockPos.getZ(),
				blockPos.getX() + 1.0, blockPos.getY() + 1.0, blockPos.getZ() + 1.0);

		// 检查实体的碰撞箱是否与方块的碰撞箱相交
		if (entityBox.intersects(blockBox)) {
			// 获取方块碰撞箱的中心点
			Vec3 blockCenter = new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
			// 获取实体碰撞箱的中心点
			Vec3 entityCenter = entityBox.getCenter();

			// 计算从方块中心到实体中心的方向向量
			return entityCenter.subtract(blockCenter);
		} else {
			// 如果没有碰撞，返回 null
			return null;
		}
	}
}