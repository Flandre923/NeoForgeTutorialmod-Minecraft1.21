package com.example.examplemod.mixin.enchantmentitemmixin.fangsheng;

import com.example.examplemod.datagen.item.enchantment.ModEnchantments;
import com.example.examplemod.mixinhelper.InjectHelper;
import com.example.examplemod.network.packet.C2S.FuC2SPacket;
import com.ibm.icu.impl.breakiter.DictionaryBreakEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements TraceableEntity {
	public ItemEntityMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	//	@Unique
//	public int returnTimer;
	@Shadow
	public abstract ItemStack getItem();

//	@Shadow @Nullable private Entity thrower;//通过Q键投掷出去的

	@Shadow @Nullable
	public abstract Entity getOwner();
//	@Unique
//	@Nullable
//	private Entity fuMaster;
//	@Unique
//	private Vec3d lastVelocity;
	@Unique
	private int cd=0;


	@Shadow public abstract void setPickUpDelay(int pickupDelay);




//	@Override
//	protected void onBlockCollision(BlockState state) {
//		super.onBlockCollision(state);
//		if(state.isIn(BlockTags.PICKAXE_MINEABLE)){
//			dropStack(state.getBlock().asItem().getDefaultStack());
//		}
//	}
	@Inject(at = @At("TAIL"), method = "setThrower")
	private void init(Entity thrower, CallbackInfo ci) {
		int i = InjectHelper.getEnchantmentLevel(this.getItem(), ModEnchantments.FANGSHENG);
		ItemStack itemStack = this.getItem();
		if(itemStack.getItem() instanceof PickaxeItem && i>0) {
			this.setPickUpDelay(200);
		}
		cd = 0;
	}
	@Inject(at = @At("TAIL"), method = "tick")
	private void init1(CallbackInfo info) {

		int i = InjectHelper.getEnchantmentLevel(this.getItem(), ModEnchantments.FANGSHENG);
		ItemStack itemStack = this.getItem();

		if(itemStack.getItem() instanceof PickaxeItem && i>0) {

			if(this.onGround()) {//如果在地上
				// 随机一个方向
				double angle = random.nextDouble() * 2 * Math.PI; // 生成一个随机角度
				double x = Math.cos(angle); // 计算 x 分量
				double z = Math.sin(angle); // 计算 z 分量
				// 创建 Vec3d 对象表示方向向量
				Vec3 direction = new Vec3(x, 0.0, z);
				// 设置水平速度
				double horizontalSpeed = 0.3f; // 你想要施加的水平速度
				if(level().isClientSide) {
					mafishmod$ItemEntityC2S(direction);
				}
				direction = FuC2SPacket.getDirection();
				if(direction!=null) {
					this.setDeltaMovement(new Vec3(direction.x * horizontalSpeed, 0.4, direction.z * horizontalSpeed));
				}
			}

			Iterable<VoxelShape> blockCollisions = this.level().getBlockCollisions(this, this.getBoundingBox().inflate(0.05));
			List<BlockPos> hitBlockPos = new ArrayList<>();
			// 遍历 blockCollisions 中的每个 VoxelShapeE
			for (VoxelShape voxelShape : blockCollisions) {
				// 获取与当前 VoxelShape 相关的方块位置
				voxelShape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
					// 计算方块位置，使用 Math.round() 进行四舍五入
					BlockPos pos = new BlockPos((int) Math.floor((minX + maxX) / 2.0),
                            (int) Math.floor((minY + maxY) / 2.0),
                            (int) Math.floor((minZ + maxZ) / 2.0));
					hitBlockPos.add(pos);
				});
			}
			for (BlockPos pos : hitBlockPos) {
				// 这里可以对每个方块位置进行你需要的操作
				BlockState blockState = this.level().getBlockState(pos);
//				System.out.println("Hit Block Position: " + pos+ "Hit Block blockState: "+blockState);
				if(blockState.is(BlockTags.MINEABLE_WITH_PICKAXE) && cd == 0){
//					System.out.println(3);
					this.level().destroyBlock(pos,true);
					cd = 10;
				}
			}
			if(cd > 0) {
				cd = cd - 1;
			}
		}
	}
	@Unique
	@OnlyIn(Dist.CLIENT)
	private void mafishmod$ItemEntityC2S(Vec3 direction){
//		PacketByteBuf buf = PacketByteBufs.create();//C2S
//		buf.writeInt(1);
//		buf.writeVec3d(direction);
//		ClientPlayNetworking.send(ModMessages.FU_ID, buf);
		PacketDistributor.sendToServer(new FuC2SPacket(1,null,direction));
	}
}