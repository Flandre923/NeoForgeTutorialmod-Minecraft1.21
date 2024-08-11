package com.example.examplemod.item.custom;

import com.example.examplemod.item.component.ModComponents;
import com.example.examplemod.item.component.custom.MyCustomData;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.logging.Logger;

public class ExampleComponent extends Item {

    public ExampleComponent() {
        super(new Properties().component(ModComponents.MY_CUSTOM_DATA,new MyCustomData(123,"123",123)));
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        DataComponentMap components = this.components();
        MyCustomData myCustomData = components.get(ModComponents.MY_CUSTOM_DATA.get());

        Logger logger = Logger.getLogger("ExampleComponent");

        if (myCustomData != null) {
            int  color = myCustomData.color();
            String name = myCustomData.name();
            int nutrition = myCustomData.nutrition();

            // 检查 color 和 name 是否为 null
            // 假设 logger.warning 只接受一个 String 参数
            String message = "Color: " + color + ", Name: " + name + ", Nutrition: " + nutrition;
            logger.warning(message);
        } else {
            // 如果 myCustomData 为 null，则记录错误信息
            logger.warning("myCustomData is null");
        }


        return super.use(level, player, usedHand);
    }
}
