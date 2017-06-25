/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.compat.CraftTweaker;

import com.sixteencolorgames.supertechprocessing.compat.jei.extruder.ExtruderJEIRecipe;
import com.sixteencolorgames.supertechprocessing.crafting.ExtruderManager;
import java.util.ArrayList;
import java.util.List;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author oa10712
 */
public class AddExtrusion implements IUndoableAction {

    private final ItemStack toStack;
    private final Object toObject;
    private final int time;

    AddExtrusion(ItemStack toStack, Object toObject, int time) {
        this.toStack = toStack;
        this.toObject = toObject;
        this.time = time;
    }

    @Override
    public void undo() {
        ExtruderManager.instance().removeExtrusion(toObject);
    }

    @Override
    public String describe() {
        return "Adding new extrusion: ";
    }

    @Override
    public String describeUndo() {
        return "Removing new extrusion: ";
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public void apply() {
        ExtruderManager.instance().addExtrusion(toObject, toStack, time);
        List<ItemStack> ores = null;
        if (toObject instanceof ItemStack) {
            ores = new ArrayList();
            ores.add((ItemStack) toObject);
        }
        if (toObject instanceof String) {
            ores = OreDictionary.getOres((String) toObject);
        }
        ExtruderJEIRecipe rec = new ExtruderJEIRecipe(ores, toStack);
        MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(rec);
    }

}
