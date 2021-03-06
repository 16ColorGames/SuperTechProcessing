/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.compat.CraftTweaker;

import com.sixteencolorgames.supertechprocessing.compat.jei.roller.RollerJEIRecipe;
import com.sixteencolorgames.supertechprocessing.crafting.RollerManager;
import com.sixteencolorgames.supertechtweaks.crafting.RecipeIngredient;
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
public class AddRolling implements IUndoableAction {

    RollerJEIRecipe rec;
    private final ItemStack toStack;
    private final Object toObject;
    private final int time;

    AddRolling(ItemStack toStack, RecipeIngredient toObject, int time) {
        this.toStack = toStack;
        this.toObject = toObject;
        this.time = time;
        rec = new RollerJEIRecipe(toObject.getExamples(), toStack);
    }

    @Override
    public void undo() {
        RollerManager.instance().removeRolling(toObject);
        MineTweakerAPI.ijeiRecipeRegistry.removeRecipe(rec);
    }

    @Override
    public String describe() {
        return "Adding new rolling: ";
    }

    @Override
    public String describeUndo() {
        return "Removing new rolling: ";
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
        RollerManager.instance().addRolling(toObject, toStack, time);
        MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(rec);
    }

}
