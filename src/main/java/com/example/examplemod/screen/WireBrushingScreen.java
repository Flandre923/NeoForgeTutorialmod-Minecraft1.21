package com.example.examplemod.screen;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.block.blockentity.custome.WireBrushingMenu;
import com.example.examplemod.screen.renderer.FluidTankRenderer;
import com.example.examplemod.util.MouseUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;

import java.util.Optional;

public class WireBrushingScreen extends AbstractContainerScreen<WireBrushingMenu> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "textures/gui/gem_infusing_station_gui.png");
//        private EnergyInfoArea energyInfoArea;
    private FluidTankRenderer renderer;


    public WireBrushingScreen(WireBrushingMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        assignEnergyInfoArea();
        assignFluidRenderer();
    }

    private void assignFluidRenderer() {
        renderer = new FluidTankRenderer(64000, true, 16, 61);
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

//        energyInfoArea = new EnergyInfoArea(x + 156, y + 13, menu.blockEntity.getEnergyStorage());
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        super.renderLabels(guiGraphics, pMouseX, pMouseY);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltips(guiGraphics, pMouseX, pMouseY, x, y);
        renderFluidAreaTooltips(guiGraphics, pMouseX, pMouseY, x, y);

    }


    private void renderFluidAreaTooltips(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if (isMouseAboveArea(pMouseX, pMouseY, x, y, 55, 15)) {
            guiGraphics.renderTooltip(this.font,renderer.getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderEnergyAreaTooltips(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 156, 13, 8, 64)) {
//            guiGraphics.renderTooltip(this.font,energyInfoArea.getTooltips(), Optional.empty(),pMouseX-x,pMouseY-y);
        }
    }
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE,x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
//        energyInfoArea.draw(guiGraphics);
        renderer.render(guiGraphics, x + 55, y + 15, menu.getFluidStack());
    }


    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }

    private void renderProgressArrow(GuiGraphics pGuiGraphics, int x, int y) {
//        if(menu.isCrafting()) {
//            pGuiGraphics.blit(TEXTURE,x + 105, y + 33, 176, 0, 8, menu.getScaledProgress());
//        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBg(guiGraphics, partialTick, mouseX, mouseY);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}

