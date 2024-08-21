package com.example.examplemod.fluid;

import java.awt.Color;

import com.example.examplemod.ExampleMod;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import org.joml.Vector3f;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;

public class ModBaseFluidType extends FluidType {

	public final ResourceLocation RENDER_OVERLAY;
	public final ResourceLocation TEXTURE_STILL;
	public final ResourceLocation TEXTURE_FLOW;
	public final ResourceLocation TEXTURE_OVERLAY;
	public final Vector3f FOG_COLOR;
	public final float fogStart;
	public final float fogEnd;

	public final ModClientFluidType CLIENT_FLUID_TYPE;


	public ModBaseFluidType(Properties properties, FunkyFluidInfo info) {
		super(properties);
		RENDER_OVERLAY = ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "textures/overlay/" + info.name + ".png");
		TEXTURE_STILL = ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "block/fluid/" + info.name + "_still");
		TEXTURE_FLOW =  ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "block/fluid/" + info.name + "_flow");
		TEXTURE_OVERLAY = ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "block/fluid/" + info.name + "_overlay");
		Color colorObject = new Color(info.color);
		FOG_COLOR = new Vector3f(colorObject.getRed()/255F, colorObject.getGreen()/255F, colorObject.getBlue()/255F);
		fogStart = info.fogStart;
		fogEnd = info.fogEnd;

		CLIENT_FLUID_TYPE = new ModClientFluidType(RENDER_OVERLAY, TEXTURE_STILL, TEXTURE_FLOW, TEXTURE_OVERLAY, FOG_COLOR, fogStart, fogEnd);
	}

	public static class FunkyFluidInfo {

		public String name;
		public int color;
		public float fogStart;
		public float fogEnd;

		public boolean isTranslucent;

		public FunkyFluidInfo(String name, int color, float fogStart, float fogEnd,boolean isTranslucent) {
			this.name = name;
			this.color = color;
			this.fogStart = fogStart;
			this.fogEnd = fogEnd;
			this.isTranslucent  = isTranslucent;
		}
	}

	// 此处可以重写你自己的客户端流体类型，并实现IClientFluidTypeExtensions接口
	public IClientFluidTypeExtensions getClientFluidType() {
		return CLIENT_FLUID_TYPE;
	}


	public static class ModClientFluidType implements  IClientFluidTypeExtensions{

		public final ResourceLocation RENDER_OVERLAY;
		public final ResourceLocation TEXTURE_STILL;
		public final ResourceLocation TEXTURE_FLOW;
		public final ResourceLocation TEXTURE_OVERLAY;
		public final Vector3f FOG_COLOR;
		public final float fogStart;
		public final float fogEnd;

        public ModClientFluidType(ResourceLocation renderOverlay, ResourceLocation textureStill, ResourceLocation textureFlow, ResourceLocation textureOverlay, Vector3f fogColor, float fogStart, float fogEnd) {
            RENDER_OVERLAY = renderOverlay;
            TEXTURE_STILL = textureStill;
            TEXTURE_FLOW = textureFlow;
            TEXTURE_OVERLAY = textureOverlay;
            FOG_COLOR = fogColor;
            this.fogStart = fogStart;
            this.fogEnd = fogEnd;
        }


        @Override
		public ResourceLocation getStillTexture() {
			return TEXTURE_STILL;
		}

		@Override
		public ResourceLocation getFlowingTexture() {
			return TEXTURE_FLOW;
		}

		@Override
		public ResourceLocation getOverlayTexture() {
			return TEXTURE_OVERLAY;
		}

		@Override
		public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
			return RENDER_OVERLAY;
		}

		@Override
		public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
			return FOG_COLOR;
		}

		@Override
		public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
			RenderSystem.setShaderFogStart(fogStart);
			RenderSystem.setShaderFogEnd(fogEnd);
		}
	}
}
