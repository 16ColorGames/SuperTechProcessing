/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.machines.rollerElectric;

import com.sixteencolorgames.supertechprocessing.crafting.RollerManager;
import static com.sixteencolorgames.supertechprocessing.machines.rollerElectric.TileEntityElectricRoller.COOK_TIME;
import static com.sixteencolorgames.supertechprocessing.machines.rollerElectric.TileEntityElectricRoller.ENERGY;
import static com.sixteencolorgames.supertechprocessing.machines.rollerElectric.TileEntityElectricRoller.MAX_ENERGY;
import static com.sixteencolorgames.supertechprocessing.machines.rollerElectric.TileEntityElectricRoller.TOTAL_COOK_TIME;
import com.sixteencolorgames.supertechtweaks.gui.ContainerBase;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;

/**
 *
 * @author oa10712
 */
public class ContainerElectricRoller extends ContainerBase {

    private int cookEnergy;
    private int totalCookEnergy;
    private int currentStored;
    private int maxStored;

    public ContainerElectricRoller(InventoryPlayer playerInventory, IInventory extrudeInv) {
        super(playerInventory, extrudeInv);
        this.addSlotToContainer(new Slot(extrudeInv, TileEntityElectricRoller.INPUT_SLOT, 56, 35));
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, extrudeInv, TileEntityElectricRoller.OUTPUT_SLOT, 116, 35));

    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener icontainerlistener = (IContainerListener) this.listeners.get(i);

            if (cookEnergy != this.tileBase.getField(COOK_TIME)) {
                icontainerlistener.sendProgressBarUpdate(this, COOK_TIME, this.tileBase.getField(COOK_TIME));
            }

            if (totalCookEnergy != this.tileBase.getField(TOTAL_COOK_TIME)) {
                icontainerlistener.sendProgressBarUpdate(this, TOTAL_COOK_TIME, this.tileBase.getField(TOTAL_COOK_TIME));
            }

            if (currentStored != this.tileBase.getField(ENERGY)) {
                icontainerlistener.sendProgressBarUpdate(this, ENERGY, this.tileBase.getField(ENERGY));
            }

            if (maxStored != this.tileBase.getField(MAX_ENERGY)) {
                icontainerlistener.sendProgressBarUpdate(this, MAX_ENERGY, this.tileBase.getField(MAX_ENERGY));
            }
        }

        cookEnergy = this.tileBase.getField(COOK_TIME);
        totalCookEnergy = this.tileBase.getField(TOTAL_COOK_TIME);
        currentStored = this.tileBase.getField(ENERGY);
        maxStored = this.tileBase.getField(MAX_ENERGY);
    }

    /**
     * Take a stack from the specified inventory slot.
     */
    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 1) {//if we are removing from the output
                if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 0) {//if we are removing from anywhere but the input
                if (RollerManager.instance().getResult(itemstack1) == null) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return null;
                    }
                } else if (index >= 2 && index < 29) {
                    if (!this.mergeItemStack(itemstack1, 29, 38, false)) {
                        return null;
                    }
                } else if (index >= 29 && index < 38 && !this.mergeItemStack(itemstack1, 2, 29, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;
    }
}
