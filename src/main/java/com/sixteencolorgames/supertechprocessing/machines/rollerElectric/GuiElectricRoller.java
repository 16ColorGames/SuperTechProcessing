package com.sixteencolorgames.supertechprocessing.machines.rollerElectric;

import com.sixteencolorgames.supertechprocessing.SuperTechProcessingMod;
import com.sixteencolorgames.supertechtweaks.gui.GuiBaseElectric;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiElectricRoller extends GuiBaseElectric {

    public GuiElectricRoller(InventoryPlayer playerInv, IInventory furnaceInv) {
        super(new ContainerElectricRoller(playerInv, furnaceInv),
                new ResourceLocation(SuperTechProcessingMod.MODID, "textures/gui/rollerElectricGui.png"),
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
        return this.tile.getField(TileEntityElectricRoller.ENERGY);
    }

    @Override
   public int getMaxEnergy() {
        return this.tile.getField(TileEntityElectricRoller.MAX_ENERGY);
    }
}
