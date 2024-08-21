package com.example.examplemod.fluid;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;

public class ModFluids {
    public static FluidResources.FluidStuff OOBLECK ;

    public static void init(){
        OOBLECK = FluidResources.register( ()-> FluidResources.addFluid("Oobleck", new ModBaseFluidType.FunkyFluidInfo("oobleck", 0xE8F3F4, 0.1F, 1.5F,true), Block.Properties.of().mapColor(MapColor.WOOL).noCollission().replaceable().liquid().pushReaction(PushReaction.DESTROY).noLootTable(), ModBaseFluidType::new, (supplier, properties) -> new LiquidBlock(supplier.get(),properties),
                prop -> prop.explosionResistance(1000F).tickRate(20),
                FluidType.Properties.create()
                        .canExtinguish(true)
                        .supportsBoating(true)
                        .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                        .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                        .canHydrate(true)
                        .viscosity(3000)
                        .motionScale(0.007D)));
    }
}
