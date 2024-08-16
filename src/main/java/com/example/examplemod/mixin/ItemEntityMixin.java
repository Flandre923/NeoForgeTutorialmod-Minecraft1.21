package com.example.examplemod.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

// 参考实现AE
@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {

    public ItemEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }


    @Inject(at = @At("RETURN"), method = "tick")
    void handleEntityTransform(CallbackInfo info){
        if (this.isRemoved()){
            return;
        }

        var self = (ItemEntity) (Object) this;

        if (!self.getItem().is(Items.DIAMOND)){
            return;
        }
        final int j = Mth.floor(this.getX());
        final int i = Mth.floor((this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0D);
        final int k = Mth.floor(this.getZ());

        FluidState fluidState = this.level().getFluidState(new BlockPos(j,i,k));
        if (!fluidState.is(FluidTags.WATER)){
            return;
        }

        if (level().isClientSide){
            // 添加蜡烛的粒子特效

        }else{
            var level = this.level();

            var region = new AABB(this.getX() - 1, this.getY() - 1, this.getZ() - 1, this.getX() + 1,
                    this.getY() + 1, this.getZ() + 1);
            List<ItemEntity> itemEntities = level.getEntities(null, region).stream()
                    .filter(e -> e instanceof ItemEntity && !e.isRemoved()).map(e -> (ItemEntity) e).toList();

            List<ItemEntity> discardItem = new ArrayList<>();
            for (ItemEntity itemEntity : itemEntities) {
                // 遍历所有的实体将一个 红石的 和 钻石 和煤炭的物品实体添加进入
                if (itemEntity.getItem().is(Items.REDSTONE) || itemEntity.getItem().is(Items.COAL) || itemEntity.getItem().is(Items.DIAMOND)){
                    discardItem.add(itemEntity);
                }
            }

            // 如果discardItem 的长度小于3 直接发挥
            if (discardItem.size() < 3){
                return;
            }
            // 遍历discardItem物品，如果大于等于1则减少一个，如果小于等于0则移除
            for (ItemEntity itemEntity : discardItem) {
                if (itemEntity.getItem().getCount() > 0){
                    itemEntity.getItem().setCount(itemEntity.getItem().getCount() - 1);
                }else if (itemEntity.getItem().getCount() <= 0){
                    itemEntity.discard();
                }
            }
            // 生成一个下届合金锭
            final double x = Math.floor(this.getX()) + .25d + random.nextDouble() * .5;
            final double y = Math.floor(this.getY()) + .25d + random.nextDouble() * .5;
            final double z = Math.floor(this.getZ()) + .25d + random.nextDouble() * .5;
            final double xSpeed = random.nextDouble() * .25 - 0.125;
            final double ySpeed = random.nextDouble() * .25 - 0.125;
            final double zSpeed = random.nextDouble() * .25 - 0.125;
            final ItemEntity newEntity = new ItemEntity(level, x, y, z, Items.NETHERITE_INGOT.getDefaultInstance());
            newEntity.setDeltaMovement(xSpeed, ySpeed, zSpeed);
            level.addFreshEntity(newEntity);

        }
    }

}
