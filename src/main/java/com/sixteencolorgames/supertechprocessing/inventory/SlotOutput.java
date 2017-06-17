/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * SlotOutput is a slot that will not accept any items
 *
 * @author oa10712
 */
public class SlotOutput extends Slot {

    public SlotOutput(IInventory inventoryIn, int index, int xPosition,
            int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    // if this function returns false, the player won't be able to insert
    // the given item into this slot
    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }
}
