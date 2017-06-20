/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author oa10712
 */
public class ExtruderManager {

    static final ExtruderManager INSTANCE = new ExtruderManager();
    HashMap<List<ItemStack>, ItemStack> extrudeList = new HashMap();
    HashMap<List<ItemStack>, Integer> timeList = new HashMap();

    public static ExtruderManager instance() {
        return INSTANCE;
    }

    public boolean addExtrusion(Object in, ItemStack out, int time) {
        List<ItemStack> ores = null;
        if (in instanceof ItemStack) {
            ores = new ArrayList();
            ores.add((ItemStack) in);
        }
        if (in instanceof String) {
            ores = OreDictionary.getOres((String) in);
        }
        if (ores != null) {
            extrudeList.put(ores, out);
            timeList.put(ores, time);
            return true;
        }
        return false;
    }

    public ItemStack getResult(ItemStack in) {
        for (Map.Entry<List<ItemStack>, ItemStack> entry : this.extrudeList.entrySet()) {
            for (ItemStack check : entry.getKey()) {
                if (this.compareItemStacks(in, check)) {
                    return (ItemStack) entry.getValue();
                }
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
        return timeList.getOrDefault(stack, 200);
    }

    public Map<List<ItemStack>, ItemStack> getList() {
        return extrudeList;
    }
}
