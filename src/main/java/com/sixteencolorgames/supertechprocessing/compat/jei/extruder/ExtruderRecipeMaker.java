/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.compat.jei.extruder;

import com.sixteencolorgames.supertechprocessing.crafting.ExtruderManager;
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
public class ExtruderRecipeMaker {

    @Nonnull
    public static List<ExtruderJEIRecipe> getRecipes(IJeiHelpers helpers) {
        IStackHelper stackHelper = helpers.getStackHelper();
        ExtruderManager furnaceRecipes = ExtruderManager.instance();
        Map<List<ItemStack>, ItemStack> smeltingMap = furnaceRecipes.getList();

        List<ExtruderJEIRecipe> recipes = new ArrayList();

        smeltingMap.entrySet().stream().map((itemStackItemStackEntry) -> {
            ItemStack output = itemStackItemStackEntry.getValue();
            List<ItemStack> inputs = itemStackItemStackEntry.getKey();
            ExtruderJEIRecipe recipe = new ExtruderJEIRecipe(inputs, output);
            return recipe;
        }).forEachOrdered((recipe) -> {
            recipes.add(recipe);
        });

        return recipes;
    }

}
