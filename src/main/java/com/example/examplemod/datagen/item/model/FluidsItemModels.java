package com.example.examplemod.datagen.item.model;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.fluid.FluidResources;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class FluidsItemModels extends ItemModelProvider {

	public FluidsItemModels(PackOutput generator, ExistingFileHelper existingFileHelper) {
		super(generator, ExampleMod.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		for (FluidResources.FluidStuff fluid : FluidResources.fluidList) {
			//bucketModel(fluid.FLUID_BUCKET, fluid.name);
			itemWithModel(fluid.FLUID_BUCKET, "item/generated");
		}
	}

	public void itemWithModel(DeferredHolder<Item,? extends Item> registryObject, String model) {
		ResourceLocation id = registryObject.getId();
		ResourceLocation textureLocation =  ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "item/" + id.getPath());
		singleTexture(id.getPath(), ResourceLocation.parse(model), "layer0", textureLocation);
	}

}
