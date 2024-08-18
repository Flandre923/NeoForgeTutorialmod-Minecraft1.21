package com.example.examplemod.menu;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.block.blockentity.custome.WireBrushingMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModMenuTypes {
    public static DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, ExampleMod.MODID);

    public static final DeferredHolder<MenuType<?>,MenuType<WireBrushingMenu>> WIRE_BRUSHING_MENU =
            registerMenuType(WireBrushingMenu::new, "wire_brushing_menu");

    private static <T extends AbstractContainerMenu>
            DeferredHolder<MenuType<?>,MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name)
    {
        return MENU_TYPES.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}


