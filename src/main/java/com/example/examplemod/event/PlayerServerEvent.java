package com.example.examplemod.event;

import com.example.examplemod.datagen.item.enchantment.ModEnchantments;
import com.example.examplemod.item.ModItem;
import com.example.examplemod.item.enchantment.ModEnchantmentHelper;
import com.example.examplemod.mixinhelper.BellBlockDelayMixinHelper;
import com.example.examplemod.network.packet.S2C.NeverGonnaS2CPacket;
import com.jcraft.jogg.Packet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

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



    @SubscribeEvent
    public static void onPlayerAttackBlock(PlayerInteractEvent.LeftClickBlock event){
        Player player = event.getEntity();
        BlockPos useBlock = event.getPos();
        BlockState state = player.level().getBlockState(useBlock);

        InteractionHand hand = player.getUsedItemHand();
        ItemStack itemStack = player.getItemInHand(hand);
        int i = getEnchantmentLevel(itemStack,ModEnchantments.NEVER_GONNA);//如果手上的物品有被附魔 “你被骗了”

        if(i>0 && (state.is(BlockTags.DIAMOND_ORES)) && !player.level().isClientSide){
//            PacketByteBuf buf = PacketByteBufs.create();//传输到client端
//            ServerPlayNetworking.send((ServerPlayerEntity) player, ModMessages.NEVER_GONNA_ID, buf);
            PacketDistributor.sendToAllPlayers(new NeverGonnaS2CPacket());
//            //检测pos边上是不是有钻石掉落物，有的话替换成煤炭
            startDelayedOperation(player.level(),player,useBlock);
        }

    }

    private static int getEnchantmentLevel(ItemStack item, ResourceKey<Enchantment> enchantmentResourceKey){
        ItemEnchantments itemEnchantments = item.get(DataComponents.ENCHANTMENTS);
        if (itemEnchantments == null) {
            return -1;
        }
        Optional<Object2IntMap.Entry<Holder<Enchantment>>> levelOptional = itemEnchantments.entrySet().stream().filter(int2Enchatment -> int2Enchatment.getKey().is(enchantmentResourceKey)).findFirst();
        return levelOptional.map(Object2IntMap.Entry::getIntValue).orElse(-1);
    }

    public static void replaceDiamondsWithCoal(Level world, BlockPos pos) {
        // 创建一个立方体区域，以给定坐标为中心，半径为1
        AABB box = new AABB(pos).inflate(1.0);

        // 获取指定区域内的所有 ItemEntity
        List<ItemEntity> itemEntities = world.getEntities(EntityType.ITEM, box, itemEntity -> {
            // 检查实体持有的物品是否为钻石
            return itemEntity.getItem().getItem() == Items.DIAMOND;
        });

        // 遍历所有 ItemEntity
        for (ItemEntity itemEntity : itemEntities) {
            // 替换成煤炭
            itemEntity.setItem(new ItemStack(Items.COAL));
        }
    }
    private static void startDelayedOperation(Level world, Player player, BlockPos pos) {
        if (world.getServer() != null) {
            world.getServer().execute(() -> {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        replaceDiamondsWithCoal(world,pos);
                    }
                }, 200); // 延迟0.2秒执行，单位为毫秒
            });
        }
    }


}
