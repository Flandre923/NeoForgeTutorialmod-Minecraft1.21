package com.example.examplemod.event;

import com.example.examplemod.item.ModItem;
import com.example.examplemod.item.enchantment.ModEnchantmentHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;

@EventBusSubscriber
public class PlayerServerEvent {

    @SubscribeEvent
    public static void onPlayerBreakBlock(BlockEvent.BreakEvent event){

        Player player = event.getPlayer();
        if(event.getLevel() instanceof ServerLevel serverLevel){
            ItemStack mainHandItem = player.getMainHandItem();
            if (ModEnchantmentHelper.hasAutoSmeltEnchantment(serverLevel,mainHandItem)){
                BlockPos pos = event.getPos();
                BlockState blockState = serverLevel.getBlockState(pos);
                List<ItemStack> drops = blockState.getBlock().getDrops(blockState, serverLevel, pos, null);
                for (ItemStack drop : drops) {
                    if (drop.is(Items.RAW_IRON)){
                        blockState.getBlock().popResource(serverLevel,pos,new ItemStack(Items.IRON_BLOCK,1));
                    }else{
                        blockState.getBlock().popResource(serverLevel,pos,drop);
                    }
                }

            }
        }
    }



    @SubscribeEvent
    public static void onEntityAttack(AttackEntityEvent event){
        Player player = event.getEntity();
        Entity target = event.getTarget();
        Level world = player.level();
        if(player.isHolding(ModItem.LIGHTNING_ITEM.get()) && !world.isClientSide){
            BlockPos blockPos = target.blockPosition();
            LightningBolt lightningEntity = EntityType.LIGHTNING_BOLT.create(target.level());
            if (lightningEntity != null) {
                lightningEntity.moveTo(Vec3.atBottomCenterOf(blockPos));
                lightningEntity.setCause(player instanceof ServerPlayer ? (ServerPlayer) player : null );
                target.level().addFreshEntity(lightningEntity);
                SoundEvent soundEvent = SoundEvents.LIGHTNING_BOLT_IMPACT;
                player.playSound(soundEvent, 5, 1.0F);
            }
        }

        if (target instanceof Chicken && !world.isClientSide){
            player.sendSystemMessage(Component.literal("哎呦你干嘛"));
        }

    }


}
