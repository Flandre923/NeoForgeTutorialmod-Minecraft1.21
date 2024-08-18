package com.example.examplemod.block;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.block.custome.WireBrushingBlock;
import com.example.examplemod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.print.DocFlavor;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ExampleMod.MODID);


    public static DeferredBlock<WireBrushingBlock> WIRE_BRUSHING_BLOCK = registerBlockAndItem("wire_brushing_block", ()-> new WireBrushingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));


    public static <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Supplier<T> Block){
        DeferredBlock<T> block = BLOCKS.register(name, Block);
        ModItems.ITEMS.register(name,() -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
