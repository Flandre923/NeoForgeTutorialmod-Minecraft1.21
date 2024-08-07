package com.example.examplemod.datagen.item.tags;

import com.example.examplemod.item.ModItem;
import com.example.examplemod.tags.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModtemTagsProvider extends ItemTagsProvider {
    public ModtemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModItemTags.MY_MINING_ENCHANTABLE).add(Items.DIAMOND_PICKAXE)
                .addTag(ItemTags.AXES);
        
        

        this.tag(ItemTags.CAT_FOOD)
                .add(ModItem.CHEESE_BERGER.get());
    }
}
