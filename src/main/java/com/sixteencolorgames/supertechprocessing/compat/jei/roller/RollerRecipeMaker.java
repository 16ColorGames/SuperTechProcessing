/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechprocessing.compat.jei.roller;

import com.sixteencolorgames.supertechprocessing.crafting.RollerManager;
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
public class RollerRecipeMaker {

    @Nonnull
    public static List<RollerJEIRecipe> getRecipes(IJeiHelpers helpers) {
        IStackHelper stackHelper = helpers.getStackHelper();
        RollerManager furnaceRecipes = RollerManager.instance();
        Map<List<ItemStack>, ItemStack> smeltingMap = furnaceRecipes.getList();

        List<RollerJEIRecipe> recipes = new ArrayList();

        smeltingMap.entrySet().stream().map((itemStackItemStackEntry) -> {
            ItemStack output = itemStackItemStackEntry.getValue();
            List<ItemStack> inputs = itemStackItemStackEntry.getKey();
            RollerJEIRecipe recipe = new RollerJEIRecipe(inputs, output);
            return recipe;
        }).forEachOrdered((recipe) -> {
            recipes.add(recipe);
        });

        return recipes;
    }

}
