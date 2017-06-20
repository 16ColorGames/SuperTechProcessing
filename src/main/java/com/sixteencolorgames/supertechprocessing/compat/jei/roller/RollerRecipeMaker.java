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
    public static List<RollerRecipe> getRecipes(IJeiHelpers helpers) {
        IStackHelper stackHelper = helpers.getStackHelper();
        RollerManager furnaceRecipes = RollerManager.instance();
        Map<ItemStack, ItemStack> smeltingMap = furnaceRecipes.getList();

        List<RollerRecipe> recipes = new ArrayList<RollerRecipe>();

        for (Map.Entry<ItemStack, ItemStack> itemStackItemStackEntry : smeltingMap.entrySet()) {
            ItemStack input = itemStackItemStackEntry.getKey();
            ItemStack output = itemStackItemStackEntry.getValue();

            List<ItemStack> inputs = stackHelper.getSubtypes(input);
            RollerRecipe recipe = new RollerRecipe(inputs, output);
            recipes.add(recipe);
        }

        return recipes;
    }

}
