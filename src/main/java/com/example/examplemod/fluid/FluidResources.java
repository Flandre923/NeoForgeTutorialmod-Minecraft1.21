package com.example.examplemod.fluid;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


public class FluidResources {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, ExampleMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, ExampleMod.MODID);
    public static final DeferredRegister<FluidType> FLUIDTYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, ExampleMod.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, ExampleMod.MODID);

    public static List<FluidStuff> fluidList = new ArrayList<FluidStuff>();

    public static FluidStuff addFluid(String localizedName, ModBaseFluidType.FunkyFluidInfo info, Block.Properties properties, BiFunction<FluidType.Properties, ModBaseFluidType.FunkyFluidInfo, FluidType> type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, Function<BaseFlowingFluid.Properties, BaseFlowingFluid.Source> source, Function<BaseFlowingFluid.Properties, BaseFlowingFluid.Flowing> flowing, @Nullable Consumer<BaseFlowingFluid.Properties> fluidProperties, FluidType.Properties prop) {
        FluidStuff fluid = new FluidStuff(info.name, localizedName, info.color,info.isTranslucent, type.apply(prop, info), block, fluidProperties, source, flowing, properties);
        fluidList.add(fluid);
        return fluid;
    }

    public static FluidStuff addFluid(String localizedName, ModBaseFluidType.FunkyFluidInfo info, Block.Properties properties, BiFunction<FluidType.Properties, ModBaseFluidType.FunkyFluidInfo, FluidType> type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, @Nullable Consumer<BaseFlowingFluid.Properties> fluidProperties, FluidType.Properties prop) {
        return addFluid(localizedName, info, properties, type, block, BaseFlowingFluid.Source::new, BaseFlowingFluid.Flowing::new, fluidProperties, prop);
    }

    public static void register(IEventBus modEventBus,DeferredHolder<CreativeModeTab, CreativeModeTab> creativeTab) {
        FluidResources.register(modEventBus);
        FluidEvent.init(creativeTab.getKey());
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        FLUIDTYPES.register(modEventBus);
        FLUIDS.register(modEventBus);

    }

    public static FluidStuff register(Supplier<FluidStuff> fluidStuffSupplier){
        return fluidStuffSupplier.get();
    }

    public static class FluidStuff {

        public final BaseFlowingFluid.Properties PROPERTIES;

        public final Supplier<BaseFlowingFluid.Source> FLUID;
        public final Supplier<BaseFlowingFluid.Flowing> FLUID_FLOW;
        public final Supplier<FluidType> TYPE;

        public final Supplier<LiquidBlock> FLUID_BLOCK;

        public final DeferredHolder<Item,BucketItem> FLUID_BUCKET;

        public final String name;
        public final String localizedName;
        public final int color;
        public final boolean isTranslucent;


        public FluidStuff(String name, String localizedName, int color,boolean isTranslucent, FluidType type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, @Nullable Consumer<BaseFlowingFluid.Properties> fluidProperties, Function<BaseFlowingFluid.Properties, BaseFlowingFluid.Source> source, Function<BaseFlowingFluid.Properties, BaseFlowingFluid.Flowing> flowing, Block.Properties properties) {
            this.name = name;
            this.localizedName = localizedName;
            this.color = color;
            this.isTranslucent = isTranslucent;

            FLUID = FLUIDS.register(name, () -> source.apply(getFluidProperties()));
            FLUID_FLOW = FLUIDS.register("flowing_" + name, () -> flowing.apply(getFluidProperties()));
            TYPE = FLUIDTYPES.register(name, () -> type);

            PROPERTIES = new BaseFlowingFluid.Properties(TYPE, FLUID, FLUID_FLOW);
            if (fluidProperties != null)
                fluidProperties.accept(PROPERTIES);

            FLUID_BLOCK = BLOCKS.register(name + "_block", () -> block.apply(FLUID, properties.lightLevel((state) -> { return type.getLightLevel(); }).randomTicks().strength(100.0F).noLootTable()));
            FLUID_BUCKET = ITEMS.register(name + "_bucket", () -> new BucketItem(FLUID.get(), new BucketItem.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

            PROPERTIES.bucket(FLUID_BUCKET).block(FLUID_BLOCK);
        }

        public BaseFlowingFluid.Properties getFluidProperties() {
            return PROPERTIES;
        }
    }
}
