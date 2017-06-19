package com.sixteencolorgames.supertechprocessing.gui;

import com.sixteencolorgames.supertechprocessing.SuperTechProcessingMod;
import com.sixteencolorgames.supertechprocessing.tileentities.TileEntityElectricExtruder;
import com.sixteencolorgames.supertechtweaks.gui.GuiBaseElectric;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiElectricExtruder extends GuiBaseElectric {

    public GuiElectricExtruder(InventoryPlayer playerInv, IInventory furnaceInv) {
        super(new ContainerElectricExtruder(playerInv, furnaceInv),
                new ResourceLocation(SuperTechProcessingMod.MODID, "textures/gui/extruderElectricGui.png"),
                playerInv,
                furnaceInv);
    }

    /**
     * Args : renderPartialTicks, mouseX, mouseY
     */
    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(i + 76, j + 34, 176, 14, l + 1, 16);
    }

    private int getCookProgressScaled(int pixels) {
        int i = this.tile.getField(2);
        int j = this.tile.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    @Override
   public int getEnergyLeftScaled(int pixels) {
        return (getEnergy() * pixels) / (getMaxEnergy());
    }

    @Override
   public int getEnergy() {
        return this.tile.getField(TileEntityElectricExtruder.ENERGY);
    }

    @Override
   public int getMaxEnergy() {
        return this.tile.getField(TileEntityElectricExtruder.MAX_ENERGY);
    }
}
