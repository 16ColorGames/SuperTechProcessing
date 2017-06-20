package com.sixteencolorgames.supertechprocessing.machines.extruderCoal;

import com.sixteencolorgames.supertechprocessing.crafting.ExtruderManager;
import com.sixteencolorgames.supertechprocessing.machines.extruderCoal.TileEntityCoalExtruder;
import static com.sixteencolorgames.supertechprocessing.machines.extruderCoal.TileEntityCoalExtruder.*;
import com.sixteencolorgames.supertechtweaks.gui.ContainerBase;
import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;


public class ContainerCoalExtruder extends ContainerBase {

    private int cookTime;
    private int totalCookTime;
    private int furnaceBurnTime;
    private int currentItemBurnTime;

    public ContainerCoalExtruder(InventoryPlayer playerInventory, IInventory extrudeInv) {
        super(playerInventory, extrudeInv);
        this.addSlotToContainer(new Slot(extrudeInv, 0, 56, 17));
        this.addSlotToContainer(new SlotFurnaceFuel(extrudeInv, 1, 56, 53));
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, extrudeInv, 2, 116, 35));

    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener icontainerlistener = (IContainerListener) this.listeners.get(i);

            if (this.cookTime != this.tileBase.getField(2)) {
                icontainerlistener.sendProgressBarUpdate(this, 2, this.tileBase.getField(2));
            }

            if (this.furnaceBurnTime != this.tileBase.getField(0)) {
                icontainerlistener.sendProgressBarUpdate(this, 0, this.tileBase.getField(0));
            }

            if (this.currentItemBurnTime != this.tileBase.getField(1)) {
                icontainerlistener.sendProgressBarUpdate(this, 1, this.tileBase.getField(1));
            }

            if (this.totalCookTime != this.tileBase.getField(3)) {
                icontainerlistener.sendProgressBarUpdate(this, 3, this.tileBase.getField(3));
            }
        }

        this.cookTime = this.tileBase.getField(2);
        this.furnaceBurnTime = this.tileBase.getField(0);
        this.currentItemBurnTime = this.tileBase.getField(1);
        this.totalCookTime = this.tileBase.getField(3);
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

            if (index == OUTPUT_SLOT) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != FUEL_SLOT && index != INPUT_SLOT) {
                if (ExtruderManager.instance().getResult(itemstack1) != null) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return null;
                    }
                } else if (TileEntityCoalExtruder.isItemFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return null;
                    }
                } else if (index >= 3 && index < 30) {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                        return null;
                    }
                } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
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
