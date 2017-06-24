/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.compat.jei.mechanicalAssembler;

import com.sixteencolorgames.supertechprocessing.crafting.MechanicalAssemblerManager;
import com.sixteencolorgames.supertechtweaks.crafting.RecipeIngredient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

/**
 *
 * @author oa10712
 */
public class MechanicalAssemblerRecipeMaker {

    @Nonnull
    public static List<MechanicalAssemblerRecipe> getRecipes(IJeiHelpers helpers) {
        IStackHelper stackHelper = helpers.getStackHelper();
        MechanicalAssemblerManager furnaceRecipes = MechanicalAssemblerManager.getInstance();
        Map<List<RecipeIngredient>, ItemStack> smeltingMap = furnaceRecipes.getRecipies();

        List<MechanicalAssemblerRecipe> recipes = new ArrayList();

        smeltingMap.entrySet().stream().map((itemStackItemStackEntry) -> {
            ItemStack output = itemStackItemStackEntry.getValue();
            List<RecipeIngredient> inputs = itemStackItemStackEntry.getKey();
            MechanicalAssemblerRecipe recipe = new MechanicalAssemblerRecipe(inputs, output);
            return recipe;
        }).forEachOrdered((recipe) -> {
            recipes.add(recipe);
        });

        return recipes;
    }

}
