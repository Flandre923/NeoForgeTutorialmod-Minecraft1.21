package com.example.examplemod.event;

import com.example.examplemod.item.enchantment.ModEnchantmentHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.apache.logging.log4j.core.jmx.Server;

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
}
