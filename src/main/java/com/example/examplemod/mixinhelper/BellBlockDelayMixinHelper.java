package com.example.examplemod.mixinhelper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BellBlockEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BellBlockDelayMixinHelper {


    // 创建一个静态Map来存储实体ID和值
    public static final Map<BlockPos, Integer> HitCoolDownMap = new ConcurrentHashMap<>();

    // 在适当的时候将实体ID和值添加到Map中
    public static void storeHitCoolDown(BlockPos blockPos, int value) {
        HitCoolDownMap.put(blockPos, value);
    }
    // 在需要时从Map中检索值
    public static int getHitCoolDown(BlockPos blockPos) {
        return HitCoolDownMap.getOrDefault(blockPos, -1); // 默认值为-1
    }

    // 创建一个静态Map来存储实体ID和值
    public static final Map<BlockPos, Direction> DirectionMap = new ConcurrentHashMap<>();

    // 在适当的时候将实体ID和值添加到Map中
    public static void storeDirection(BlockPos blockPos, Direction value) {
        DirectionMap.put(blockPos, value);
    }
    // 在需要时从Map中检索值
    public static Direction getDirection(BlockPos blockPos) {
        return DirectionMap.getOrDefault(blockPos, Direction.NORTH); // 默认值为NORTH
    }

    // 创建一个静态Map来存储实体ID和值
    public static final Map<BlockPos, BellBlockEntity> BellBlockEntityMap = new ConcurrentHashMap<>();

    // 在适当的时候将实体ID和值添加到Map中
    public static void storeBellBlockEntity(BlockPos blockPos, BellBlockEntity value) {
        BellBlockEntityMap.put(blockPos, value);
    }
    // 在需要时从Map中检索值
    public static BellBlockEntity getBellBlockEntity(BlockPos blockPos) {
        return BellBlockEntityMap.getOrDefault(blockPos, null); // 默认值为null
    }
}
