package com.sixteencolorgames.supertechprocessing.gui;

import com.sixteencolorgames.supertechprocessing.SuperTechProcessingMod;
import com.sixteencolorgames.supertechprocessing.tileentities.TileEntityCoalExtruder;
import com.sixteencolorgames.supertechtweaks.gui.GuiBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiCoalExtruder extends GuiBase {

    public GuiCoalExtruder(InventoryPlayer playerInv, IInventory furnaceInv) {
        super(new ContainerCoalExtruder(playerInv, furnaceInv),
                new ResourceLocation(SuperTechProcessingMod.MODID, "textures/gui/extruderGui.png"),
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

        if (TileEntityCoalExtruder.isBurning(this.tile)) {
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);//Render flames
        }

        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);//Render progress bar
    }

    private int getCookProgressScaled(int pixels) {
        int i = this.tile.getField(2);
        int j = this.tile.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getBurnLeftScaled(int pixels) {
        int i = this.tile.getField(1);

        if (i == 0) {
            i = 200;
        }

        return this.tile.getField(0) * pixels / i;
    }
}
