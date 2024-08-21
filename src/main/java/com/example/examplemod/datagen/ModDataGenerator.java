package com.example.examplemod.datagen;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.datagen.item.enchantment.ModEnchantments;
import com.example.examplemod.datagen.item.model.FluidsItemModels;
import com.example.examplemod.datagen.item.tags.ModBlockTagsProvider;
import com.example.examplemod.datagen.item.tags.ModtemTagsProvider;
import com.example.examplemod.datagen.lang.ModLangGen;
import com.ibm.icu.util.Output;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.data.tags.VanillaBlockTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.spongepowered.asm.util.ObfuscationUtil;

import java.util.concurrent.CompletableFuture;
// 用于注册数据生成器的类，该类通过Forge的EventBusSubscriber注解自动注册到MOD总线上
@EventBusSubscriber(modid = ExampleMod.MODID,bus = EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {
    // 订阅GatherDataEvent事件，当数据收集事件触发时执行该方法
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        // lang us
        generator.addProvider(event.includeClient(),new ModLangGen(output,"en_us"));
        // item model
        generator.addProvider(event.includeServer(),new FluidsItemModels(output,existingFileHelper));

        // 为数据生成器添加一个自定义的数据包内置条目提供者
        generator.addProvider(event.includeServer(),new ModDatapackBuiltinEntriesProvider(output,lookupProvider));

        TagsProvider<Block> tagsprovider4 = generator.addProvider(event.includeServer(),new ModBlockTagsProvider(output,lookupProvider,ExampleMod.MODID,existingFileHelper));
        //
        generator.addProvider(event.includeServer(),new ModtemTagsProvider(output,lookupProvider,tagsprovider4.contentsGetter(),ExampleMod.MODID,existingFileHelper));
    }
}

