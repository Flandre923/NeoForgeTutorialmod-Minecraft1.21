package com.example.examplemod.mixinhelper;

import com.example.examplemod.enchantmentblock.BlockEnchantmentStorage;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.Set;


public class InjectHelper {


    public static void onPlacedInject(Level world, ItemStack itemStack, BlockPos pos) {
        if (!world.isClientSide) {//只在服务端运行
            if (!Objects.equals(itemStack.getEnchantments(), new ListTag())) {//如果方块有附魔

//                ListTag enchantments = itemStack.getEnchantments(); //获取物品栈上的附魔信息列表
//                BlockEnchantmentStorage.addBlockEnchantment(pos.toImmutable(), enchantments);//储存信息
                addToList(itemStack,pos.immutable());
            }
        }
    }

    public static ListTag enchantmentsToNbtList(ItemStack itemStack,BlockPos currentPos){
        // 在这里对满足条件的方块进行处理
        ItemEnchantments itemEnchantments = itemStack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);

        ListTag enchantmentNbtList = new ListTag();
        Set<Object2IntMap.Entry<Holder<Enchantment>>> entries = itemEnchantments.entrySet();
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : entries) {
            Holder<Enchantment> key = entry.getKey();
            int intValue = entry.getIntValue();


            CompoundTag enchantmentNbt = new CompoundTag();
            enchantmentNbt.putString("id",String.valueOf(key.getKey()));
            enchantmentNbt.putInt("lvl",intValue);
            enchantmentNbtList.add(enchantmentNbt);

        }
        return enchantmentNbtList;
    }

    public static void  addToList(ItemStack itemStack,BlockPos currentPos){
        ListTag listTag = enchantmentsToNbtList(itemStack,currentPos);
        BlockEnchantmentStorage.addBlockEnchantment(currentPos, listTag);
    }
}
