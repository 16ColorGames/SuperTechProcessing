/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.inventory;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author oa10712
 */
public class SlotMultiOreInput extends Slot {

    private final String subOre;

    public SlotMultiOreInput(IInventory inventoryIn, int index, int xPosition, int yPosition, String subOre) {
        super(inventoryIn, index, xPosition, yPosition);
        this.subOre = subOre;
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        for (String ore : OreDictionary.getOreNames()) {
            if (ore.contains(subOre)) {
                for (ItemStack oreStack : OreDictionary.getOres(ore)) {
                    if (oreStack.getItem() == stack.getItem() && oreStack.getMetadata() == stack.getMetadata()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
