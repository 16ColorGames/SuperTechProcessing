/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.compat.CraftTweaker;

import com.sixteencolorgames.supertechprocessing.compat.jei.mechanicalAssembler.MechanicalAssemblerJEIRecipe;
import com.sixteencolorgames.supertechprocessing.crafting.MechanicalAssemblerManager;
import com.sixteencolorgames.supertechprocessing.crafting.MechanicalAssemblerRecipe;
import com.sixteencolorgames.supertechtweaks.crafting.RecipeIngredient;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.item.ItemStack;

/**
 *
 * @author oa10712
 */
public class AddAssembly implements IUndoableAction {

    private final MechanicalAssemblerRecipe recipe;

    AddAssembly(ItemStack output, RecipeIngredient wire, RecipeIngredient circuit, RecipeIngredient base, RecipeIngredient... other) {
        recipe = new MechanicalAssemblerRecipe(output, wire, circuit, base, other);
    }

    @Override
    public void undo() {
        MechanicalAssemblerManager.getInstance().removeAssembly(recipe);
    }

    @Override
    public String describe() {
        return "Adding new mechanical assembly: ";
    }

    @Override
    public String describeUndo() {
        return "Removing new mechanical assembly: ";
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
        MechanicalAssemblerManager.getInstance().addAssembly(recipe);
        MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(recipe);
        System.out.println("Recipe Added: "+recipe.getOutput().getDisplayName());
    }

}
