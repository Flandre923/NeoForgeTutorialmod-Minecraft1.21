package com.example.examplemod.mixinhelper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WeaponEnchantmentMixinHelper {

    // 创建一个静态 Map 来存储实体 UUID 和值
    private static final Map<UUID, Integer> reverseMap = new HashMap<>();

    // 在适当的时候将实体 UUID 和值添加到 Map 中
    public static void storeReverse(UUID entityUUID, int value) {
        reverseMap.put(entityUUID, value);
    }

    // 在需要时从 Map 中检索值
    public static int getReverse(UUID entityUUID) {
        return reverseMap.getOrDefault(entityUUID, 0); // 默认值为0，如果未找到实体 UUID
    }

    // 创建一个静态Map来存储实体UUID和值
    private static final Map<Integer, Integer> entityValueMap = new HashMap<>();

    // 在适当的时候将实体UUID和值添加到Map中
    public static void storeEntityValue(int entityID, int value) {
        entityValueMap.put(entityID, value);
    }
    // 在需要时从Map中检索值
    public static int getEntityValue(int entityID) {
        return entityValueMap.getOrDefault(entityID, 0); // 默认值为0，如果未找到实体UUID
    }


//
//    private static int value = 0; // 初始化值为0
//    private static Entity entity = null; // 初始化Entity为null
//
//    public static void setValue(int newValue) {
//        value = newValue;
//    }
//
//    public static int getValue() {
//        return value;
//    }
//
//    public static void setEntity(Entity newEntity) {
//        entity = newEntity;
//    }
//
//    public static Entity getEntity() {
//        return entity;
//    }
}
