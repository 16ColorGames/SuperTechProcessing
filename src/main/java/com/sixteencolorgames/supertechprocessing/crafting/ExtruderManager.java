/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.crafting;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.item.ItemStack;

/**
 *
 * @author oa10712
 */
public class ExtruderManager {

    static final ExtruderManager INSTANCE = new ExtruderManager();
    HashMap<ItemStack, ItemStack> extrudeList = new HashMap();
    HashMap<ItemStack, Integer> timeList = new HashMap();

    public static ExtruderManager instance() {
        return INSTANCE;
    }

    public boolean addExtrusion(ItemStack in, ItemStack out, int time) {
        if (extrudeList.containsKey(in)) {
            return false;
        }
        extrudeList.put(in, out);
        timeList.put(in, time);
        return true;
    }

    public ItemStack getResult(ItemStack in) {
        for (Map.Entry<ItemStack, ItemStack> entry : this.extrudeList.entrySet()) {
            if (this.compareItemStacks(in, (ItemStack) entry.getKey())) {
                return (ItemStack) entry.getValue();
            }
        }
        return null;
    }

    /**
     * Compares two itemstacks to ensure that they are the same. This checks
     * both the item and the metadata of the item.
     */
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    public int getCookTime(ItemStack stack) {
        return timeList.getOrDefault(stack, 160);
    }

    public Map<ItemStack, ItemStack> getList() {
        return extrudeList;
    }
}
