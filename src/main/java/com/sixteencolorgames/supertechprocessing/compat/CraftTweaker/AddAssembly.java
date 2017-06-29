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

    MechanicalAssemblerJEIRecipe jei;
    private final MechanicalAssemblerRecipe recipe;

    AddAssembly(ItemStack output, RecipeIngredient wire, RecipeIngredient circuit, RecipeIngredient base, RecipeIngredient... other) {
        System.out.println("Adding assembly: " + output.getDisplayName());
        System.out.println("    " + wire.getExamples().get(0).getDisplayName() + ":" + wire.getExamples().get(0).stackSize);
        System.out.println("    " + circuit.getExamples().get(0).getDisplayName() + ":" + circuit.getExamples().get(0).stackSize);
        System.out.println("    " + base.getExamples().get(0).getDisplayName() + ":" + base.getExamples().get(0).stackSize);
        for (RecipeIngredient ri : other) {
            System.out.println("    " + ri.getExamples().get(0).getDisplayName() + ":" + ri.getExamples().get(0).stackSize);
        }
        recipe = new MechanicalAssemblerRecipe(output, wire, circuit, base, other);
        jei = new MechanicalAssemblerJEIRecipe(recipe.getInputList(), recipe.getOutput());
    }

    @Override
    public void undo() {
        MechanicalAssemblerManager.getInstance().removeAssembly(recipe);
        MineTweakerAPI.ijeiRecipeRegistry.removeRecipe(jei);
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
        MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(jei);
        System.out.println("Recipe Added: " + recipe.getOutput().getDisplayName());
    }

}
